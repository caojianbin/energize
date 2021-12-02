package com.showtime.serviceenergize.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.showtime.serviceenergize.entity.StProcode;
import com.showtime.serviceenergize.mapper.StProcodeMapper;
import com.showtime.serviceenergize.service.StProcodeService;
import com.showtime.serviceenergize.utils.JedisUtil;
import com.showtime.serviceenergize.utils.UserConst;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * 书唐省市区代码表 服务实现类
 * </p>
 *
 * @author cjb
 * @since 2020-01-17
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class StProcodeServiceImpl extends ServiceImpl<StProcodeMapper, StProcode> implements StProcodeService {

    @Autowired
    private StProcodeMapper procodeMapper;

    /**
     * 获取所有省份
     *
     * @return
     */
    @Override
    public List<StProcode> getProvinces() {
        //先从redis中获取数据
        Object provinces = JedisUtil.getObject(UserConst.PROCODE_PROVINCES);
        List<StProcode> stProcodes = null;
        if (provinces == null){
             stProcodes = procodeMapper.selectProvinces();
            JedisUtil.setObject(UserConst.PROCODE_PROVINCES,stProcodes,JedisUtil.TEN_DAY);
        }else {
            stProcodes = ( List<StProcode>)provinces;

        }
        return stProcodes;
    }

    /**
     * 获取所有市
     *
     * @return
     */
    @Override
    public List<StProcode> getCitys() {
        //先从redis中取出数据
        Object citys = JedisUtil.getObject(UserConst.PROCODE_CITYS);
        List<StProcode> stCitys = null;
        if (citys == null){
            stCitys = procodeMapper.selectCitys();
            JedisUtil.setObject(UserConst.PROCODE_CITYS,stCitys,JedisUtil.TEN_DAY);
        }else {
            stCitys = (List<StProcode>) citys;
        }
        return stCitys;
    }

    /**
     * 获取所有区域
     *
     * @return
     */
    @Override
    public List<StProcode> getAreas() {
        //先从redis中取出数据
        Object areas = JedisUtil.getObject(UserConst.PROCODE_AREAS);
        List<StProcode> stAreas = null;
        if (areas == null){
            stAreas = procodeMapper.selectAreas();
            JedisUtil.setObject(UserConst.PROCODE_AREAS,stAreas,JedisUtil.TEN_DAY);
        }else {
            stAreas = (List<StProcode>) areas;
        }
        return stAreas;
    }
}
