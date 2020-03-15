/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.peng.icu.nettyserver;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioDatagramChannel;

public class ChineseProverbServer extends Thread {
    org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(ChineseProverbServer.class);

    private int port = 0;

    public ChineseProverbServer(int port) {
        this.port = port;
    }

    @Override
    public void run() {
        try {
            run(port);
        } catch (Exception ex) {
            log.info(ex);
        }
    }

    public void run(int port) {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
         try {
            //通过NioDatagramChannel创建Channel，并设置Socket参数支持广播
           //UDP相对于TCP不需要在客户端和服务端建立实际的连接，因此不需要为连接（ChannelPipeline）设置handler
            Bootstrap b = new Bootstrap();

            b.group(bossGroup)
                    .channel(NioDatagramChannel.class)
                    .option(ChannelOption.SO_BROADCAST, true)
                    .handler(new ChineseProverbServerHandler());
            System.out.println("开启服务器，监听端口 " + port);
            b.bind(port)
                    .sync()
                    .channel()
                    .closeFuture()
                    .await();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.out.println("服务端优雅的关闭");
            bossGroup.shutdownGracefully();
        }
    }
}