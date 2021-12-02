package com.showtime.serviceenergize.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.showtime.serviceenergize.entity.StEnergizeCommercial;
import com.showtime.serviceenergize.entity.vo.StEnergizeCommercialVo;
import net.sf.json.JSONObject;

/**
 * <p>
 * 书唐赋能商户管理表 服务类
 * </p>
 *
 * @author cjb
 * @since 2019-11-28
 */
public interface StEnergizeCommercialService extends IService<StEnergizeCommercial> {

    /**
     * 根据条件全部查询
     *
     * @param ctName 商户名称
     * @param page   分页信息
     * @return 商户集合
     */
    JSONObject selctCommercialList(Page page, String ctName);

    /**
     * 添加赋能商户
     *
     * @param  commercialVo 商户信息
     * @return 成功条件
     */
    Integer addEnergize(StEnergizeCommercialVo commercialVo);

    /**
     * 修改赋能商户
     *
     * @param commercialVo 商户信息
     * @param ctId 商户id
     * @return 成功条件
     */
    Integer updateEnergize(String ctId,StEnergizeCommercialVo commercialVo);

    /**
     * 根据id修改用户状态商户
     *
     * @param ctId 商户id
     * @return 成功条件
     */
    Integer delEnergize(String ctId,Integer ctStatus);
}
