import scala.collection.mutable

/**
 * @ClassName sss
 * @Date 2020/3/15 1:31
 * @Author pengyifu
 */
object sss {
    def main(args: Array[String]): Unit = {
        val map = mutable.Map[String,String]()
        map("peng")= "321234"
        println(map)
        map("peng")= "er4etert"
        println(map)

    }
}
