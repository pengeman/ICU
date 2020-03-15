package org.peng.icu.user

import scala.collection.mutable
import scala.collection.mutable.Map
import java.{util => ju}
import collection.JavaConversions._

/**
 * @ClassName ClientRoom
 * @Date 2020/3/8 16:12
 * @Author pengyifu
 */
object ClientRoom {
    private val clients: mutable.Map[String, Client] = Map()


    def add(client: Client): Unit = {
        clients(client.getUsername) = client
    }

    def get(userName: String): Client = {
        if(clients.contains(userName))
            clients(userName)
        else
            null
    }

    def list() = {
        clients.map(_._2)
    }

    def getList(): ju.List[Client] = {
        list.toList
    }

    def clear() = {
        clients.clear()
    }
}
