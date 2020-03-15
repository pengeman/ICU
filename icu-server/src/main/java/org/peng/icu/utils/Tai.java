/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.peng.icu.utils;


import org.peng.icu.user.Client;
import org.peng.icu.user.ClientRoom;

import java.util.List;
import java.util.Scanner;

/**
 * @author peng
 */
public class Tai extends Thread {
    @Override
    public void run() {
        while (true) {
            try {
                byte[] by = new byte[10];
                System.out.print("> ");
                Scanner sc = new Scanner(System.in);
                String bbs = sc.nextLine();

//                if (bbs.equals("exit")) {
//                    break;
//                }
                //String subbbs = bbs.substring(0, 3);
                switch (bbs) {
                    case "quit":
                    case "exit":
                        System.exit(0);
                        break;
                    case "file":

                        break;
                    case "M":
                        break;
                    case "showme":
//                        TaiFace sf = new TaiFace();
//                        sf.setVisible(true);
                        break;
                    case "list":
                    case "users":
                        List<Client> clients = ClientRoom.getList();
                        if (ListUtil.isEmpty(clients)) {
                            System.out.println("no user");
                        } else {
                            System.out.println("Current Total Client: " + clients.size());
                            for (Client client : clients) {
                                System.out.println(client);
                            }
                        }
                        break;
                    case "clear --all":
                        /*
                        todo:
                            考虑客户端如何得知自己从服务器中clear了
                            可用方法:(用哪种)(推荐2)
                                1. 服务端clear时主动告知客户端
                                2. 客户端向服务端发送心跳, 如果心跳发送失败则告知客户端"断开连接"(与服务器clear没有关系)
                                    这样的话 服务端clear功能就是用来清除过期客户端列表
                        */
                        System.out.println("[WARN]ARE YOU SURE?(Y/N)");
                        String a = sc.nextLine();
                        if(a.equalsIgnoreCase("y")){
                            ClientRoom.clear();
                        }
                        break;
                    default:

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
