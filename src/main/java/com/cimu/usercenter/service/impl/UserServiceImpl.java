package com.cimu.usercenter.service.impl;
import java.util.Date;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cimu.usercenter.common.ErrorCode;
import com.cimu.usercenter.execption.BusinessException;
import com.cimu.usercenter.model.domain.User;
import com.cimu.usercenter.mapper.UserMapper;
import com.cimu.usercenter.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.cimu.usercenter.contant.UserContant.USER_LOGIN_STATE;

/**
* @author 14109
* @description 针对表【user】的数据库操作Service实现
* @createDate 2024-07-04 11:27:16
*/
@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService {
    @Resource
    private UserMapper userMapper;
    //混淆密码
    private final String salt="cimu";

    @Override
    public long UserRegister(String userAccount, String userPassword, String checkPassword,String planetCode) {
        //1、校验
        if(StringUtils.isAllBlank(userAccount,userPassword,checkPassword)){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"参数为空");
        }
        if(userAccount.length()<4){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"用户账号小于4位");
        }
        if(userPassword.length()<8||checkPassword.length()<8){
            //todo 异常处理的messsage被description覆盖了，而description没有值
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"密码小于8位");
        }
        if(planetCode.length()>5){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"星球编号过长");
        }
        //防止特殊字符
        Pattern compile = Pattern.compile("[[ _`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]|\\n|\\r|\\t]+");
        Matcher matcher=compile.matcher(userAccount);
        if(matcher.find()){
           return -1;
        }
        //账号不能重复
        QueryWrapper<User> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("userAccount",userAccount);
        long count=this.count(queryWrapper);
        if(count>0){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"账号已存在");
        }
        //星球编号不能重复
        queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("planetCode",planetCode);
        count=this.count(queryWrapper);
        if(count>0){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"星球编号已存在");
        }
        //密码不正确
        if(!checkPassword.equals(userPassword)){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"两次输入密码不一致");
        }
        //加密
        String encryptPassword= DigestUtils.md5DigestAsHex((salt+userPassword).getBytes(StandardCharsets.UTF_8));
        //插入数据
        User user=new User();
        user.setUserAccount(userAccount);
        user.setUserPassword(encryptPassword);
        user.setPlanetCode(planetCode);
        boolean saveResult=this.save(user);
        if(!saveResult){
            throw new RuntimeException();
        }
        return user.getId();
    }

    @Override
    public User UserLogin(String userAccount, String userPassword, HttpServletRequest request) {
        if(StringUtils.isAllBlank(userAccount,userPassword)){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"参数为空");
        }
        if(userAccount.length()<4){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"账号不能小于4位");
        }
        if(userPassword.length()<8){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"密码不能小于8位");
        }
        //防止特殊字符
        Pattern compile = Pattern.compile("[[ _`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]|\\n|\\r|\\t]+");
        Matcher matcher=compile.matcher(userAccount);
        if(matcher.find()){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"不能包含特俗字符");
        }
        String encryptPassword= DigestUtils.md5DigestAsHex((salt+userPassword).getBytes(StandardCharsets.UTF_8));
        //账号不存在
        QueryWrapper<User> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("userAccount",userAccount);
        queryWrapper.eq("userPassword",encryptPassword);
        User user = userMapper.selectOne(queryWrapper);
        if(user==null){
            log.info("User login failed,userAccount cannot match userPassword");
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"账号与密码不匹配");
        }
        User safetyUser=makeSafety(user);
        //记录用户的登录态
        request.getSession().setAttribute(USER_LOGIN_STATE,user);
        return safetyUser;
    }
    //用户信息脱敏
    @Override
    public User makeSafety(User originUser){
        if(originUser==null){
            return null;
        }
        User safetyUser=new User();
        safetyUser.setId(originUser.getId());
        safetyUser.setUserName(originUser.getUserName());
        safetyUser.setUserAccount(originUser.getUserAccount());
        safetyUser.setAvatarUrl(originUser.getAvatarUrl());
        safetyUser.setGender(originUser.getGender());
        safetyUser.setUserRole(originUser.getUserRole());
        safetyUser.setPhone(originUser.getPhone());
        safetyUser.setEmial(originUser.getEmial());
        safetyUser.setIsValid(originUser.getIsValid());
        safetyUser.setPlanetCode(originUser.getPlanetCode());
        safetyUser.setCreateTime(originUser.getCreateTime());
        return safetyUser;
    }

    /***
     * @description: 用户注销
            * @param: null
            * @return:
            * @author 14109
            * @date: 16/7/2024 下午2:53
     */
    @Override
    public int UserLogout(HttpServletRequest request) {
        request.getSession().removeAttribute(USER_LOGIN_STATE);
        return 1;
    }

}




