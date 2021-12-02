package com.showtime.serviceenergize.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.showtime.serviceenergize.entity.StEnergizeUserUnlockSchool;
import com.showtime.serviceenergize.mapper.StEnergizeUserUnlockSchoolMapper;
import com.showtime.serviceenergize.service.StEnergizeUserUnlockSchoolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 商户用户解锁时限表 服务实现类
 * </p>
 *
 * @author cjb
 * @since 2019-11-28
 */
@Service
public class StEnergizeUserUnlockSchoolServiceImpl extends ServiceImpl<StEnergizeUserUnlockSchoolMapper, StEnergizeUserUnlockSchool> implements StEnergizeUserUnlockSchoolService {
    @Autowired
    private StEnergizeUserUnlockSchoolMapper mapper;

    /**
     * 查询用户购买记录
     *
     * @param userNumber           用户账号
     * @param ctIdentificationCode 商户号
     * @return
     */
    @Override
    public List<StEnergizeUserUnlockSchool> getUserUnlockSchool(String userNumber, String ctIdentificationCode) {
        return mapper.selectUserUnlockSchool(userNumber, ctIdentificationCode);
    }
}
