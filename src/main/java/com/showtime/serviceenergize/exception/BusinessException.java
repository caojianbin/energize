package com.showtime.serviceenergize.exception;

import com.showtime.serviceenergize.exception.code.ResponseCodeInterface;

/**
* @ClassName BusinessException
* @Description : 业务异常处理类
* @Author : jlp
* @Date: 2019/12/17 20:48
*/
public class BusinessException extends RuntimeException{
    /**
     * 异常编号
     */
    private final int messageCode;

    /**
     * 对messageCode 异常信息进行补充说明
     */
    private final String detailMessage;

    public BusinessException(int messageCode,String message) {
        super(message);
        this.messageCode = messageCode;
        this.detailMessage = message;
    }
    /**
     * 构造函数
     * @param code 异常码
     */
    public BusinessException(ResponseCodeInterface code) {
        this(code.getCode(), code.getMsg());
    }

    public int getMessageCode() {
        return messageCode;
    }

    public String getDetailMessage() {
        return detailMessage;
    }
}
