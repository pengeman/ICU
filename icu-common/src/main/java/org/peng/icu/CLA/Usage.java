
package org.peng.icu.CLA;

import java.util.ArrayList;
import java.util.List;

/**
 * 一套命令行参数集
 *
 * @author peng 2020/1/22
 */
public class Usage {
    List<String[]> args = new ArrayList();
    String[] arg = new String[4];

    /*    todo:wait 2  */
    public interface UsageFunction {
        void run(String value);
    }

    public void set(String posix, String gnu, int yn, String value, UsageFunction function) {
        set(posix, gnu, yn, value);
    }

    /**
     * @param posix posix标准, 缩写
     * @param gnu   gnu标准, 全拼
     * @param yn    0: 没有后续参数, 1: 有后续参数
     * @param value 注释信息
     */
    public void set(String posix, String gnu, int yn, String value) {
        arg[0] = posix;
        arg[1] = gnu;
        arg[2] = yn + "";
        arg[3] = value;
        String[] a2 = arg.clone();
        args.add(a2);

//        function
    }

    public List<String[]> getAll() {
        return args;
    }

}
