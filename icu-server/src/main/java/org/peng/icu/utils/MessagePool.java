/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.peng.icu.utils;

import java.util.*;

/**
 * 消息池 放置如下消息 登录的用户名 登录的用户ip 登录的用户port 登录的用户数量 登录的用户登录时间
 *
 * @author peng
 */
@Deprecated
public class MessagePool {

    static Map<String, Object> msgs = new HashMap();
    static{
        msgs.put("user", new ArrayList());
        msgs.put("count", 0);
    }
    //static private User user = new User();

    static public void addUser(String name, String ip, String port) {
        List users = (List) msgs.get("user");
        if (users == null) {
            users = new ArrayList();
        }else{
            for (int i = 0; i < users.size(); i++) {
                User user2 = (User) users.get(i);
                if (user2.name.equals(name)) {
                    users.remove(user2);
                    break;
                }
            }
        }
       
        // 没有存储这个用户

        User user = new User();

        user.name = name;
        user.ip = ip;
        user.port = port;
        user.signinTime = new Date().getTime();

        users.add(user);
        //msgs.put("user", users);
        int c = (Integer) msgs.get("count") + 1;
        msgs.put("count", c);

    }

    static public void delUser(String name) {
        List users = (List) msgs.get("user");
        for (int i = 0; i < users.size(); i++) {
            User user2 = (User) users.get(i);
            if (user2.name.equals(name)) {
                users.remove(i);
                return;
            }
        }     // 没有存储这个用户
    }

    static public List getUsers() {
        List users = (List) msgs.get("user");
        if (users == null || users.size() == 0) {
            return null;
        }
        
        List usersLine = new ArrayList();
        for (Iterator it = users.iterator(); it.hasNext();) {
            User user = (User) it.next();
            String name = user.name;
            String ip = user.ip;
            String port = user.port;
            String signinTime = new java.text.SimpleDateFormat("yyyy/MM/dd hh:mm:ss").format(new Date(user.signinTime));
            String us = name + "," + ip + "," + port + "," + signinTime;
            
            usersLine.add(us);
            
        }
        return usersLine;
    }
}

