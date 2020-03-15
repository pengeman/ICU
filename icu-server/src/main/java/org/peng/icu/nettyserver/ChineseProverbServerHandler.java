package org.peng.icu.nettyserver;/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.DatagramPacket;
import io.netty.util.CharsetUtil;
import io.netty.util.internal.ThreadLocalRandom;

import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.peng.icu.protocol.Parse;
import org.peng.icu.protocol.Protocol01;
import org.peng.icu.protocol.ProtocolUtil;
import org.peng.icu.user.Client;
import org.peng.icu.user.ClientRoom;
import org.peng.icu.utils.*;

public class ChineseProverbServerHandler extends SimpleChannelInboundHandler<DatagramPacket> {

    Logger log = Logger.getLogger(ChineseProverbServer.class);

    boolean flag = false;
    InetSocketAddress addr1 = null;
    InetSocketAddress addr2 = null;
    Map chatRoom = new HashMap();

    private static final String[] DICTIONARY = {"小葱拌豆腐，一穷二白", "只要功夫深，铁棒磨成针", "山中无老虎，猴子称霸王"};

    private String nextQuote() {
        //线程安全随机类，避免多线程环境发生错误
        int quote = ThreadLocalRandom.current().nextInt(DICTIONARY.length);
        return DICTIONARY[quote];
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx); //To change body of generated methods, choose Tools | Templates.
        log.info("链接服务器");
        /* 开启控制台交互 */
        new Tai().start();
    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
            throws Exception {
//        ctx.close();
        cause.printStackTrace();
    }

    @Override
    protected void channelRead0(ChannelHandlerContext chc, DatagramPacket i) throws Exception {
        try{

            this.messageArrive(chc, i);
        }catch(Exception e){

        }
    }

    private void messageArrive(ChannelHandlerContext ctx, DatagramPacket packet)
            throws Exception {
        String msg = packet.content().toString(CharsetUtil.UTF_8);
        log.info("收到一个数据包:" + msg);
        Parse parse = new Parse(msg);

        Protocol01 protocol = parse.check(); // 检查通过，应该返回protocol对象
        if (protocol == null) {
            return;
        }
        Client clientSenderA = ClientUtil.buildClient(packet, protocol);
        String flagmsg = new String(protocol.getFlagmsg());

        if (flagmsg.equals("SN")) {
            //登录，
            log.info("sign in ... ");
            InetSocketAddress ar1 = packet.sender();  // 地址
            String user = new String(protocol.getUser());
            String pwd = new String(protocol.getPwd());
//            String anonymous = new String(protocol.getIndex()); //
            // 验证用户名口令
            log.info("验证用户名口令" + user + "/" + pwd
            );
            /*目前没用*/
            if (this.check(user, pwd) == 0) {
                this.replay("E", ar1, ctx);
                log.info("验证失败");
                return;
            }

            log.info("验证通过");
//            Client client = new Client();
//            client.setUsername(user.trim());
//            client.setPwd(pwd.trim());
////            client.setAnonymous(anonymous);
//            client.setIp(ar1.getAddress().getHostAddress());
//            client.setPort(ar1.getPort() + "");
//            client.setSigninTime(new Date());
//            client.setNetAddr(packet.sender());

//            MessagePool.addUser(user, ar1.getAddress().getHostAddress(), ar1.getPort() + "");
            ClientRoom.add(clientSenderA);  /* 记录当前用户 */

            this.replay("B", ar1, ctx);  // 发送B，表示登录成功

            // 将新人发送给其他已经登录者，并给其他已经登录者发送给新人，这叫新人报到
            /* 先不要 */
//            log.debug("新人报到，向每个人发送报到消息");
//            this.newerSignin(client, ctx);
//            log.debug(client.getIp() + ":" + client.getPort());

        } else if (flagmsg.equals("FU")) {
            // 回应客索取所有登录者消息

//            Client currentClient = null;
//
            InetSocketAddress ar = packet.sender();
            String msg2 = HandlerUtil.requestFU(clientSenderA);
//            String ip = ar.getAddress().getHostAddress();
//            String port = ar.getPort() + "";
//            log.info(ip + ":" + port + " 客户端发来FU");
//
//            List<Client> clientList = JavaConversions.seqAsJavaList(ClientRoom.list());
//
//
//            List<Client> ul = Common.USERROOM;
//            for (Client c : ul) { // 查询得到当前请求者的client
//                if (c.getIp().equals(ip) && c.getPort().equals(port)) {
//                    currentClient = c;
//                    break;
//                }
//            }
//            if (currentClient == null) {
//                return;
//            }
//            String currentUser = currentClient.getUsername(); // 当前请求者的名字
//            Client client;
//            StringBuffer msg2 = new StringBuffer();
//            for (int i = 0; i < Common.USERROOM.size(); i++) {
//                client = (Client) Common.USERROOM.get(i);
//                String name = client.getUsername();
//                if (name.equals(currentUser)) {
//                    continue;
//                }
//                String ip2 = client.getIp();
//                String port2 = client.getPort();
//                String userName = client.getUsername();
//                String anonymous = client.getAnonymous();
//
//                msg2.append(userName).append(":").append(anonymous).append(":").append(ip2).append(":").append(port2);
//                msg2.append(";");
//
//            }
            if (msg2.length() < 3) {
                return;
            }
            protocol.setFlagmsg("FU".toCharArray());
            protocol.setDataLen("000000".toCharArray());
            protocol.setContent(msg2);
            protocol.setUser("pppppppp".toCharArray());
            protocol.setPwd("wwwwwwww".toCharArray());
            this.replay(protocol.toStr(), ar, ctx); // 返回相应

        } else if (flagmsg.equals("TO")) {
            // touch someone
//            String sa1 = protocol.getContent();
//            sa1 = sa1.split(":")[0];
            String touchClient = ProtocolUtil
                    .parseTouch(protocol)
                    .getTouchUsername();
            Client clientB = ClientRoom.get(touchClient);
//            InetSocketAddress clientAAddress = packet.sender();

//            InetSocketAddress a2 = (InetSocketAddress)


//            String touch = makeTouch(a2); // 生成一个touch a2 的touch协议
//            String remot = touch;
            log.info("Touch 命令 :" + clientSenderA + " -> " + clientB);
            if (clientB != null) {

                /* 给A发送B的信息*/
                Protocol01 touch2A = ProtocolUtil.makeTouch(
                        clientB.getUsername(),
                        clientB.getIp(),
                        clientB.getPort(),
                        null);
                this.replay(touch2A, clientSenderA, ctx);

                log.info("Touch 命令 :" + clientB + " -> " + clientSenderA);
                /* 给B发送A的信息*/
                Protocol01 touch2B = ProtocolUtil.makeTouch(
                        clientSenderA.getUsername(),
                        clientSenderA.getIp(),
                        clientSenderA.getPort(),
                        null);
                this.replay(touch2B, clientB, ctx);
            } else {
                log.info("Touch result: no user " + touchClient);
                Protocol01 touch2A = ProtocolUtil.makeTouch(touchClient, null, null, null);
                this.replay(touch2A, clientSenderA, ctx);
            }
//            touch = makeTouch(a1);
//            remot = touch;
//            this.replay(remot, a2, ctx);

        } else if (flagmsg.equals("DD")) {
            InetSocketAddress ad1 = ad1 = packet.sender();
            ctx.writeAndFlush(new DatagramPacket(
                    Unpooled.copiedBuffer("QQQ 111".getBytes()), ad1));

            System.out.println("回应数据QQQ");
            ctx.writeAndFlush(new DatagramPacket(
                    Unpooled.copiedBuffer("QQQ 2222".getBytes()), ad1));
            System.out.println("回应数据QQQ222");
        } else if (flagmsg.equalsIgnoreCase("M")) {
            //addr1 -> addr2
            String remot = "A " + addr2.getAddress().getHostAddress()
                    + " " + addr2.getPort();
            ctx.writeAndFlush(new DatagramPacket(
                    Unpooled.copiedBuffer(remot.getBytes()), addr1));
            //addr2 -> addr1
            remot = "A " + addr1.getAddress().getHostAddress()
                    + " " + addr1.getPort();
            ctx.writeAndFlush(new DatagramPacket(
                    Unpooled.copiedBuffer(remot.getBytes()), addr2));
            System.out.println("M 命令");
        }

    }

    // 将新人发送给其他已经登录者，并给其他已经登录者发送给新人，这叫新人报到
//    private void newerSignin(Client client, ChannelHandlerContext ctx) {
//        String newerIP = client.getIp();
//        String newerPort = client.getPort();
//        String newerUsername = client.getUsername();
//        InetSocketAddress newer_ar = client.getNetAddr();
//        int f = 0;
//        List users = Common.USERROOM;
//        Iterator it = users.iterator();
//        while (it.hasNext()) {
//            Client c1 = (Client) it.next();
//            String ip1 = c1.getIp();
//            String port1 = c1.getPort();
//            String username = c1.getUsername();
//            if (username.equals(newerUsername)) {
//                f = 1; // 登录者在USERROOM中已经存在
//            }
//            try {
//                String[] ips = ip1.split("\\.");
//                byte[] ipBuf = new byte[4];
//
//                ipBuf[0] = (byte) (Integer.parseInt(ips[0]) & 0xff);
//                ipBuf[1] = (byte) (Integer.parseInt(ips[1]) & 0xff);
//                ipBuf[2] = (byte) (Integer.parseInt(ips[2]) & 0xff);
//                ipBuf[3] = (byte) (Integer.parseInt(ips[3]) & 0xff);
//
//                java.net.InetAddress netAddr = java.net.InetAddress.getByAddress(ip1, ipBuf);
//                InetSocketAddress ar1 = new java.net.InetSocketAddress(netAddr, Integer.parseInt(port1));
//                String msg = makeNewer(username,ip1, port1);
//                this.replay(msg, newer_ar, ctx);
//
//                String newer_msg = makeNewer(newerUsername,newerIP, newerPort);
//                this.replay(newer_msg, ar1, ctx);
//            } catch (UnknownHostException ex) {
//                log.error(ex);
//            }
//
//        }
//        chatRoom.put(newerUsername, newer_ar);
//        if (f == 0) {
//            Common.USERROOM.add(client);
//        }
//    }

//    private String makeNewer(String username, String ip, String port) {
//        Protocol01 protocol = new Protocol01();
//        protocol.setFlagmsg("CT".toCharArray());
//        protocol.setDataLen("000000".toCharArray());
//        protocol.setContent(username + ":" + username + ":" + ip + ":" + port);
//
//        String msg = protocol.toStr();
//        return msg;
//    }


    /**
     * 验证用户名口令，
     *
     * @param user
     * @param pwd
     * @return 1, 验证通过，0,验证不通过
     */
    private int check(String user, String pwd) {
        return 1;
    }

//    private String makeTouch(String ip, String port) {
//        Protocol01 protocol = new Protocol01();
//        protocol.setFlagmsg("TO".toCharArray());
//        protocol.setDataLen("000000".toCharArray());
//        protocol.setContent(ip + ":" + port);
//        String msg = protocol.toStr();
//        return msg;
//    }

//    private String makeTouch(InetSocketAddress a) {
//        String ip;
//        String port;
//
//        ip = a.getAddress().getHostAddress();
//        port = a.getPort() + "";
//
//        return makeTouch(ip, port);
//    }

    private void replay(Protocol01 protocol, Client client, ChannelHandlerContext ctx) {
        InetSocketAddress addr = client.getNetAddr();
        String msg = protocol.toStr();
        replay(msg, addr, ctx);
    }

    /**
     * 将消息msg发送给addr
     *
     * @param msg
     * @param addr
     * @param ctx
     */
    private void replay(String msg, InetSocketAddress addr, ChannelHandlerContext ctx) {
        ctx.writeAndFlush(new DatagramPacket(Unpooled.copiedBuffer(msg.getBytes()), addr));
        log.info("replay:" + msg);
    }
}
