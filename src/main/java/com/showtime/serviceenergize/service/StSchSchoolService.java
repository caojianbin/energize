package com.showtime.serviceenergize.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.showtime.common.model.dto.ResponseJsonCode;
import com.showtime.serviceenergize.entity.StSchSchool;
import com.showtime.serviceenergize.entity.dto.HomeSchoolDto;
import com.showtime.serviceenergize.entity.dto.StSchSchoolDto;
import com.showtime.serviceenergize.entity.dto.StSchoolAdmissionCodeDto;
import com.showtime.serviceenergize.entity.vo.FuzzyQuerySchoolVo;

import java.util.List;

/**
 * <p>
 * 学校信息表 服务类
 * </p>
 *
 * @author cjb
 * @since 2019-12-26
 */
public interface StSchSchoolService extends IService<StSchSchool> {
    /**
     * 根据学编码查询
     *
     * @param schAdmissionCode     招生代码
     * @param userNumber           用户账号
     * @param ctIdentificationCode 商户号
     * @return
     */
    ResponseJsonCode getSchoolByCoding(String schAdmissionCode, String userNumber, String ctIdentificationCode);

    /**
     * 分页查询学校列表信息
     *
     * @param userNumber           用户ID（与自己的收藏和解锁图标相关）
     * @param ctIdentificationCode 商户号
     * @param pageNum              当前页数
     * @param pageSize             当前页需要显示的数
     * @return
     */
    List<HomeSchoolDto> getSchSchoolList(String userNumber, String ctIdentificationCode, Integer pageNum, Integer pageSize, Integer isPlat);

    /**
     * 根据学校名称模糊查询（书堂录）
     *
     * @param schName              学校名字
     * @param list                 学校ID列表
     * @param userNumber           商户用户号
     * @param ctIdentificationCode 商户号
     * @param pageNum              当前页数
     * @param pageSize             当前页需要显示的数量
     * @return
     */
    List<FuzzyQuerySchoolVo> selectStSchoolNameListBySchName(String schName, String[] list, String userNumber, String ctIdentificationCode, Integer pageNum, Integer pageSize);

    /**
     * 赋能用户新增浏览记录
     *
     * @param schId
     */
    Integer addView(String schId);
}
