package com.cimu.usercenter.model.request;

import lombok.Data;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;

/**
 * @projectName: User-center
 * @package: com.cimu.usercenter.model.domain.request
 * @className: LoginRequest
 * @author: 辞暮
 * @date: 5/7/2024 上午10:52
 * @version: 1.0
 */
@Data
public class LoginRequest implements Serializable {
    //用户账户
    String userAccount;
    //用户密码
    String userPassword;
}
