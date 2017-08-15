package com.news.chenhao.android.com.haonews.until;

import java.util.List;
import java.util.Map;

/**
 * 集合工具类
 */
public class CollectionsUtil {
    //这个工具类用来比对List集合数组和Map是否为空的判断
    public static boolean isEmpty(List list) {
        return (list == null || list.isEmpty());
    }

    public static boolean isNotEmpty(List list) {
        return !isEmpty(list);
    }

    public static boolean isEmpty(Map map) {
        return (map == null || map.isEmpty());
    }

    public static boolean isNotEmpty(Map map) {
        return !isEmpty(map);
    }

    public static <T> boolean isEmpty(T[] t) {
        return (t == null || t.length == 0);
    }

    public static <T> boolean isNotEmpty(T[] t) {
        return !isEmpty(t);
    }
}