package com.cimu.usercenter.common;

/**
 * @projectName: User-center
 * @package: com.cimu.usercenter.common
 * @className: ErrorCode
 * @author: 辞暮
 * @date: 16/7/2024 下午4:19
 * @version: 1.0
 */
public enum ErrorCode {

    SUCCESS(0,"ok",""),
    PARAMS_ERROR(400,"请求参数错误",""),
    NULL_ERROR(401,"请求为空",""),
    NO_AUTH(402,"无权限",""),
    NOT_LOGIN(403,"未登录",""),
    SYSTEM_ERROR(405,"系统内部异常","");

    //状态码
    private final int code;
    //状态码信息
    private final String messsage;
    //状态码描述
    private final String description;

    ErrorCode(int code, String message, String description) {
        this.code = code;
        this.messsage = message;
        this.description = description;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return messsage;
    }

    public String getDescription() {
        return description;
    }
}
