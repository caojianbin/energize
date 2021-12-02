package com.showtime.serviceenergize.service;

import com.github.pagehelper.PageInfo;
import com.showtime.serviceenergize.entity.StSchAlumni;

import java.util.List;

/**
 * @Author zs
 * @Date 2019/2/13 15:55
 * @Description //TODO 知名校友的service
 */

public interface StSchAlumniService {
    /**
     * 根据学校ID查询知名校友信息
     * @param schoolId
     * @return
     */
    List<StSchAlumni> selectListStSchAlumniBySchoolId(Integer schoolId);
}
