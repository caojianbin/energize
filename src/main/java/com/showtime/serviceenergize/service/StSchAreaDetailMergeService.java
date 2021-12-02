package com.showtime.serviceenergize.service;

import com.showtime.serviceenergize.entity.StSchArea;

/**
 * @Author zs
 * @Date 2019/2/23 14:59
 * @Description //TODO 高校详情页面整合（知名校友，动态文章，同学录）service
 */

public interface StSchAreaDetailMergeService {

    /**
     * 根据学校ID和用户ID查询学校详情页
     *
     * @param schId                学校id
     * @param userNumber           商户用户号
     * @param ctIdentificationCode 商户号
     * @return
     */
    StSchArea selectStSchAreaDetail(Integer schId, String userNumber, String ctIdentificationCode);
}
