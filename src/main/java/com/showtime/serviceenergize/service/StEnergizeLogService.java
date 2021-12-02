package com.showtime.serviceenergize.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.showtime.serviceenergize.entity.StEnergizeLog;
import net.sf.json.JSONObject;

/**
 * <p>
 * 赋能操作日志 服务类
 * </p>
 *
 * @author cjb
 * @since 2019-11-28
 */
public interface StEnergizeLogService extends IService<StEnergizeLog> {

    //增删改
    int createLog(StEnergizeLog log);
    int updateLog(StEnergizeLog log);

    /**
     * 查询日志列表
     *
     * @param userName 用户名
     * @param roleType 角色类型
     * @param page     分页信息
     * @return list 结果集
     */
    JSONObject getEnergizeList(Page page, String userName, Integer roleType);
}
