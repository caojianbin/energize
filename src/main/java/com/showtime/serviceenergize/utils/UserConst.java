package com.showtime.serviceenergize.utils;

/**
 * @ClassName : UserConst
 * @Description : 用户相关常量类
 * @Author : jingliping
 * @Date: 2019-11-04 17:07
 */
public class UserConst {

    /**
     * 超级管理员
     */
    public static final String CJGLY= "cjgly";

    /**
     * 运维人员
     */
    public static final String YWRY= "ywry";

    /**
     * 高校用户
     */
    public static final String GXYH= "gxyh";


    /**
     * redis-OK
     */
    public final static String OK = "OK";

    /**
     * redis过期时间，以秒为单位，一分钟
     */
    public final static int EXRP_MINUTE = 60;

    /**
     * redis过期时间，以秒为单位，一小时
     */
    public final static int EXRP_HOUR = 60 * 60;

    /**
     * redis过期时间，以秒为单位，一天
     */
    public final static int EXRP_DAY = 60 * 60 * 24;

    /**
     * redis-key-前缀-shiro:cache:
     */
    public final static String PREFIX_SHIRO_CACHE = "shiro:cache:";

    /**
     * redis-key-前缀-shiro:access_token:
     */
    public final static String PREFIX_SHIRO_ACCESS_TOKEN = "shiro:access_token:";

    /**
     * redis-key-前缀-shiro:refresh_token:
     */
    public final static String PREFIX_SHIRO_REFRESH_TOKEN = "shiro:refresh_token:";

    /**
     * redis-key-前缀-shiro:refresh_pre_token:
     */
    public final static String PREFIX_SHIRO_PRE_REFRESH_TOKEN = "shiro:refresh_pre_token:";

    /**
     * JWT-account:
     */
    public final static String USER_PHONE = "user_phone";

    /**
     * JWT-currentTimeMillis:
     */
    public final static String CURRENT_TIME_MILLIS = "currentTimeMillis";

    /**
     * PASSWORD_MAX_LEN
     */
    public static final Integer PASSWORD_MAX_LEN = 8;

    /**
     *  请求头
     */
    public static final String AUTHORIZATION= "Authorization";

    /**
     * redis-key-前缀-procode_: 省份
     */
    public final static String PROCODE_PROVINCES = "procode_provinces";

    /**
     * redis-key-前缀-procode_: 市区
     */
    public final static String PROCODE_CITYS = "procode_citys";

    /**
     * redis-key-前缀-procode_: 区域
     */
    public final static String PROCODE_AREAS = "procode_areas";
}
