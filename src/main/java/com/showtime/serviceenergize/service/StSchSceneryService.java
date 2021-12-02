package com.showtime.serviceenergize.service;

import com.github.pagehelper.PageInfo;
import com.showtime.serviceenergize.entity.StSchScenery;

import java.util.List;

/**
 * @Author wz
 * @Date 2019/10/11 15:14
 * @Description //TODO 校园风光的service
 */

public interface StSchSceneryService {
    /**
     * 根据学校ID查询校园风光信息
     * @param schId
     * @return
     */
    List<StSchScenery> selectListStSchSceneryBySchoolId(Integer schId);
}
