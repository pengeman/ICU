package org.peng.icu.utils;

import org.peng.icu.protocol.Protocol01;
import io.netty.channel.socket.DatagramPacket;
import org.peng.icu.protocol.Touch;
import org.peng.icu.user.Client;

import java.net.InetSocketAddress;
import java.util.Date;

/**
 * @ClassName ClientUtil
 * @Date 2020/3/14 18:14
 * @Author pengyifu
 */
public class ClientUtil {
    public static Client buildClient(DatagramPacket packet, Protocol01 protocol) {
        InetSocketAddress ar1 = packet.sender();  // 地址
        String user = new String(protocol.getUser());
        String pwd = new String(protocol.getPwd());
//            String anonymous = new String(protocol.getIndex()); //
        Client client = new Client();
        client.setUsername(user.trim());
        client.setPwd(pwd.trim());
//            client.setAnonymous(anonymous);
        client.setIp(ar1.getAddress().getHostAddress());
        client.setPort(ar1.getPort() + "");
        client.setSigninTime(new Date());
        client.setNetAddr(packet.sender());
        return client;
    }

    public static Client buildClient(Touch touch){
        Client client = new Client();
        client.setIp(touch.getTouchIp());
        client.setUsername(touch.getTouchUsername());
        client.setPort(touch.getTouchPort());
        return client;
    }
}
