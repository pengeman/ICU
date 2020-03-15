/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.peng.icu.nettyclient;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioDatagramChannel;
import org.peng.icu.pmq.PQueue;
import org.peng.icu.utils.RandomUtil;

import java.io.IOException;
import java.net.BindException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Properties;

public class ChineseProverbClient {

    org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(ChineseProverbClient.class);
    Properties properties = new Properties();
    PQueue pq = null;
    Channel channel = null;

    public ChineseProverbClient(org.peng.icu.pmq.PQueue pq1, org.peng.icu.pmq.PQueue pq2, String ip, int port) {
        boolean isPropertiesAddressAlreadyUse = false;
        while (true) {
            try {
                properties.load(this.getClass().getClassLoader().getResourceAsStream("client.properties"));
                EventLoopGroup group = new NioEventLoopGroup();
                Bootstrap b = new Bootstrap();
                b.group(group)
                        .channel(NioDatagramChannel.class)
//                    .option(ChannelOption.SO_BROADCAST, true)
                        .handler(new ChineseProverbClientHandler(pq1, pq2, ip, port));

//            int p = findPort();
//            if (p == 0) {
//                return;
//            }
//            System.out.println(p);
//            int p = 9999;
                int bindPort;
                String listen_port = properties.getProperty("listen.port");
                if (listen_port == null || isPropertiesAddressAlreadyUse) {
                    bindPort = randomPort();
                } else {
                    bindPort = Integer.parseInt(listen_port);
                }
                log.info("listen port: " + bindPort);
                Channel ch = b.bind(bindPort).sync().channel();

                this.channel = ch;
                ch.closeFuture().await();
                System.out.println("------------------------------------------");
                break;
            } catch (InterruptedException | IOException ex) {
                ex.printStackTrace();
                log.error(ex);
                if (ex instanceof BindException) {
                    log.info("try another new port");
                    isPropertiesAddressAlreadyUse = true;
                }
            }
        }

    }

    //    private ChannelFuture bindPort(Bootstrap b) {
//        ChannelFuture cf = null;
////        for(int i=49152;i<=65535;i++){
////        }
//        int port = Integer.parseInt(properties.getProperty("listen.port"));
//        cf = b.bind(port);
//        return cf;
//    }

    //    @Deprecated
    public int randomPort() {
        return RandomUtil.randomIntRange(49152, 65535);
    }

    @Deprecated
    private void bindPort(String host, int port) throws Exception {
        Socket s = new Socket();
        s.bind(new InetSocketAddress(host, port));
        s.close();
    }

}
