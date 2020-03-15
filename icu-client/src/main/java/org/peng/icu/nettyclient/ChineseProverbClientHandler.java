/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.peng.icu.nettyclient;

import org.peng.icu.protocol.Parse;
import org.peng.icu.protocol.Protocol01;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.DatagramPacket;
import org.peng.icu.protocol.ProtocolUtil;
import org.peng.icu.user.Client;
import org.peng.icu.utils.ClientUtil;
import org.peng.icu.utils.IpUtil;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;

public class ChineseProverbClientHandler extends SimpleChannelInboundHandler<DatagramPacket> {

    org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(ChineseProverbClientHandler.class);
    org.peng.icu.pmq.PQueue pq_toServer = null; // 向外输出消息
    org.peng.icu.pmq.PQueue pq_fromServer = null; // 接受回来的消息
    String ip; // 服务器的ip
    int port;  // 服务器的port

    public ChineseProverbClientHandler(java.io.PipedOutputStream outPipe) {
//        this.outPipe = outPipe;
    }

    public ChineseProverbClientHandler(org.peng.icu.pmq.PQueue pq1, org.peng.icu.pmq.PQueue pq2, String ip, int port) {
        this.pq_toServer = pq1;
        this.pq_fromServer = pq2;
        this.ip = ip;
        this.port = port;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        log.info("本地监听端口已开启:");
        super.channelActive(ctx);
        /* 连接服务器线程 */
        new ConnectServerThread(ctx, pq_toServer, ip, port).start();
        new Tai(ctx).start();
    }

//    @Deprecated
//    public void messageReceived(ChannelHandlerContext ctx, DatagramPacket msg) throws Exception {
//        String response = msg.content().toString(CharsetUtil.UTF_8);
//        //if(response.startsWith("谚语查询结果：")){
//        System.out.println(response);
//        ctx.close();
//        //}
//    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

    @Override
    protected void channelRead0(ChannelHandlerContext chc, DatagramPacket i) throws Exception {
        //this.messageReceived(chc, i);
        log.info("客户端收到消息");
        msgArrive(chc, i);
    }

    protected void msgArrive(ChannelHandlerContext ctx, DatagramPacket packet)
            throws Exception {
        String friendIP; // 正在聊天的好友
        String friendPort; // 正在聊天的好友
        friendIP = packet.sender().getAddress().getHostAddress();
        friendPort = packet.sender().getPort() + "";

        ByteBuf buf = packet.copy().content();
        byte[] req = new byte[buf.readableBytes()];
        buf.readBytes(req);
        String str = new String(req, StandardCharsets.UTF_8);
        log.info("msg arrive: " + str);
        String[] list = str.split(" ");
        // 登录验证失败
        if (list[0].equals("E")) {
            log.info("登录验证失败");
            // 通过管道向外发送消息,登录失败
//            outPipe.write("E".getBytes());
//            outPipe.flush();outPipe.close();
            this.pq_fromServer.put("E");
            return;
        }
        if (list[0].equals("B")) {
            log.info("登录验证成功，你是第一位登录者");
            pq_fromServer.put("B");
//            outPipe.write("B".getBytes());
//            outPipe.flush();outPipe.close();
            return;
        }
        if (list[0].equals("PP")) {
            //String toIP; // 正在聊天的好友
            //String toPort; // 正在聊天的好友
            this.pq_fromServer.put("PP:" + list[1] + ":" + friendIP + ":" + friendPort);
            return;
        }

        // 解析收到的数据包
        Parse parse = new Parse(str);
        Protocol01 protocol = parse.check();

        if (protocol == null) {
            log.debug(protocol);
            return;
        }
        String flagmsg = new String(protocol.getFlagmsg());
        if (flagmsg.equals("CT")) {
            // 新来的要加入好友列表，内容里是新人的ip和port; 格式 ip:port
            String[] newer = protocol.getContent().split(":");
            String newerIP = newer[0];
            String newerPort = newer[1];
            log.info("新人来到 " + newerIP + ":" + newerPort);
            newerComein(newerIP, newerPort); // 将新人的消息发回界面
            return;
        }

        if (flagmsg.equals("MD")) {
            // 收到文字信息
            pq_fromServer.put(str);
        }
        if (flagmsg.equals("DD")) {
            // 收到文档信息，
            pq_fromServer.put(str);
        }
        if (flagmsg.equals("FU")) {
            // 收到索取好友信息
            pq_fromServer.put(str);
        }
        if (flagmsg.equals("TO")) {
            // 收到打洞消息，不再把消息发回界面，这里直接处理
            Client clientB = ClientUtil.buildClient(
                    ProtocolUtil.parseTouch(protocol));

            if (clientB == null || clientB.getIp() == null || clientB.getPort() == null) {
                System.out.println("Server: no user " + clientB == null ? "" : clientB.getUsername());
            } else {

//            String context = protocol.getContent();
//            String[] ss = context.split(":");
                String ip = clientB.getIp();
                String port = clientB.getPort();
                log.info("send the message of through:to->" + ip + ":" + port);

                byte[] ipBuf = IpUtil.ipToByte(ip);

                InetAddress netAddr = InetAddress.getByAddress(ip, ipBuf);
                InetSocketAddress ar1 = new InetSocketAddress(netAddr, Integer.parseInt(port));
                while (true) {
                    log.info(ar1);
                    ctx.writeAndFlush(new DatagramPacket(
                            Unpooled.copiedBuffer("PP hello,what are you doing..".getBytes()), ar1));
                    Thread.sleep(1000);
                }
//                ctx.writeAndFlush(new DatagramPacket(
//                        Unpooled.copiedBuffer("PP hello,what are you doing..".getBytes()), ar1));
            }

            //如果是A 则发送打洞消息
//            if (list[0].equals("A")) {
//                String ip = list[1];
//                String port = list[2];
//                log.info("发送打洞消息:" + ip + ":" + port);
//                ctx.writeAndFlush(new DatagramPacket(
//                        Unpooled.copiedBuffer("打洞信息".getBytes()), new InetSocketAddress(ip, Integer.parseInt(port))));
//                Thread.sleep(1000);
//                ctx.writeAndFlush(new DatagramPacket(
//                        Unpooled.copiedBuffer("P2P info..".getBytes()), new InetSocketAddress(ip, Integer.parseInt(port))));
//
//            }
        }

    }


    private void newerComein(String newerIP, String newerPort) {
        String msg = newerIP + ":" + newerPort;
        pq_fromServer.put(msg);
    }
}
