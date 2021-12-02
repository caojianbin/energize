package com.showtime.serviceenergize.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.showtime.common.exception.MyException;
import com.showtime.common.model.ResultEnum;
import com.showtime.serviceenergize.entity.StEmpowermentUserTable;
import com.showtime.serviceenergize.entity.StEnergizeCommercial;
import com.showtime.serviceenergize.entity.vo.StEnergizeCommercialVo;
import com.showtime.serviceenergize.mapper.StEnergizeCommercialMapper;
import com.showtime.serviceenergize.service.StEmpowermentUserTableService;
import com.showtime.serviceenergize.service.StEnergizeCommercialService;
import com.showtime.serviceenergize.utils.*;
import com.showtime.serviceenergize.utils.common.TokenUtil;
import net.sf.json.JSONObject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.List;

/**
 * <p>
 * 书唐赋能商户管理表 服务实现类
 * </p>
 *
 * @author cjb
 * @since 2019-11-28
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class StEnergizeCommercialServiceImpl extends ServiceImpl<StEnergizeCommercialMapper, StEnergizeCommercial> implements StEnergizeCommercialService {
    /**
     * 书唐赋能商户管理表 Mapper
     */
    @Autowired
    private StEnergizeCommercialMapper commercialMapper;

    /**
     * 赋能管理后台用户表 服务类
     */
    @Autowired
    private StEmpowermentUserTableService userTableService;

    @Autowired
    private HttpServletRequest request;

    private static final String STID = "stid_";
    private static final String CODE = "stsh_";

    /**
     * 根据条件全部查询
     *
     * @param ctName 商户名称
     * @param page   分页信息
     * @return 商户集合
     */
    @Override
    public JSONObject selctCommercialList(Page page, String ctName) {
        PageHelper.startPage((int) page.getCurrent(), (int) page.getSize());
        List<StEnergizeCommercial> list = commercialMapper.selectCommercialList(ctName);
        PageInfo<StEnergizeCommercial> pageInfo = new PageInfo<>(list);
        return BootStrapUtils.initServerJson(pageInfo.getTotal(), pageInfo.getList());
    }

    /**
     * 添加赋能商户
     *
     * @param commercialVo 商户信息
     * @return 成功条件
     */
    @Override
    public Integer addEnergize(StEnergizeCommercialVo commercialVo) {
        if (commercialVo == null) {
            throw new MyException(ResultEnum.PARAMS_EMPTY);
        }
        StEnergizeCommercial commercial = new StEnergizeCommercial();
        BeanUtils.copyProperties(commercialVo, commercial);
        //新增商户默认为正常状态：商户使用状态（1-正常，2-注销）
        commercial.setCtStatus(1);
        commercial.setCtIdentificationCode(CODE + UUIDUtil.getCode());
        commercial.setCtId(STID + UUIDUtil.getUuid());
        //操作账号信息
        String userPhone = TokenUtil.getUserPhone(request);
        StEmpowermentUserTable eUserTable = userTableService.getOne(new QueryWrapper<StEmpowermentUserTable>().eq("user_phone", userPhone));
        if (eUserTable != null) {
            commercial.setCreateName(eUserTable.getUserUserName());
            commercial.setCreateTime(String.valueOf(System.currentTimeMillis()));
        }
        int addRetNum = commercialMapper.insert(commercial);
        return addRetNum;
    }

    /**
     * 修改赋能商户
     *
     * @param commercialVo 商户信息
     * @param ctId         商户id
     * @return 成功条件
     */
    @Override
    public Integer updateEnergize(String ctId, StEnergizeCommercialVo commercialVo) {
        if (commercialVo == null && ctId == null) {
            throw new MyException(ResultEnum.PARAMS_EMPTY);
        }
        StEnergizeCommercial commercial = commercialMapper.selectOne(new QueryWrapper<StEnergizeCommercial>().eq("ct_id", ctId));
        //将vo对象copy到实体对象中
        BeanUtils.copyProperties(commercialVo, commercial);
        //拿到修改账号信息
        //操作账号信息
        String userPhone = TokenUtil.getUserPhone(request);
        StEmpowermentUserTable eUserTable = userTableService.getOne(new QueryWrapper<StEmpowermentUserTable>().eq("user_phone", userPhone));
        if (eUserTable != null) {
            commercial.setUpdateName(eUserTable.getUserUserName());
            commercial.setUpdateTime(String.valueOf(System.currentTimeMillis()));
        }
        commercial.setCtStatus(1);

        int updateRetNum = commercialMapper.update(commercial, new UpdateWrapper<StEnergizeCommercial>().eq("ct_id", ctId));
        return updateRetNum;
    }


    /**
     * 根据id修改用户状态商户
     *
     * @param ctId 商户id
     * @return 成功条件
     */
    @Override
    public Integer delEnergize(String ctId,Integer ctStatus) {
        //判断参数是否为1或者2
        if (!StringUtils.isNumeric(ctStatus)) {
            throw new MyException(ResultEnum.PARAMETER_EXCEPTION);
        }
        //根据id查询出数据
        StEnergizeCommercial commercial = commercialMapper.selectOne(new QueryWrapper<StEnergizeCommercial>().eq("ct_id", ctId));
        if (commercial == null) {
            throw new MyException(ResultEnum.OBJECT_IS_NULL);
        }
        //修改状态
        if(ctStatus == 1){
            commercial.setCtStatus(2);
        }else {
            commercial.setCtStatus(1);
        }

        int delRetNum = commercialMapper.update(commercial, new UpdateWrapper<StEnergizeCommercial>().eq("ct_id", ctId));
        return delRetNum;
    }
}
