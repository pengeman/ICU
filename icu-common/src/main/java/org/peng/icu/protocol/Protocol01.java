/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.peng.icu.protocol;

/**
 * 通讯协议01
 * 客户端与服务端通讯 或 客户端与客户端通讯
 * 
 * 说明	协议内容	长度	
  标识头部	$$FICU01	8	
     序号	xxxxxxxx	8	链接服务器时，服务器分配的序号
  信息标识	MD999999	8	表示数据包是一个文字包，长度是999999
	        DD999999	8	表示数据包是一个文档包，长度是999999
                HJ000000      8       标出心跳包，长度是000000,内容空
                RD999999	8	请求发送文档数据
                RCsn/d/m	8	发送文档内容，第n次发送/本地发送文档的长度/文档总长度
                CT000000       8      表示有新来的要加入好友列表，内容里是新人的ip和port; 格式 ip:port
                FU000000       8      表示向服务器索取好友,内容为空，服务器会话也是FU，表示返回好友列表，内容是好友列表。ip:port;ip:port;...
                SN             8      请求登录
                
     空行		8	
     内容	aaaaaaaaaa		如果是文字包，内容部分就是具体的文字，如果是文档包，
                                        内容部分就是文档的文件类型：文件名称，
                                        1例如：doc:病毒一出论.doc  。 jepg:蝙蝠全家照.jpg
    用户名      xxxxxxxx          8   如果是登录请求，这里回填写用户名口令
    口令        xxxxxxxx          8   否则，这里的值无效
     结束	ok		

 * @author peng
 */
public class Protocol01  {
    private  final char[] flaghead = {'$','$','F','I','C','U','0','1'};
    private  char[] index = new char[8];
    private  char[] flagmsg = new char[2];
    private  char[] dataLen = new char[6];
    private  char[] spaces = new char[8];
    private  String content = "";
    private  char[] user = new char[8];
    private  char[] pwd = new char[8];
    private  final char[] theEnd = "ok".toCharArray();

   
     public String toStr(){
        StringBuffer sb = new StringBuffer();
        sb.append(getFlaghead());
        sb.append(getIndex());
        sb.append(getFlagmsg());
        sb.append(getDataLen());
        sb.append(getSpaces());
        sb.append(getContent());
        sb.append(getUser());
        sb.append(getPwd());
        sb.append(getTheEnd());
        return sb.toString();
    }

    /**
     * @return the flaghead
     */
    public char[] getFlaghead() {
        return flaghead;
    }

    /**
     * @return the index
     */
    public char[] getIndex() {
        return index;
    }

    /**
     * @param index the index to set
     */
    public void setIndex(char[] index) {
        this.index = index;
    }

    /**
     * @return the flagmsg
     */
    public char[] getFlagmsg() {
        return flagmsg;
    }

    /**
     * @param flagmsg the flagmsg to set
     */
    public void setFlagmsg(char[] flagmsg) {
        this.flagmsg = flagmsg;
    }

    /**
     * @return the dataLen
     */
    public char[] getDataLen() {
        return dataLen;
    }

    /**
     * @param dataLen the dataLen to set
     */
    public void setDataLen(char[] dataLen) {
        this.dataLen = dataLen;
    }

    /**
     * @return the spaces
     */
    public char[] getSpaces() {
        return spaces;
    }

    /**
     * @param spaces the spaces to set
     */
    public void setSpaces(char[] spaces) {
        this.spaces = spaces;
    }

    /**
     * @return the content
     */
    public String getContent() {
        return content;
    }

    /**
     * @param content the content to set
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * @return the user
     */
    public char[] getUser() {
        return user;
    }

    /**
     * @param user the user to set
     */
    public void setUser(char[] user) {
        this.user = user;
    }

    /**
     * @return the pwd
     */
    public char[] getPwd() {
        return pwd;
    }

    /**
     * @param pwd the pwd to set
     */
    public void setPwd(char[] pwd) {
        this.pwd = pwd;
    }

    /**
     * @return the theEnd
     */
    public char[] getTheEnd() {
        return theEnd;
    }
    
}
