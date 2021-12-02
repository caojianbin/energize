package com.showtime.serviceenergize.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.showtime.common.utils.StringUtils;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Map;

/**
 * <p>
 * 赋能操作日志
 * </p>
 *
 * @author cjb
 * @since 2019-11-28
 */
//@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class StEnergizeLog extends Model<StEnergizeLog> {

    private static final long serialVersionUID = 1L;

    /**
     * 日志id
     */
    private String logId;

    /**
     * 日志类型
     */
    private String logType;

    /**
     * 日志标题
     */
    private String logTitle;

    /**
     * 请求地址
     */
    private String logRequestUri;

    /**
     * 请求方式
     */
    private String logMethod;

    /**
     * IP
     */
    private String logIp;

    /**
     * 提交参数
     */
    private String logParams;

    /**
     * 异常
     */
    private String logException;

    /**
     * 耗时
     */
    private String logTimeout;

    /**
     * 用户账号
     */
    private String userId;

    /**
     * 创建人
     */
    private String createName;

    /**
     * 创建时间
     */
    private String createTime;

    /**
     * 修改人
     */
    private String updateName;

    /**
     * 修改时间
     */
    private String updateTime;

    /**
     * 角色类型
     */
    private Integer roleType;

    /**
     * 日志自增编号
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 设置请求参数
     * @param paramMap
     */
    public void mapToParams(Map<String, String[]> paramMap) {
        if (paramMap == null){
            return;
        }
        StringBuilder params = new StringBuilder();

        for (Map.Entry<String, String[]> param : ((Map<String, String[]>)paramMap).entrySet()){
            params.append(("".equals(params.toString()) ? "" : "&") + param.getKey() + "=");
            String paramValue = ((param.getValue() != null && param.getValue().length > 0) ? param.getValue()[0] : "");
//            params.append(StringUtils.abbr(StringUtils.endsWithIgnoreCase(param.getKey(), "password") ? "" : paramValue, 500));
            params.append(("password".equals(param.getKey())?"":paramValue));
        }
        this.logParams = params.toString();
    }
    @Override
    protected Serializable pkVal() {
        return this.id;
    }


    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getLogId() {
        return logId;
    }

    public void setLogId(String logId) {
        this.logId = logId;
    }

    public String getLogType() {
        return logType;
    }

    public void setLogType(String logType) {
        this.logType = logType;
    }

    public String getLogTitle() {
        return logTitle;
    }

    public void setLogTitle(String logTitle) {
        this.logTitle = logTitle;
    }

    public String getLogRequestUri() {
        return logRequestUri;
    }

    public void setLogRequestUri(String logRequestUri) {
        this.logRequestUri = logRequestUri;
    }

    public String getLogMethod() {
        return logMethod;
    }

    public void setLogMethod(String logMethod) {
        this.logMethod = logMethod;
    }

    public String getLogIp() {
        return logIp;
    }

    public void setLogIp(String logIp) {
        this.logIp = logIp;
    }

    public String getLogParams() {
        return logParams;
    }

    public void setLogParams(String logParams) {
        this.logParams = logParams;
    }

    public void setLogException(String logException) {
        this.logException = logException.replaceAll("null","Null");
    }

    public String getLogException() {
        return logException;
    }

    public String getLogTimeout() {
        return logTimeout;
    }

    public void setLogTimeout(String logTimeout) {
        this.logTimeout = logTimeout;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCreateName() {
        return createName;
    }

    public void setCreateName(String createName) {
        this.createName = createName;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateName() {
        return updateName;
    }

    public void setUpdateName(String updateName) {
        this.updateName = updateName;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getRoleType() {
        return roleType;
    }

    public void setRoleType(Integer roleType) {
        this.roleType = roleType;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "StEnergizeLog{" +
                "logId='" + logId + '\'' +
                ", logType='" + logType + '\'' +
                ", logTitle='" + logTitle + '\'' +
                ", logRequestUri='" + logRequestUri + '\'' +
                ", logMethod='" + logMethod + '\'' +
                ", logIp='" + logIp + '\'' +
                ", logParams='" + logParams + '\'' +
                ", logException='" + logException + '\'' +
                ", logTimeout='" + logTimeout + '\'' +
                ", userId='" + userId + '\'' +
                ", createName='" + createName + '\'' +
                ", createTime='" + createTime + '\'' +
                ", updateName='" + updateName + '\'' +
                ", updateTime='" + updateTime + '\'' +
                ", roleType=" + roleType +
                ", id=" + id +
                '}';
    }
}
