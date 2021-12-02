package com.showtime.serviceenergize.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.showtime.serviceenergize.entity.StEnergizeUserUnlockSchool;

import java.util.List;

/**
 * <p>
 * 商户用户解锁时限表 服务类
 * </p>
 *
 * @author cjb
 * @since 2019-11-28
 */
public interface StEnergizeUserUnlockSchoolService extends IService<StEnergizeUserUnlockSchool> {

    /**
     * 查询用户购买记录
     *
     * @param userNumber 用户账号
     * @param ctId       商户号
     * @return
     */
    List<StEnergizeUserUnlockSchool> getUserUnlockSchool(String userNumber, String ctId);
}
