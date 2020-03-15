
package org.peng.icu.protocol;

/**
 * 验证并解析收到的数据包
 *
 * @author peng
 */
public class Parse {
    private String protocol;
    private String type;
    private Protocol01 protocol01 = new Protocol01();

    public Parse() {
    }

    public Parse(String protocol) {
        this.protocol = protocol;
    }

    /**
     * 验证数据是否符合协议格式
     *
     * @return null, 验证失败 ；Protocol01,验证通过
     */
    public Protocol01 check() {
        if (this.protocol == null) return null;
        if (this.protocol.length() < 20) return null;

        String flaghead1 = this.protocol.substring(0, 8);
        String index = this.protocol.substring(8, 16);
        String flagmsg = this.protocol.substring(16, 18); // 信息部分的标识头
        String dataLen = this.protocol.substring(18, 24); // 信息部分的内容部分
        String spaces = this.protocol.substring(24, 32);  // 空行
        String content = this.protocol.substring(32, this.protocol.length() - 18);
        String username = this.protocol.substring(this.protocol.length() - 18, this.protocol.length() - 10);
        String password = this.protocol.substring(this.protocol.length() - 10, this.protocol.length() - 2);
        String theEnd = this.protocol.substring(this.protocol.length() - 2);
        dataLen = dataLen.trim();

        if (!flaghead1.equals("$$FICU01")) {
            return null;
        }


        switch (flagmsg) {
            case "MD":
                this.type = "MD";
                int dlen = Integer.parseInt(dataLen);
                if (content.length() != dlen) return null;
                break;
            case "DD":
                this.type = "DD";
                break;
            case "TO":
                this.type = "TO";
                break;
            case "RD":
                this.type = "RD";
                break;
            case "SC":
                this.type = "SC";
                break;
            case "SN":
                this.type = "SN";
                break;
            case "CT":
                this.type = "CT";
                break;
            case "FU":
                this.type = "FU";
                break;
            default:
                this.type = null;
                return null;
        }


        protocol01.setIndex(index.toCharArray());
        protocol01.setFlagmsg(flagmsg.toCharArray());
        protocol01.setDataLen(dataLen.toCharArray());
        protocol01.setSpaces(spaces.toCharArray());
        protocol01.setContent(content);
        protocol01.setUser(username.toCharArray());
        protocol01.setPwd(password.toCharArray());

        return protocol01;
    }

    /**
     * 协议的类型，
     * 协议分为01型，02型，03型，04型，05型
     */
    public String getType() {
        return null;
    }

    /**
     * 得到协议内容
     *
     * @return
     */
    public String getContent() {
        return null;
    }


    /**
     * 得到协议中的文档
     *
     * @return
     */

    public Object getDoc() {
        return null;
    }


    /**
     * @return the protocol
     */
    public String getProtocol() {
        return protocol;
    }

    /**
     * @param protocol the protocol to set
     */
    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public static void main(String[] args) {
        Parse p = new Parse();
        String msg = "$$FICU01        SN000000              pwt     sdf     ok";
        p.setProtocol(msg);
        Protocol01 proc = p.check();
        System.out.println(proc.toStr());
    }

}
