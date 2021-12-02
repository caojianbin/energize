package com.showtime.serviceenergize.utils;

import com.github.pagehelper.util.StringUtil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @program: com.showtime.serviceenergize.utils
 * @description: /TDD 字符串验证
 * @author: cjb
 * @create: 2020-04-02 16:13
 */
public class StringUtils {
    static String reg = "(?:')|(?:--)|(/\\*(?:.|[\\n\\r])*?\\*/)|"
            + "(\\b(select|update|and|or|delete|insert|trancate|char|into|substr|ascii|declare|exec|count|master|into|drop|execute|html|script)\\b)";

    //表示忽略大小写
    static Pattern sqlPattern = Pattern.compile(reg, Pattern.CASE_INSENSITIVE);


    /**
     * 参数校验
     *
     * @param str ep: "or 1=1"
     */
    public static boolean isSqlValid(String str) {
        Matcher matcher = sqlPattern.matcher(str);
        if (matcher.find()) {
            //获取非法字符：or
            System.out.println("参数存在非法字符，请确认：" + matcher.group());
            return false;
        }
        return true;
    }

    /**
     * 判断string是否为1或者2
     *
     * @param str
     * @return
     */
    public static boolean isNumeric(Integer str) {
            if (str == 1 || str == 2) {
                return true;
        }
        return false;
    }

}
