/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.peng.icu.utils;

import java.util.ArrayList;

/**
 * 辅助工具
 *
 * @author peng
 */
public class IpUtil {

    /**
     * 十进制的ip转换成十六进制ip格式 192.168.2.1 -> c0a80201
     *
     * @param ip
     * @return
     */
    static public String ipToHex(String ip) {
        byte[] ip1 = new byte[4];

        String[] ips = ip.split("\\.");
        StringBuffer sb = new StringBuffer();
        for (String str : ips) {
            int ist = Integer.parseInt(str);
            String iph = Integer.toHexString(ist);
            iph = len2(iph);
            sb.append(iph);
        }
        return sb.toString();

    }

    static public byte[] ipToByte(String ip) {
        String[] ips = ip.split("\\.");
        byte[] ipBuf = new byte[4];
        ipBuf[0] = (byte) (Integer.parseInt(ips[0]) & 0xff);
        ipBuf[1] = (byte) (Integer.parseInt(ips[1]) & 0xff);
        ipBuf[2] = (byte) (Integer.parseInt(ips[2]) & 0xff);
        ipBuf[3] = (byte) (Integer.parseInt(ips[3]) & 0xff);
        return ipBuf;
    }

    /**
     * 十六进制格式ip转换成十进制格式ip c0a80201 -> 192.168.2.1
     *
     * @param ip
     * @return
     */
    static public String ipToDec(String ip) {
        long sum = 0, tmp = 0;
        ip = ip.toUpperCase();
        StringBuffer sb = new StringBuffer();
        for (int k = 0; k < 4; k++) {
            String subip = ip.substring(0, 2);
            for (int i = 0; i < subip.length(); i++) {
                char c = subip.charAt(i);
                if (c >= '0' && c <= '9') {
                    tmp = c - '0';
                } else if (c >= 'A' && c <= 'F') {
                    tmp = c - 'A' + 10;
                } else {
                    System.out.println("ipToDec()中有非法字符");
                    break;
                }
                sum = sum * 16 + tmp;
            }
            ip = ip.substring(2);
            sb.append(sum).append(".");
            sum = 0;
        }
        return sb.substring(0, sb.length() - 1);

    }

    private static String len2(String s) {

        return s.length() == 2 ? s : "0" + s;
    }
}
