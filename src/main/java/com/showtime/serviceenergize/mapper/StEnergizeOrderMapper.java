package com.showtime.serviceenergize.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.showtime.serviceenergize.entity.StEnergizeOrder;
import com.showtime.serviceenergize.entity.dto.StEnergizeOrderVo;
import com.showtime.serviceenergize.entity.vo.EnergizeOrderVo;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

/**
 * <p>
 * 书唐赋能订单表 Mapper 接口
 * </p>
 *
 * @author cjb
 * @since 2019-11-28
 */
public interface StEnergizeOrderMapper extends BaseMapper<StEnergizeOrder> {

    /**
     * 根据条件查询订单数据
     *
     * @param ctName    商户名称
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return List结果集
     */
    List<StEnergizeOrderVo> selectOrdertListByCondition(@Param("ctName") String ctName, @Param("startTime") String startTime, @Param("endTime") String endTime);

    /**
     * 统计金额
     *
     * @return
     */
    EnergizeOrderVo countMoney();

    /**
     * 统计本月收入
     *
     * @return
     */
    EnergizeOrderVo countTisMonthMoeny();

    /**
     * 统计上月收入
     *
     * @return
     */
    EnergizeOrderVo counLastMonthMoeny();
}
