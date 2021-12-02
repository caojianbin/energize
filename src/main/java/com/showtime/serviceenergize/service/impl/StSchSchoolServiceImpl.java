package com.showtime.serviceenergize.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.showtime.common.exception.MyException;
import com.showtime.common.model.ResultEnum;
import com.showtime.common.model.dto.ResponseJsonCode;
import com.showtime.common.utils.CommonUtils;
import com.showtime.common.utils.RedisUtils;
import com.showtime.serviceenergize.entity.StEnergizeCommercial;
import com.showtime.serviceenergize.entity.StEnergizeUserUnlockSchool;
import com.showtime.serviceenergize.entity.StProcode;
import com.showtime.serviceenergize.entity.StSchSchool;
import com.showtime.serviceenergize.entity.dto.HomeSchoolDto;
import com.showtime.serviceenergize.entity.dto.StSchSchoolDto;
import com.showtime.serviceenergize.entity.dto.StSchoolAdmissionCodeDto;
import com.showtime.serviceenergize.entity.vo.FuzzyQuerySchoolVo;
import com.showtime.serviceenergize.mapper.StEnergizeCommercialMapper;
import com.showtime.serviceenergize.mapper.StSchSchoolMapper;
import com.showtime.serviceenergize.mapper.StSchTagInfoSlaveMapper;
import com.showtime.serviceenergize.service.StEnergizeUserUnlockSchoolService;
import com.showtime.serviceenergize.service.StFeignClientSchoolService;
import com.showtime.serviceenergize.service.StProcodeService;
import com.showtime.serviceenergize.service.StSchSchoolService;
import com.showtime.serviceenergize.utils.SchoolConst;
import com.showtime.serviceenergize.utils.SchoolRedisKey;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.util.StringUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.showtime.common.utils.RedisUtils.redisTemplate;

/**
 * <p>
 * 学校信息表 服务实现类
 * </p>
 *
 * @author cjb
 * @since 2019-12-26
 */
@Service
public class StSchSchoolServiceImpl extends ServiceImpl<StSchSchoolMapper, StSchSchool> implements StSchSchoolService {
    /**
     * 学校信息表 Mapper
     */
    @Autowired
    private StSchSchoolMapper schSchoolMapper;
    @Autowired
    private StProcodeService procodeService;
    @Autowired
    private StSchTagInfoSlaveMapper stSchTagInfoSlaveMapper;
    @Autowired
    private StEnergizeUserUnlockSchoolService userUnlockSchoolService;
    @Autowired
    private StEnergizeCommercialMapper commercialMapper;
    @Autowired
    private RedisTemplate redisTemplate;
    /**
     * 根据学校名称搜索，是否是汉字
     */
    private static final Pattern P_STR = Pattern.compile("[\\u4e00-\\u9fa5]+");


    /**
     * 根据学编码查询
     *
     * @param schAdmissionCode     招生代码
     * @param userNumber           用户账号
     * @param ctIdentificationCode 商户号
     * @return
     */
    @Override
    public ResponseJsonCode getSchoolByCoding(String schAdmissionCode, String userNumber, String ctIdentificationCode) {
        if (StringUtil.isEmpty(schAdmissionCode) && StringUtil.isEmpty(userNumber) && StringUtil.isEmpty(ctIdentificationCode)) {
            throw new MyException(ResultEnum.PARAMS_EMPTY);
        }
        StSchoolAdmissionCodeDto codeing = schSchoolMapper.selectSchoolCoding(schAdmissionCode);
        if (codeing == null) {
            return ResponseJsonCode.creatResponseJsonCode(200, "高校招生代码不存在");
        }
        StEnergizeCommercial commercial = commercialMapper.selectCommercialByCtId(ctIdentificationCode);
        if (commercial == null) {
            return ResponseJsonCode.creatResponseJsonCode(200, "商户号错误");
        }
        StSchSchoolDto stSchSchool = schSchoolMapper.selectSchoolByCoding(schAdmissionCode);
        //查询商户用户是否解锁该学校
        addUserUnlockInfor(stSchSchool, userNumber, ctIdentificationCode);
        return ResponseJsonCode.successRequestJsonCode(stSchSchool);
    }

    /**
     * 分页查询学校列表信息
     *
     * @param userNumber           用户ID（与自己的收藏和解锁图标相关）
     * @param ctIdentificationCode 商户号
     * @param pageNum              当前页数
     * @param pageSize             当前页需要显示的数
     * @return
     */
    @Override
    public List<HomeSchoolDto> getSchSchoolList(String userNumber, String ctIdentificationCode, Integer pageNum, Integer pageSize, Integer isPlat) {
        //得到分页页码
        pageNum = CommonUtils.getPageNum(pageNum, pageSize);
        //分页得到高校列表
        List<HomeSchoolDto> schSchoolList = schSchoolMapper.getSchSchoolList(pageNum, pageSize, isPlat);
        if (schSchoolList == null) {
            return null;
        }
        //添加学校地址信息
        addAddress(schSchoolList);

        //添加学校标签信息
        schSchoolList = addSchTagInfor(schSchoolList);

        //添加用户解锁学校信息
        if (userNumber != null && ctIdentificationCode != null) {
            schSchoolList = addUnlockInfor(schSchoolList, userNumber, ctIdentificationCode);
        }

        return schSchoolList;
    }


    /**
     * 根据学校名称模糊查询（书堂录）
     *
     * @param schName  学校名字
     * @param list     学校ID列表
     * @param pageNum  当前页数
     * @param pageSize 当前页需要显示的数量
     * @return
     */
    @Override
    public List<FuzzyQuerySchoolVo> selectStSchoolNameListBySchName(String schName, String[] list, String userNumber, String ctIdentificationCode, Integer pageNum, Integer pageSize) {
        //计算时间
        long start1 = System.currentTimeMillis();
        //得到分页页码
        pageNum = CommonUtils.getPageNum(pageNum, pageSize);
        new RedisUtils(redisTemplate);
        //学校名字拼音
        String schNameSpell = null;
        if (StringUtils.isNotEmpty(schName)) {
            //搜索名字不为空，判断学校名字是拼音还是汉字
            Map<String, String> myCenterMap = getSchNameSpell(schName);
            if (myCenterMap.get("noData") == null) {
                schName = myCenterMap.get("schName");
                schNameSpell = myCenterMap.get("schNameSpell");
            } else {
                //默认不存在的情况
                return new ArrayList();
            }
        }
        List<FuzzyQuerySchoolVo> returnList = schSchoolMapper.selectStSchoolNameListBySchName(1, schName, schNameSpell, pageNum, pageSize);
        //添加解锁信息
        returnList = addUnlockInforFuzzyQuery(returnList, userNumber, ctIdentificationCode);
        return returnList;
    }

    /**
     * 赋能用户新增浏览记录
     *
     * @param schId
     */
    @Override
    public Integer addView(String schId) {
        StSchSchool stSchSchool = schSchoolMapper.selectById(schId);
        Integer update = 0;
        if ( null != stSchSchool){
            //浏览量
            String stPageView = stSchSchool.getStPageView();
            Integer view = 1;
            if (null != stPageView ){
                view = view + Integer.parseInt(stPageView);
            }
            stSchSchool.setStPageView(view.toString());
            update = schSchoolMapper.updateById(stSchSchool);
        }
        return update;
    }

    /**
     * 循环添加学校解锁信息(模糊查询)
     *
     * @param returnList           学校列表
     * @param userNumber           用户账号
     * @param ctIdentificationCode 商户号
     */
    public List<FuzzyQuerySchoolVo> addUnlockInforFuzzyQuery(List<FuzzyQuerySchoolVo> returnList, String userNumber, String ctIdentificationCode) {
        if (StringUtil.isNotEmpty(userNumber) && StringUtil.isNotEmpty(ctIdentificationCode)) {
            //根据用户查询解锁学校的列表
            List<StEnergizeUserUnlockSchool> unlockByUid = userUnlockSchoolService.getUserUnlockSchool(userNumber, ctIdentificationCode);
            List<StEnergizeUserUnlockSchool> unlockSchools = new ArrayList<>();
            unlockByUid.forEach(e -> {
                long endTime = Long.valueOf(e.getSeuEndTime()).longValue();
                if ((endTime > System.currentTimeMillis())) {
                    unlockSchools.add(e);
                }
            });
            if (returnList != null) {
                returnList.forEach(school -> {
                    unlockSchools.forEach(lock -> {
                        //如果相等则表明已解锁
                        if (lock.getSchId().equals(school.getSchId())) {
                            school.setUnlockStatus(1);
                        }
                    });
                });
            }
        }
        return returnList;
    }


    /**
     * 循环添加学校解锁信息
     *
     * @param stSchSchools         学校列表
     * @param userNumber           用户账号
     * @param ctIdentificationCode 商户号
     */
    public List<HomeSchoolDto> addUnlockInfor(List<HomeSchoolDto> stSchSchools, String userNumber, String ctIdentificationCode) {
        if (StringUtil.isNotEmpty(userNumber) && StringUtil.isNotEmpty(ctIdentificationCode)) {
            //根据用户查询解锁学校的列表
            List<StEnergizeUserUnlockSchool> unlockByUid = userUnlockSchoolService.getUserUnlockSchool(userNumber, ctIdentificationCode);
            List<StEnergizeUserUnlockSchool> unlockSchools = new ArrayList<>();
            unlockByUid.forEach(e -> {
                long endTime = Long.valueOf(e.getSeuEndTime()).longValue();
                if ((endTime > System.currentTimeMillis())) {
                    unlockSchools.add(e);
                }
            });
            if (unlockSchools != null) {
                stSchSchools.forEach(school -> {
                    unlockSchools.forEach(lock -> {
                        //如果相等则表明已解锁
                        if (lock.getSchId().equals(school.getSchId())) {
                            school.setUnlockStatus(1);
                        }
                    });
                });
            }
        }
        return stSchSchools;
    }


    /**
     * 添加商户用户解锁学校信息
     *
     * @param stSchSchool          学校信息
     * @param userNumber           商户用户账号
     * @param ctIdentificationCode 商户号
     * @return
     */
    public StSchSchoolDto addUserUnlockInfor(StSchSchoolDto stSchSchool, String userNumber, String ctIdentificationCode) {
        if (null != stSchSchool) {
            StEnergizeUserUnlockSchool one = userUnlockSchoolService.getOne(new QueryWrapper<StEnergizeUserUnlockSchool>().eq("ct_identification_code", ctIdentificationCode).eq("user_number", userNumber).eq("sch_id", stSchSchool.getSchId()));
            if (one != null) {
                Long endTime = Long.valueOf(one.getSeuEndTime());
                //解锁时间没有过期
                if (endTime > System.currentTimeMillis()) {
                    stSchSchool.setUnlockStatus(1);
                }
            }

        }
        return stSchSchool;
    }

    /**
     * 循环添加学校对应的标签
     *
     * @param stSchSchools 学校列表
     * @return
     */
    public List<HomeSchoolDto> addSchTagInfor(List<HomeSchoolDto> stSchSchools) {
        if (null != stSchSchools && stSchSchools.size() > 0) {
            stSchSchools.forEach(school -> {
                Integer schId = school.getSchId();
                school.setTagList(stSchTagInfoSlaveMapper.getTagBySchId(schId));
            });
        }
        return stSchSchools;
    }

    /**
     * @return
     * @throws
     * @description 添加学校地址信息
     * @Param schSchoolList 学校列表
     * @Author jlp
     * @Date 2019/12/25 12:00
     */
    private void addAddress(List<HomeSchoolDto> schSchoolList) {

        if (schSchoolList == null) {
            return;
        }
        //获取所有省份列表
        List<StProcode> provinces = procodeService.getProvinces();

        //获取所有城市列表
        List<StProcode> citys = procodeService.getCitys();

        for (HomeSchoolDto schoolDto : schSchoolList) {
            String saProvinceCode = schoolDto.getSaProvince();
            String saCityCode = schoolDto.getSaCity();

            String procodeProvinceName = getProcodeProvinceName(saProvinceCode, provinces);
            String procodeCityName = getProcodeCityName(saCityCode, citys);
            //都为空
            if (StringUtils.isEmpty(procodeProvinceName) && StringUtils.isEmpty(procodeCityName)) {
                schoolDto.setSchCity("");
                //都不为空
            } else if (!StringUtils.isEmpty(procodeProvinceName) && !StringUtils.isEmpty(procodeCityName)) {
                if (SchoolConst.ADDRESS_NAME_MAINCITY.equals(procodeCityName)) {
                    procodeCityName = "";
                }
                schoolDto.setSchCity(procodeProvinceName + procodeCityName);
            } else {
                //省份为空，设置城市信息
                if (StringUtils.isEmpty(procodeProvinceName)) {
                    schoolDto.setSchCity(procodeCityName);
                } else { //城市为空，设置省份信息
                    schoolDto.setSchCity(procodeProvinceName);
                }
            }

        }
    }

    /**
     * @return
     * @throws
     * @description 根据省份code获取省份名称
     * @Param code 省份code
     * @Param provinces 省份列表
     * @Author jlp
     * @Date 2019/12/25 11:49
     */
    private String getProcodeProvinceName(String code, List<StProcode> provinces) {

        if (StringUtils.isEmpty(code) || provinces == null) {
            return null;
        }
        for (StProcode province : provinces) {
            Integer stProvincecode = province.getStProvincecode();
            if (stProvincecode == null) {
                continue;
            }
            if (code.equals(stProvincecode + "")) {
                return province.getStProvincename();
            }
        }
        return null;
    }


    /**
     * @return
     * @throws
     * @description 根据城市code获取城市名称
     * @Param code 城市code
     * @Param citys 城市列表
     * @Author jlp
     * @Date 2019/12/25 11:49
     */
    private String getProcodeCityName(String code, List<StProcode> citys) {
        if (StringUtils.isEmpty(code) || citys == null) {
            return null;
        }
        for (StProcode city : citys) {
            Integer stCitycode = city.getStCitycode();
            if (stCitycode == null) {
                continue;
            }
            if (code.equals(stCitycode + "")) {
                return city.getStCityname();
            }
        }
        return null;
    }

    /**
     * 模糊字段  得到搜索字段汉字或者拼音
     *
     * @param schName 不等于null
     * @return
     */
    public Map<String, String> getSchNameSpell(String schName) {
        Map<String, String> map = new HashMap<>(16);

        Matcher m = P_STR.matcher(schName);
        String schNameSpell = null;
        String noData = null;
        if (m.find()) {
            //这种情况下1.肯定有汉字，且把非汉字的字符转换-
            String reg = "[^\u4e00-\u9fa5]";
            String str = schName.replaceAll(reg, "-");
            String[] arr = str.split("-");
            if (arr.length == 1) {
                //1：全汉字
                //2：汉字+拼音结尾（拼音结尾不算长度）
                schName = arr[0];
            } else {
                String arrStr = null;
                for (int i = 0, len = arr.length; i < len; i++) {
                    arrStr = arr[i];
                    if (StringUtils.isNotBlank(arrStr)) {
                        //拼音结尾+汉字
                        if (i < len - 1) {
                            //说明汉字后面肯定还有汉字

                            //拼音+汉字+拼音
                            //汉字+拼音+汉字

                            //默认这种情况下没有数据
                            noData = "noData";
                        }
                        schName = arrStr;
                        break;
                    }
                }
            }

        } else {
            schNameSpell = schName;
            schName = null;
            noData = null;
        }
        map.put("schName", schName);
        map.put("schNameSpell", schNameSpell);
        map.put("noData", noData);
        return map;
    }

}
