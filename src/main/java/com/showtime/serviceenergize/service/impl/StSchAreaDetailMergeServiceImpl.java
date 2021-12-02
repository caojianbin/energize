package com.showtime.serviceenergize.service.impl;

import com.showtime.common.utils.StringUtils;
import com.showtime.serviceenergize.entity.StEnergizeUserUnlockSchool;
import com.showtime.serviceenergize.entity.StSchAlumni;
import com.showtime.serviceenergize.entity.StSchArea;
import com.showtime.serviceenergize.entity.StSchScenery;
import com.showtime.serviceenergize.entity.vo.StSchMajorVo;
import com.showtime.serviceenergize.entity.vo.StaDressVo;
import com.showtime.serviceenergize.mapper.StSchAreaSlaveMapper;
import com.showtime.serviceenergize.mapper.StSchMajorSlaveMapper;
import com.showtime.serviceenergize.service.*;
import com.showtime.serviceenergize.utils.SchoolConst;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author zs
 * @Date 2019/2/23 15:00
 * @Description //TODO 高校详情页面整合（知名校友，动态文章，同学录）的实现类
 */
@Transactional(rollbackFor = Exception.class)
@Slf4j
@Service
@SuppressWarnings("unchecked")
public class StSchAreaDetailMergeServiceImpl implements StSchAreaDetailMergeService {

    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private StSchAreaSlaveMapper stSchAreaSlaveMapper;
    @Autowired
    private StSchAlumniService alumniService;
    @Autowired
    private StSchSceneryService stSchSceneryService;
    @Autowired
    private StSchMajorSlaveMapper stSchMajorSlaveMapper;
    @Autowired
    private StEnergizeUserUnlockSchoolService userUnlockSchoolService;

    /**
     * 根据学校ID和用户ID查询学校详情页
     *
     * @param schId                学校id
     * @param userNumber           商户用户号
     * @param ctIdentificationCode 商户号
     * @return
     */
    @Override
    public StSchArea selectStSchAreaDetail(Integer schId, String userNumber, String ctIdentificationCode) {

        //获取高校主校区信息
        StSchArea stSchArea = stSchAreaSlaveMapper.selectAreaDetailById(schId);
        if (stSchArea == null) {
            return null;
        }
        //获取地址
        StaDressVo staDressVo = stSchAreaSlaveMapper.selectDressBySaId(stSchArea.getSaId());
        if (staDressVo != null) {
            //判断 省 市 是否是空
            if (staDressVo.getStProvinceName() == null || staDressVo.getStCityName() == null) {
                assemblyAddress(staDressVo, stSchArea);
            } else {
                if (staDressVo.getStProvinceName().equals(staDressVo.getStCityName())) {
                    staDressVo.setStCityName(null);
                }
                assemblyAddress(staDressVo, stSchArea);
            }
        }
        //获取校友列表
        List<StSchAlumni> stSchAlumni = alumniService.selectListStSchAlumniBySchoolId(stSchArea.getSchId());
        //获取校园风光列表
        List<StSchScenery> stSchScenery = stSchSceneryService.selectListStSchSceneryBySchoolId(stSchArea.getSaId());

        //整合数据
        stSchArea.setAlumni(stSchAlumni);
        stSchArea.setScenery(stSchScenery);
        unlock(stSchArea, schId);

        if (null != userNumber && null != ctIdentificationCode) {
            //根据用户查询解锁学校的列表
            List<StEnergizeUserUnlockSchool> userUnlockSchool = userUnlockSchoolService.getUserUnlockSchool(userNumber, ctIdentificationCode);
            List<StEnergizeUserUnlockSchool> unlockSchools = new ArrayList<>();
            userUnlockSchool.forEach(e -> {
                Long endTime = Long.valueOf(e.getSeuEndTime());
                if (endTime > System.currentTimeMillis()) {
                    unlockSchools.add(e);
                }
            });
            if (stSchArea != null) {
                unlockSchools.forEach(lock -> {
                    //如果相等则表明已解锁
                    if (lock.getSchId().equals(stSchArea.getSchId())) {
                        stSchArea.setUnlockStatus(1);
                    }
                });
            }
        }
        return stSchArea;
    }

    /**
     * 剔出组装地址
     *
     * @return
     */
    private void assemblyAddress(StaDressVo staDressVo, StSchArea stSchArea) {
        //拼接地址，根据三元运算，地址为空则设为字符串
        String address = (staDressVo.getStProvinceName() == null ? "" : staDressVo.getStProvinceName()) + (staDressVo.getStCityName() == null ? "" : staDressVo.getStCityName()) + (staDressVo.getStAreaName() == null ? "" : staDressVo.getStAreaName()) + (staDressVo.getAdress() == null ? "" : staDressVo.getAdress());
        if (!StringUtils.isEmpty(address)) {
            address = address.replace(SchoolConst.ADDRESS_NAME_MAINCITY, "");
        }
        stSchArea.setStAddres(address);//完整地址
        String city = (staDressVo.getStCityName() == null ? "" : staDressVo.getStCityName()) + (staDressVo.getStAreaName() == null ? "" : staDressVo.getStAreaName());
        if (!StringUtils.isEmpty(city)) {
            city = city.replace(SchoolConst.ADDRESS_NAME_MAINCITY, "");
        }
        stSchArea.setStCity(city);//所在城市
    }

    /**
     * 已激活
     *
     * @param stSchArea
     * @return
     */
    private void unlock(StSchArea stSchArea, Integer schId) {
        //获取优势专业
//            List<StSchMajor> stSchMajors = stSchMajorSlaveMapper.selectAdvantageStSchMajorListBySchId(stSchArea.getSchId());
        //暂用
        List<StSchMajorVo> stSchMajorVos = stSchMajorSlaveMapper.selectMajorNameList(schId);
        stSchArea.setAdvSpe(stSchMajorVos);
    }
}
