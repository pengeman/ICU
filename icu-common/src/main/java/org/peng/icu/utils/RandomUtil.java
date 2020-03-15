package org.peng.icu.utils;


import java.util.Random;

/**
 * @ClassName RandomUtil
 * @Date 2020/3/14 19:35
 * @Author pengyifu
 */
public class RandomUtil {
    static public int randomIntRange(int from, int to) {
        return new Random().nextInt(from) % (to - from + 1) + from;
    }
}
