package com.cimu.usercenter.execption;

import com.cimu.usercenter.common.ErrorCode;

/**
 * @projectName: User-center
 * @package: com.cimu.usercenter.execption
 * @className: BusinessExcepiton
 * @author: 辞暮
 * @date: 16/7/2024 下午4:52
 * @version: 1.0
 */
public class BusinessException extends RuntimeException{
    private final int code;

    private final String description;

    public BusinessException(String message,int code, String description) {
        super(message);
        this.code = code;
        this.description = description;
    }
    public BusinessException(ErrorCode errorCode){
        super(errorCode.getMsg());
        this.code=errorCode.getCode();
        this.description=errorCode.getDescription();
    }
    public BusinessException(ErrorCode errorCode,String description){
        super(errorCode.getMsg());
        this.code=errorCode.getCode();
        this.description=description;
    }

    public int getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }
}
