package org.peng.icu.protocol;

import org.peng.icu.utils.StringUtil;

/**
 * @ClassName ProtocolUtil
 * @Date 2020/3/14 16:03
 * @Author pengyifu
 */
public class ProtocolUtil {
    /**
     * A -> S: B.username  A.username(协议字段)
     * S -> A: B.username, B.ip, B.port
     * S -> B: A.username, A.ip, A.port
     *
     * @param requester   请求方
     * @param touchClient 被请求方
     * @param ip          被请求方ip
     * @param port        被请求方port
     * @return
     */
    public static Protocol01 makeTouch(String touchClient, String ip, String port, String requester) {
        Protocol01 protocol01 = new Protocol01();
        protocol01.setFlagmsg("TO".toCharArray());
        protocol01.setDataLen("000000".toCharArray());

//        Touch touch = new Touch(touchClient,ip,port);
        String content = touchClient + ":" + ip + ":" + port;
        protocol01.setContent(content);
        protocol01.setUser(strCharArray(requester)); /* 如果是S->A, 那么请求方字段是null */
        return protocol01;
    }

    public static Touch parseTouch(Protocol01 p) {
        String[] a = p.getContent().split(":");
        return new Touch(a[0], a[1], a[2]);
    }

    private static char[] strCharArray(String str) {
        char[] cs = new char[8];
        if (str != null) {
            for (int i = 0; i < 8; i++) {
                cs[i] = str.charAt(i);
            }
        }
        return cs;
    }
}
