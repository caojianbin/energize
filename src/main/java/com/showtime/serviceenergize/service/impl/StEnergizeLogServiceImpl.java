package com.showtime.serviceenergize.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.showtime.serviceenergize.entity.StEnergizeLog;
import com.showtime.serviceenergize.entity.vo.StEnergizeLogVo;
import com.showtime.serviceenergize.mapper.StEnergizeLogMapper;
import com.showtime.serviceenergize.service.StEnergizeLogService;
import com.showtime.serviceenergize.utils.BootStrapUtils;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 赋能操作日志 服务实现类
 * </p>
 *
 * @author cjb
 * @since 2019-11-28
 */
@Service
public class StEnergizeLogServiceImpl extends ServiceImpl<StEnergizeLogMapper, StEnergizeLog> implements StEnergizeLogService {

    /**
     * 赋能操作日志 Mapper
     */
    @Autowired
    private StEnergizeLogMapper energizeLogMapper;

    /**
     * 查询日志列表
     *
     * @param userName 用户名
     * @param roleType 角色类型
     * @param page     分页信息
     * @return list 结果集
     */
    @Override
    public JSONObject getEnergizeList(Page page, String userName, Integer roleType) {
        //拿到分页起始页和显示条数
        PageHelper.startPage((int)page.getCurrent(),(int)page.getSize());
        List<StEnergizeLogVo> list = energizeLogMapper.selectEnergizeList(userName, roleType);
        //将集合给pageInfo管理，自动进行分页
        PageInfo<StEnergizeLogVo> pageInfo = new PageInfo<>(list);
        return BootStrapUtils.initServerJson(pageInfo.getTotal(),pageInfo.getList());
    }

    @Override
    public int createLog(StEnergizeLog log) {
        return this.energizeLogMapper.insert(log);
    }

    @Override
    public int updateLog(StEnergizeLog log) {
        return this.energizeLogMapper.update(log , new QueryWrapper<StEnergizeLog>().eq("log_id",log.getLogId()));
    }

}
