package com.cimu.usercenter.model.request;

import lombok.Data;

import java.io.Serializable;

/**
 * @projectName: User-center
 * @package: com.cimu.usercenter.model.domain.request
 * @className: RegisterLoginRequest
 * @author: 辞暮
 * @date: 5/7/2024 上午10:42
 * @version: 1.0
 */
@Data
public class RegisterRequest implements Serializable {
    //星球编号
    private String planetCode;
    //用户账户
    private String userAccount;
    //用户密码
    private String userPassword;
    //校验密码
    private String checkPassword;
}
