
package org.peng.icu.nettyserver;

import org.peng.icu.CLA.CommandLineArgs;
import org.peng.icu.CLA.Usage;


/**
 *
 * @author peng
 */
public class ServerBootStrap {

    /**
     * @param args the command line arguments
     */
    public void run(String[] args) throws Exception {
        //ChineseProverbServer.main(args);
        Usage usage = new Usage();
        usage.set("h", "help", 0, "查看本帮助文档",help);
        usage.set("p", "port", 1, "设置监听的端口号",null);
        
        CommandLineArgs cla = new CommandLineArgs();
        cla.setUsage(usage);
        cla.parse(args);
        String v = cla.getArg("h");
        if (v != null){
            cla.print();
            return;
        }
        v = cla.getArg("p");
        if (v != null){
            int port = Integer.parseInt(v);
            //com.peng.icu.server.EchoServer server = new EchoServer(port);
            ChineseProverbServer server = new ChineseProverbServer(port);
            server.start();
        }else{
            cla.print(); // 打印帮助信息
            return;
        }
        
        //com.peng.icu.server.EchoServer.main(args);
        System.out.println("has started ...");
    }

    /* todo:wait */
    Usage.UsageFunction help = value -> {

    };

    public static void main(String[] args) throws Exception {
        new ServerBootStrap().run(args);
    }
}
