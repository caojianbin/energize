package com.showtime.serviceenergize.utils;

/**
 * @Author xrj
 * @Date 2019/1/30 14:05
 * @Description  redis缓存的key 描述
 */
public class SchoolRedisKey {

    /**
     * 10天(差一点)方便重新设置
     */
    public static final Long TEN_DAY=863000L;

   /**
    * 书唐省市区代码表 省份KEY
    */
   public static final String ST_PROCODE_PROVINCE="st_procode_province";

    /**
     * 书唐省市区代码表 城市KEY
     */
    public static final String ST_PROCODE_CITY="st_procode_city";

    /**
     * 书唐省市区代码表 区KEY
     */
    public static final String ST_PROCODE_AREA="st_procode_area";

    /**
     * 书唐全部地址
     */
    public static final String ST_PROCODE_LIST="st_procode_list";

    /**
     * 招生代碼
     */
    public static final String SCHOOL_CODING_KEY="school_coding_key";



}
