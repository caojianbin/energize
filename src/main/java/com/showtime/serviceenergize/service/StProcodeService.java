package com.showtime.serviceenergize.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.showtime.serviceenergize.entity.StProcode;

import java.util.List;

/**
 * <p>
 * 书唐省市区代码表 服务类
 * </p>
 *
 * @author cjb
 * @since 2020-01-17
 */
public interface StProcodeService extends IService<StProcode> {

    /**
     * 获取所有省份
     *
     * @return
     */
    List<StProcode> getProvinces();

    /**
     * 获取所有市
     *
     * @return
     */
    List<StProcode> getCitys();

    /**
     * 获取所有区域
     *
     * @return
     */
    List<StProcode> getAreas();

}
