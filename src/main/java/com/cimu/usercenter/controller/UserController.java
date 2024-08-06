package com.cimu.usercenter.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cimu.usercenter.common.BaseResponse;
import com.cimu.usercenter.common.ErrorCode;
import com.cimu.usercenter.common.ResultUtils;
import com.cimu.usercenter.contant.UserContant;
import com.cimu.usercenter.execption.BusinessException;
import com.cimu.usercenter.model.domain.User;
import com.cimu.usercenter.model.request.LoginRequest;
import com.cimu.usercenter.model.request.RegisterRequest;
import com.cimu.usercenter.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @projectName: User-center
 * @package: com.cimu.usercenter.controller
 * @className: UserController
 * @author: 辞暮
 * @date: 5/7/2024 上午10:31
 * @version: 1.0
 */
@RequestMapping("/user")
@RestController
public class UserController {
    @Resource
    private UserService userService;

    @PostMapping("/Register")
    public BaseResponse<Long> userRegister(@RequestBody RegisterRequest request){
        if(request==null){
            throw new BusinessException(ErrorCode.NULL_ERROR);
        }
        String userAccount=request.getUserAccount();
        String userPassword=request.getUserPassword();
        String checkPassword=request.getCheckPassword();
        String planetCode=request.getPlanetCode();
        if(StringUtils.isAllBlank(userAccount,userPassword,checkPassword,planetCode)){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Long result=userService.UserRegister(userAccount , userPassword , checkPassword,planetCode);
        return ResultUtils.success(result);
    }
    @PostMapping("/login")
    public BaseResponse<User> userLogin(@RequestBody LoginRequest loginRequest,HttpServletRequest request){
        if(loginRequest==null){
            System.out.println("请求为空");
            throw new BusinessException(ErrorCode.NULL_ERROR);
        }
        String userAccount=loginRequest.getUserAccount();
        String userPassword=loginRequest.getUserPassword();
        if(StringUtils.isAllBlank(userAccount,userPassword)){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        User user=userService.UserLogin(userAccount,userPassword,request);
        return ResultUtils.success(user);
    }
    @PostMapping("/logout")
    public BaseResponse<Integer> userLogout(HttpServletRequest request){
        if(request==null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"未收到请求");
        }
        int result=userService.UserLogout(request);
        return ResultUtils.success(result);
    }
    @GetMapping("/current")
    public BaseResponse<User> getCurrentUser(HttpServletRequest request){
        Object userObj=request.getSession().getAttribute(UserContant.USER_LOGIN_STATE);
        User currentUser=(User)userObj;
        if(currentUser==null){
            throw new BusinessException(ErrorCode.NULL_ERROR,"未登录");
        }
        Long userId=currentUser.getId();
        //todo 检验用户是否合法
        User user = userService.getById(userId);
        User result=userService.makeSafety(user);
        return ResultUtils.success(result);
    }
    /**
     * @description: 查询用户
            * @param: userName  用户名 request
            * @return: java.util.List<com.cimu.usercenter.model.domain.User>
            * @author 14109
            * @date: 5/7/2024 下午3:32
     */
    @GetMapping("/search")
    public BaseResponse<List<User>> searchUsers(String userName,HttpServletRequest request){
        //仅管理员可查询
       if(!isAdmin(request)){
           throw new BusinessException(ErrorCode.NO_AUTH,"无相关权限");
       }
        QueryWrapper<User> queryWrapper=new QueryWrapper<>();
        if(StringUtils.isNotBlank(userName)){
            queryWrapper.like("userName",userName);
        }
        List<User> userList=userService.list();
        List<User> result=userList.stream().map(user -> {
            user.setUserPassword(null);
            return userService.makeSafety(user);
        }).collect(Collectors.toList());
        return ResultUtils.success(result);
    }
    /**
     * @description: 删除用户
            * @param: id 用户id request
            * @return: boolean
            * @author 14109
            * @date: 5/7/2024 下午3:32
     */
    @PostMapping("/delete")
    public BaseResponse<Boolean> deleteUser(@RequestBody Long id,HttpServletRequest request){
        if(!isAdmin(request)){
            throw new BusinessException(ErrorCode.NO_AUTH,"无相关权限");
        }
        if(id<0){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Boolean result=userService.removeById(id);
        return ResultUtils.success(result);
    }
    //判断是否为管理员
    public boolean isAdmin(HttpServletRequest request){
        Object userObj=request.getSession().getAttribute(UserContant.USER_LOGIN_STATE);
        User user=(User)userObj;
        return user!=null&&user.getUserRole()==UserContant.ADMIN_ROLE;
    }
}
