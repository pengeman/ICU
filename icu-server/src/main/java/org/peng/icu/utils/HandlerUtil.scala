package org.peng.icu.utils

import org.apache.log4j.Logger
import org.peng.icu.user.{Client, ClientRoom}

/**
 * @ClassName HandlerUtil
 * @Date 2020/3/8 16:33
 * @Author pengyifu
 */
class HandlerUtil

object HandlerUtil {
    val log: Logger = Logger.getLogger(classOf[HandlerUtil])

//    def requestFU(ctx: ChannelHandlerContext, packet: DatagramPacket): String = {
//        requestFU(packet.sender())
//    }

    def requestFU(client:Client): String = {
        val ip = client.getIp
        val port = client.getPort
        log.info(ip + ":" + port + " 客户端发来FU")

        val clientList = ClientRoom.list()

        val res = clientList
            .filter(client => {
                !((ip equals client.getIp) && (port equals client.getPort))
            })
            ./:("")((str, client: Client) => {
                s"${str}${client.getUsername}:${client.getAnonymous}:${client.getIp}:${client.getPort};"
            })
        res
    }
}

//    def requestTO(protocol:Protocol01) = {
//        val touchClient = ProtocolUtil.parseTouch(protocol).getTouchUsername
//        val a2 = chatRoom.get(touchClient).asInstanceOf[InetSocketAddress]
//        val a1 = packet.sender
//
//        log.info("Touch 命令 :" + a1 + " -> " + a2)
//
//        var touch = makeTouch(a2) // 生成一个touch a2 的touch协议
//        var remot = touch
//        this.replay(remot, a1, ctx)
//
//        log.info("Touch 命令 :" + a2 + " -> " + a1)
//        touch = makeTouch(a1)
//        remot = touch
//        this.replay(remot, a2, ctx)
//    }
//
//}
