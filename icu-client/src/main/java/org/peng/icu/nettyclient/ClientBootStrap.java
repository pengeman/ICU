/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.peng.icu.nettyclient;


import org.peng.icu.pmq.PQueue;
import org.peng.icu.pmq.PQueueUtil;
import org.peng.icu.protocol.Protocol01;
import org.apache.log4j.Logger;

/**
 * @author peng
 */
public class ClientBootStrap {
    PQueue pq_toServer = PQueueUtil.getPQToServer(); // 向外输出消息
    PQueue pq_fromServer = PQueueUtil.getPQFromSerber(); // 接受回来的消息
    Logger log = Logger.getLogger(ClientBootStrap.class);

    public void run(String[] args) {
        init(args[0], args[1], args[2], args[3]);
    }

    public void init(String ip, String port, String user, String pwd) {
//        String ip = null;
//        String port = null;
//        String user = null;
//        String pwd = null;

        Protocol01 protocol = new Protocol01();
        protocol.setFlagmsg(new char[]{'S', 'N', '0', '0', '0', '0', '0', '0'});
        protocol.setUser(String.format("%-8s", user).toCharArray());
        protocol.setIndex(String.format("%-8s", user).toCharArray());
        protocol.setPwd(String.format("%-8s", pwd).toCharArray());
        String msg = protocol.toStr();
//        pq_toServer = new org.peng.icu.pmq.PQueue(10);
//        pq_fromServer = new org.peng.icu.pmq.PQueue(10);

        log.info("client发送:" + msg);
        try {
            pq_toServer.put(msg);
        } catch (Exception ex) {
            ex.printStackTrace();
            log.error(ex);
        }

        new ChineseProverbClient(pq_toServer, pq_fromServer, ip, Integer.parseInt(port));
    }

    public static void main(String[] args) throws Exception {
        //org.neety.client.TimeClient.main(args);
        //ChineseProverbClient.main(args);
//       SigninFace.main(args);
//        new ClientBootStrap().init("127.0.0.1", "8888", "peng", "pengeman");
        new ClientBootStrap().run(args);
    }
}
