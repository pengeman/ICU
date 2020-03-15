import org.peng.icu.nettyclient.ClientBootStrap
import org.peng.icu.nettyserver.ServerBootStrap


object TestServer {
    def main(args: Array[String]): Unit = {
        val a: Array[String] = "-p 8888".split(" ")
        ServerBootStrap.main(a)

    }
}

object  TestClientA{
    def main(args: Array[String]): Unit = {
        val a = Array(
//            "127.0.0.1",
            "sucicada.tk",
            "8888",
            "peng",
            "pengeman")
        ClientBootStrap.main(a);
    }
}

object  TestClientB{
    def main(args: Array[String]): Unit = {
        val a = Array(
            "127.0.0.1",
            "8888",
            "clientB",
            "pengeman")
        ClientBootStrap.main(a);
    }
}