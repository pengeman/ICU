package org.peng.icu.utils;

/**
 * @ClassName StringUtil
 * @Date 2020/3/14 17:05
 * @Author pengyifu
 */
public class StringUtil {
    /**
     * 如果null, 返回空字符串
     */
    public static String checkNull(String a) {
        if (a == null) {
            return "";
        }
        return a;
    }
}
