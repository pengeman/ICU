/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.peng.icu.user;

import java.util.Date;

/**
 * @author peng
 */
public class Client {
    private String username;
    private String pwd;
    private String anonymous;
    private String ip;
    private String port;
    private java.net.InetSocketAddress netAddr;
    private Date signinTime;

    public Client() {
    }

    /**
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return the pwd
     */
    public String getPwd() {
        return pwd;
    }

    /**
     * @param pwd the pwd to set
     */
    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    /**
     * @return the anonymous
     */
    public String getAnonymous() {
        return anonymous;
    }

    /**
     * @param anonymous the anonymous to set
     */
    public void setAnonymous(String anonymous) {
        this.anonymous = anonymous;
    }

    /**
     * @return the ip
     */
    public String getIp() {
        return ip;
    }

    /**
     * @param ip the ip to set
     */
    public void setIp(String ip) {
        this.ip = ip;
    }

    /**
     * @return the port
     */
    public String getPort() {
        return port;
    }

    /**
     * @param port the port to set
     */
    public void setPort(String port) {
        this.port = port;
    }

    /**
     * @return the signinTime
     */
    public Date getSigninTime() {
        return signinTime;
    }

    /**
     * @param signinTime the signinTime to set
     */
    public void setSigninTime(Date signinTime) {
        this.signinTime = signinTime;
    }

    /**
     * @return the netAddr
     */
    public java.net.InetSocketAddress getNetAddr() {
        return netAddr;
    }

    /**
     * @param netAddr the netAddr to set
     */
    public void setNetAddr(java.net.InetSocketAddress netAddr) {
        this.netAddr = netAddr;
    }


    @Override
    public String toString() {
        return "Client{" +
                "username='" + username + '\'' +
                ", pwd='" + pwd + '\'' +
                ", anonymous='" + anonymous + '\'' +
                ", ip='" + ip + '\'' +
                ", port='" + port + '\'' +
                ", netAddr=" + netAddr +
                ", signinTime=" + signinTime +
                '}';
    }
}
