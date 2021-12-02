package com.showtime.serviceenergize.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.showtime.serviceenergize.entity.StEnergizeOrder;
import net.sf.json.JSONObject;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 书唐赋能订单表 服务类
 * </p>
 *
 * @author cjb
 * @since 2019-11-28
 */
public interface StEnergizeOrderService extends IService<StEnergizeOrder> {

    /**
     * 根据条件查询订单数据
     *
     * @param ctName    商户名称
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @param page      分页信息
     * @return List结果集
     */
    JSONObject slelcOrdertListByCondition(Page page, String ctName, String startTime, String endTime);

    /**
     * 统计金额
     *
     * @return map结果集
     */
    Map<String, BigDecimal> countEnergizeMoney();
}
