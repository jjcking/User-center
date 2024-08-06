package com.cimu.usercenter.common;

/**
 * @projectName: User-center
 * @package: com.cimu.usercenter.common
 * @className: ResultUtils
 * @author: 辞暮
 * @date: 16/7/2024 下午4:10
 * @version: 1.0
 */
public class ResultUtils {
    //成功
    public static <T> BaseResponse<T> success(T data){
        return new BaseResponse<>(0,data,"ok");
    }
    //失败
    public static BaseResponse error(ErrorCode errorCode){
        return new BaseResponse(errorCode);
    }
    public static BaseResponse error(ErrorCode errorCode,String message,String description){
        return new BaseResponse(errorCode.getCode(),null,message,description);
    }
    public static BaseResponse error(ErrorCode errorCode,String description){
        return new BaseResponse(errorCode.getCode(),null,errorCode.getMsg(),description);
    }
    public static BaseResponse error(int code,String message,String description){
        return new BaseResponse(code,null,message,description);
    }
}
