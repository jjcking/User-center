package com.cimu.usercenter.common;

import lombok.Data;

import java.io.Serializable;

/**
 * @projectName: User-center
 * @package: com.cimu.usercenter.common 通用返回类
 * @className: BaseResponse
 * @author: 辞暮
 * @date: 16/7/2024 下午4:03
 * @version: 1.0
 */
@Data
public class BaseResponse<T> implements Serializable {
    private int code;
    private T data;
    private String msg;
    private String description;

    public BaseResponse(int code, T data, String msg,String description) {
        this.code = code;
        this.data = data;
        this.msg = msg;
        this.description=description;
    }
    public BaseResponse(int code,T data,String msg){
        this(code,data,msg,"");
    }
    public BaseResponse(int code,T data){
        this(code,data,"","");
    }
    public BaseResponse(ErrorCode errorCode){
        this(errorCode.getCode(),null,errorCode.getMsg(),errorCode.getDescription());
    }
}
