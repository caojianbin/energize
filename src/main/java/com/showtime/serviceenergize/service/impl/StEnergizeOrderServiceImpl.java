package com.showtime.serviceenergize.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.showtime.serviceenergize.entity.StEnergizeOrder;
import com.showtime.serviceenergize.entity.dto.StEnergizeOrderVo;
import com.showtime.serviceenergize.entity.vo.EnergizeOrderVo;
import com.showtime.serviceenergize.mapper.StEnergizeOrderMapper;
import com.showtime.serviceenergize.service.StEnergizeOrderService;
import com.showtime.serviceenergize.utils.BootStrapUtils;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 书唐赋能订单表 服务实现类
 * </p>
 *
 * @author cjb
 * @since 2019-11-28
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class StEnergizeOrderServiceImpl extends ServiceImpl<StEnergizeOrderMapper, StEnergizeOrder> implements StEnergizeOrderService {

    /**
     * 书唐赋能订单表 Mapper
     */
    @Autowired
    private StEnergizeOrderMapper orderMapper;

    /**
     * 根据条件查询订单数据
     *
     * @param ctName    商户名称
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @param page      分页信息
     * @return List结果集
     */
    @Override
    public JSONObject slelcOrdertListByCondition(Page page, String ctName, String startTime, String endTime) {
        PageHelper.startPage((int) page.getCurrent(), (int) page.getSize());
        List<StEnergizeOrderVo> list = orderMapper.selectOrdertListByCondition(ctName, startTime, endTime);
        PageInfo<StEnergizeOrderVo> pageInfo = new PageInfo<>(list);
        return BootStrapUtils.initServerJson(pageInfo.getTotal(), pageInfo.getList());
    }

    /**
     * 统计金额
     *
     * @return map结果集
     */
    @Override
    public Map<String, BigDecimal> countEnergizeMoney() {
        HashMap money = new HashMap();
        EnergizeOrderVo lastMonthMoeny = orderMapper.counLastMonthMoeny();
        EnergizeOrderVo sumMoney = orderMapper.countMoney();
        EnergizeOrderVo tisMonthMoeny = orderMapper.countTisMonthMoeny();
        if (lastMonthMoeny == null) {
            money.put("lastMonthMoeny", 0);
        } else {
            money.put("lastMonthMoeny", (lastMonthMoeny.getLastMonthMoney() == null ? 0 : lastMonthMoeny.getLastMonthMoney().setScale(2, BigDecimal.ROUND_HALF_UP)));
        }
        if (sumMoney == null) {
            money.put("sumMoney", 0);
        } else {
            money.put("sumMoney", (sumMoney.getMoney() == null ? 0 : sumMoney.getMoney().setScale(2, BigDecimal.ROUND_HALF_UP)));
        }
        if (tisMonthMoeny == null) {
            money.put("tisMonthMoeny", 0);
        } else {
            money.put("tisMonthMoeny", (tisMonthMoeny.getTisMonthMoney() == null ? 0 : tisMonthMoeny.getTisMonthMoney().setScale(2, BigDecimal.ROUND_HALF_UP)));
        }
        //换算环比（本期数-上期数）/上期数×100%
//        BigDecimal tisMonthMoney = tisMonthMoeny.getTisMonthMoney().setScale(2,BigDecimal.ROUND_HALF_UP);
//        BigDecimal lastMonthMoney = lastMonthMoeny.getLastMonthMoney().setScale(2,BigDecimal.ROUND_HALF_UP);
//        BigDecimal multiply = (tisMonthMoney.subtract(lastMonthMoney)).divide(lastMonthMoney,20,BigDecimal.ROUND_HALF_UP);
//        int mom = multiply.intValue() * 100;
//        money.put("mom",mom+"%");
        return money;
    }
}
