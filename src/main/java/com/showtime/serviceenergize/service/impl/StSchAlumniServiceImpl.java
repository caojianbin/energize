package com.showtime.serviceenergize.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.showtime.common.exception.MyException;
import com.showtime.common.model.ResultEnum;
import com.showtime.common.utils.JudgeNumberUtil;
import com.showtime.serviceenergize.entity.StSchAlumni;
import com.showtime.serviceenergize.mapper.StSchAlumniSlaveMapper;
import com.showtime.serviceenergize.service.StSchAlumniService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * @Author zs
 * @Date 2019/2/13 16:00
 * @Description //TODO 知名校友的实现类
 */

@Transactional(rollbackFor = Exception.class)
@Slf4j
@Service
public class StSchAlumniServiceImpl implements StSchAlumniService {
    @Autowired
    private StSchAlumniSlaveMapper stSchAlumniSlaveMapper;

    @Override
    public List<StSchAlumni> selectListStSchAlumniBySchoolId(Integer schoolId) {
        //判断传入的参数是否为正整数
        boolean positiveNumeric = JudgeNumberUtil.isPositiveNumeric(schoolId + "");
        if (!positiveNumeric) {
            //传入的参数为非法数字，抛异常
            throw new MyException(ResultEnum.ILLEGAL_NUMBER);
        }
        //从从数据库中查询知名校友的信息
        return stSchAlumniSlaveMapper.selectList(new QueryWrapper<StSchAlumni>().eq("sch_id",schoolId));
    }
}
