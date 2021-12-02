package com.showtime.serviceenergize.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.showtime.serviceenergize.entity.StEnergizeCommercial;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 书唐赋能商户管理表 Mapper 接口
 * </p>
 *
 * @author cjb
 * @since 2019-11-28
 */
public interface StEnergizeCommercialMapper extends BaseMapper<StEnergizeCommercial> {
    /**
     * 条件查询所有商户
     *
     * @param ctName 商户名称
     * @return 商户列表
     */
    List<StEnergizeCommercial> selectCommercialList(@Param("ctName") String ctName);

    /**
     * 查询商户是否存在
     *
     * @param ctIdentificationCode 商户号
     * @return
     */
    StEnergizeCommercial selectCommercialByCtId(@Param("ctIdentificationCode") String ctIdentificationCode);
}
