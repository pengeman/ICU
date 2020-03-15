import org.peng.icu.protocol.Protocol01;

/**
 * @ClassName Test
 * @Date 2020/3/14 23:03
 * @Author pengyifu
 */
public class Test {
    public static void main(String[] args) {
        Protocol01 p = new Protocol01();
        p.setContent("sdfsdfsdfdsfsdfsdfsd");
        System.out.println(p.toStr());
    }
}
