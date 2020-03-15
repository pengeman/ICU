package org.peng.icu.CLA;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 命令行功能
 *
 * @author peng 2020/1/22
 */
public class CommandLineArgs {

    Usage usage = null;
    String version = "version:1.0 pengweitao 2020/1/22";
    Map<String, String> argMap = new HashMap();

    /**
     * 打印全部参数信息
     */
    public void print() {
        if (usage == null) {
            return;
        }
        List<String[]> args = usage.getAll();
        println(version);
        for (String[] arg : args) {
            System.out.print(" -");
            System.out.print(arg[0] + " ");
            System.out.print(arg[1] + " ");
            System.out.print(arg[3]);

            System.out.println();
        }
    }

    /**
     * 解析参数。首先检查参数正确，提示无效参数<br/>
     * 将参数解析成键值对
     * 如果事单独单词的参数, 就将其值定为 -
     *
     * @param args    命令行参数
     * @param argList 命令行参数用法说明
     * @return int, 1:执行成功,参数没有错误；0：执行成功，参数有错误; -1:执行失败
     */
    public int parse(String[] args, List<String[]> argList) {
        int j = 1;
        for (int i = 0; i < args.length; i = i + j) {
            String arg = args[i];
            // 取出参数对应的值and yn
            String[] vs = findVYN(arg, argList);
            if (vs == null) return -1;
            if (vs[0].equals("-1")) {
                // 参数错误，打印报告
                report(arg);
                this.argMap.clear();
                return 0;
            }
            //this.argMap.put(arg, vs[0]);
            if (vs[1].split(",")[1].equals("1")) {
                j = 2;
                this.argMap.put(arg, args[i + 1]);
            } else {
                this.argMap.put(arg, "-");
                j = 1;
            }

        }
        return 1;
    }

    public int parse(String[] args) {
        if (usage == null) {
            return -1;
        }
        List usageList = usage.getAll();
        return this.parse(args, usageList);
    }

    /**
     * 参数数量,先做参数解析，解析完，才能知道参数的数量
     */
    public int len() {
        return this.argMap.size();
    }

    /**
     * 取得一个参数值
     *
     * @param key ，参数
     * @return 参数值, 返回null，表示没有这个参数key
     */
    public String getArg(String key) {
        String v1 = null;

        v1 = argMap.get("-" + key);

        return v1;
    }

    public void setUsage(Usage u) {
        usage = u;
    }

    private void println(String v) {
        System.out.println(v);
    }

    /**
     * 根据参数key，查找对应的值以及对应的posix格式的参数key
     *
     * @param arg
     * @param argList
     * @return 找到的posix及值，以逗号分割, 如果没有找到，返回-1
     */
    private String findValue(String arg, List<String[]> argList) {
        //System.out.println("arg:" + arg);
        for (String[] usage : argList) {
            if (arg.equals("-" + usage[0]) || arg.equals("--" + usage[1])) {
                return usage[0] + "," + usage[3];
            }
        }
        return "-1";
    }

    private String findYN(String arg, List<String[]> argList) {
        for (String[] usage : argList) {
            if (arg.equals("-" + usage[0]) || arg.equals("--" + usage[1])) {
                return usage[0] + "," + usage[2];
            }
        }
        return "-1";
    }

    private String[] findVYN(String arg, List<String[]> argList) {
        for (String[] usage : argList) {
            if (arg.equals("-" + usage[0]) || arg.equals("--" + usage[1])) {
                String s1 = usage[0] + "," + usage[3];
                String s2 = usage[0] + "," + usage[2];
                return new String[]{s1, s2};
            }
        }
        return new String[]{"-1", "-1"};
    }

    private void report(String arg) {
        System.out.println(arg + " 参数不存在，请实用-h 或 --help 参数查看使用帮助");
    }
}
