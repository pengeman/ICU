package org.peng.icu.nettyclient;

import org.peng.icu.protocol.Parse;
import org.peng.icu.protocol.Protocol01;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.socket.DatagramPacket;
import org.apache.log4j.Logger;
import org.peng.icu.pmq.PQueue;
import org.peng.icu.utils.IpUtil;

import java.net.InetSocketAddress;

/**
 * @ClassName ConnectServerThread
 * @Date 2020/3/1 17:26
 */
public class ConnectServerThread extends Thread {
    Logger log = Logger.getLogger(this.getClass());
    String friendIP; // 正在聊天的好友
    String friendPort; // 正在聊天的好友
    ChannelHandlerContext ctx;
    PQueue pq_toServer;
    String serverIP;
    int serverPort;

    public ConnectServerThread(ChannelHandlerContext ctx, PQueue pq_toServer, String serverIP, int serverPort) {
        this.ctx = ctx;
        this.pq_toServer = pq_toServer;
        this.serverIP = serverIP;
        this.serverPort = serverPort;
    }

    /**
     * 这里是处理toServer中消息队列中的消息, 即向外发送
     */
    @Override
    public void run() {
        log.info("to server thread");
        while (true) {
            try {
// 所有的操作都依赖于这个队列
                String str = (String) pq_toServer.take();  // 从队列中拿到消息
                log.info("to server or client: " + str);
//                        if (str.equals("exit")) {
//                            System.exit(0);
//                        }
                Parse parse = new Parse(str);
                Protocol01 protocol = parse.check();
                String flagMsg = new String(protocol.getFlagmsg());
                if ("MD".equals(flagMsg) || "DD".equals(flagMsg)) {
                    /* 给其他客户端发 */
                    this.friendIP = new String(protocol.getUser());
                    this.friendIP = IpUtil.ipToDec(this.friendIP);
                    log.info("friendIP: " + friendIP);
                    this.friendPort = new String(protocol.getPwd()).trim();
                    ctx.writeAndFlush(new DatagramPacket(
                            Unpooled.copiedBuffer(str.getBytes()),
                            new InetSocketAddress(friendIP, Integer.parseInt(friendPort))));
                } else {
                    /* 登录服务器, 给服务器发送 */
                    ctx.writeAndFlush(
                            new DatagramPacket(
                                    Unpooled.copiedBuffer(str.getBytes()),
                                    new InetSocketAddress(serverIP, serverPort))
                    );
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                log.error(ex);
            }
        }
    }
}
