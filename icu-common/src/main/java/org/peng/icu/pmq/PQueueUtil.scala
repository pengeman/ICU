package org.peng.icu.pmq

import org.peng.icu.protocol.Protocol01

/**
 * @ClassName PQueueBuilder
 * @Date 2020/3/14 16:20
 * @Author pengyifu
 */
object PQueueUtil {
    private val MAX_LENGTH = 10
    private val pq_toServer: PQueue = new PQueue(MAX_LENGTH)
    private val pq_fromServer: PQueue = new PQueue(MAX_LENGTH)

    def getPQToServer(): PQueue = {
        pq_toServer
    }

    def getPQFromSerber() = {
        pq_fromServer
    }

    def send(p: Protocol01) = {
        getPQToServer.put(p.toStr)
    }

}
