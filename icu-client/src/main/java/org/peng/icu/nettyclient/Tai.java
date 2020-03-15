/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.peng.icu.nettyclient;


import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.socket.DatagramPacket;
import org.peng.icu.protocol.Protocol01;
import org.peng.icu.pmq.PQueueUtil;
import org.peng.icu.protocol.ProtocolUtil;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.Scanner;

/**
 * @author peng
 */
public class Tai extends Thread {
    ChannelHandlerContext ctx;

    public Tai(ChannelHandlerContext ctx) {
        this.ctx = ctx;
    }

    @Override
    public void run() {
        while (true) {
            try {
                byte[] by = new byte[10];
                System.out.print("> ");
                Scanner scanner = new Scanner(System.in);
                String bbs = scanner.nextLine().trim();
//                if (bbs.equals("exit")) {
//                    break;
//                }
                //String subbbs = bbs.substring(0, 3);
                switch (bbs) {
                    case "quit":
                    case "exit":
                        System.exit(0);
                        break;
                    case "to":
                    case "TO":
                    case "touch":
                        /* 发送想要聊天的人的用户名 */
                        System.out.print("who to touch: ");
                        String toClient = scanner.nextLine().trim();
                        Protocol01 p = ProtocolUtil.makeTouch(toClient, null, null, null);
                        PQueueUtil.send(p);
                        break;
                    case "whoami":
                    case "showme":
//                        TaiFace sf = new TaiFace();
//                        sf.setVisible(true);
                        break;
                    case "list":
                    case "users":
//                        List<Client> clients = ClientRoom.getList();
//                        if (ListUtil.isEmpty(clients)) {
//                            System.out.println("no user");
//                        } else {
//                            System.out.println("Current Total Client: " + clients.size());
//                            for (Client client : clients) {
//                                System.out.println(client);
//                            }
//                        }
//                        break;
                    case "go":
                        InetAddress netAddr = InetAddress.getByAddress("112.65.48.31",new byte[]{112,65,48,31});
                        InetSocketAddress ar1 = new InetSocketAddress(netAddr, Integer.parseInt("9998"));
                        while (true) {
                            System.out.println(ar1);
                            ctx.writeAndFlush(new DatagramPacket(
                                    Unpooled.copiedBuffer("PP hello,what are you doing..".getBytes()), ar1));
                            Thread.sleep(1000);
                        }
//                    case "clear --all":
//                        System.out.println("[WARN]ARE YOU SURE?(Y/N)");
//                        Scanner scanner = new Scanner(System.in);
//                        String a = scanner.nextLine();
//                        if(a.equalsIgnoreCase("y")){
//                            ClientRoom.clear();
//                        }
//                        break;
                    default:

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
