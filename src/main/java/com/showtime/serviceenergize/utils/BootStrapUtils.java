package com.showtime.serviceenergize.utils;

import com.baomidou.mybatisplus.core.metadata.IPage;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 胡佳奇
 *
 * @date 11/14/2018 13:38
 *
 * @description  封装返回给bootStrap的数据
 */
public class BootStrapUtils {


    /**
     * 返回给bootStrap的json  需要包含 total   rows  不能不能正确渲染  注：服务端分页
     * @param total 总条数
     * @param list 当前页的数据
     * @return
     */
    public static JSONObject initServerJson(long total, List list){
        Map map = new HashMap();
        map.put("rows", JSONArray.fromObject(list));
        map.put("total",total);
        String mapStr = map.toString().replaceAll("null", "\"\"");
        return JSONObject.fromObject(mapStr);
    }


    /**
     * 初始化mybatisPlus 的值  服务端分页渲染
     * @param iPage 请求成功后的值
     * @return
     */
    public static JSONObject initServerJsonByIpage(IPage iPage){
        if (iPage.getRecords() != null && iPage.getRecords().size() > 0){
            return initServerJson(iPage.getTotal(),iPage.getRecords());
        }
        return initServerJson(0,new ArrayList());

    }

    /**
     * 返回给bootStrap的json    注：客户端分页
     * @param list 当前页的数据
     * @return
     */
    public static JSONArray initServerJson(List list){
        return JSONArray.fromObject(list);
    }

}
