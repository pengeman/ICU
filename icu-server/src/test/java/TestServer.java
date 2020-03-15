import org.peng.icu.nettyserver.ServerBootStrap;

/**
 * @ClassName TestServer
 * @Date 2020/2/29 15:46
 */
public class TestServer {
    public static void main(String[] args) throws Exception {
        String[] a = "-p 8888".split(" ");
        ServerBootStrap.main(a);
    }
}
