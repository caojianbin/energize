package com.showtime.serviceenergize.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.showtime.serviceenergize.entity.StEnergizeUserUnlockSchool;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 商户用户解锁时限表 Mapper 接口
 * </p>
 *
 * @author cjb
 * @since 2019-11-28
 */
public interface StEnergizeUserUnlockSchoolMapper extends BaseMapper<StEnergizeUserUnlockSchool> {
    /**
     * 查询用户购买记录
     *
     * @param userNumber           用户账号
     * @param ctIdentificationCode 商户号
     * @return
     */
    List<StEnergizeUserUnlockSchool> selectUserUnlockSchool(@Param("userNumber") String userNumber, @Param("ctIdentificationCode") String ctIdentificationCode);
}
