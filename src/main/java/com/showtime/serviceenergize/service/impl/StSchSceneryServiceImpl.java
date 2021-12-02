package com.showtime.serviceenergize.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.showtime.common.exception.MyException;
import com.showtime.common.model.ResultEnum;
import com.showtime.common.utils.JudgeNumberUtil;
import com.showtime.serviceenergize.entity.StSchScenery;
import com.showtime.serviceenergize.mapper.StSchScenerySlaveMapper;
import com.showtime.serviceenergize.service.StSchSceneryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * @Author wz
 * @Date 2019/10/11 15:26
 * @Description //TODO 校园风光的service
 */
@Service
public class StSchSceneryServiceImpl implements StSchSceneryService {

    @Autowired
    private StSchScenerySlaveMapper stSchScenerySlaveMapper;

    /**
     * 根据学校ID查询校园风光信息
     *
     * @param schId
     * @return
     */
    @Override
    public List<StSchScenery> selectListStSchSceneryBySchoolId(Integer arId) {
        //判断传入的参数是否为正整数
        boolean positiveNumeric = JudgeNumberUtil.isPositiveNumeric(arId + "");
        if(!positiveNumeric){
            //传入的参数为非法数字，抛异常
            throw new MyException(ResultEnum.ILLEGAL_NUMBER);
        }
        //从从数据库中查询校园风光的信息
        return stSchScenerySlaveMapper.selectList(new QueryWrapper<StSchScenery>().eq("ar_id",arId));
    }
}
