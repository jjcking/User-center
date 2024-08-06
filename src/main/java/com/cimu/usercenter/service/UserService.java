package com.cimu.usercenter.service;

import com.cimu.usercenter.model.domain.User;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletRequest;

/**
* @author 14109
* @description 针对表【user】的数据库操作Service
* @createDate 2024-07-04 11:27:16
*/
public interface UserService extends IService<User> {
    /***
     * @description: 用户注册
            * @param: userAccount 用户账号
                      userPassword 用户密码
                      checkPassword 校验密码
            * @return: java.lang.Long   用户id
            * @author 14109
            * @date: 4/7/2024 下午12:10
     */
    long UserRegister(String userAccount, String userPassword, String checkPassword,String planetCode);
    User UserLogin(String userAccount, String userPassword, HttpServletRequest request);
    User makeSafety(User originUser);
    //用户注销
    int UserLogout(HttpServletRequest request);
}
