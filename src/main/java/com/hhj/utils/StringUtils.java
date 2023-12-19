package com.hhj.utils;

/**
 * ClassName: StringUtils
 * Package: com.hhj.utils
 * Description:
 *  对字符串进行检查
 * @Author honghuaijie
 * @Create 2023/12/16 19:06
 * @Version 1.0
 * Yesterday is history,tomorrow is a mystery,
 * but today is a gift.That is why it's called the present
 */
public class StringUtils {


    /**
     * 判断传入的字符串是否为空
     * 字符串为空就返回true
     * 不为空就返回false
     * @param str
     * @return
     */
    public static Boolean isEmpty(String str){
        return (str == null) || (str.equals(""));
    }

    /**
     * 判断传入的字符串不为空
     * 字符串不为空就返回true
     * 为空就返回false
     * @param str
     * @return
     */
    public static Boolean isNotEmpty(String str){
        return !isEmpty(str);
    }
}
