package com.x61.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.x61.bean.*;
import com.x61.code.Code;
import com.x61.mapper.RightMapper;
import com.x61.mapper.UserMapper;
import com.x61.mapper.UserRightRelationMapper;
import com.x61.service.interfaces.IUserService;
import com.x61.utils.DesUtil;
import com.x61.utils.JWTUtil;
import com.x61.utils.TimeUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class UserServiceServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Autowired
    UserMapper userMapper;
    @Autowired
    UserRightRelationMapper userRightRelationMapper;
    @Autowired
    RightMapper rightMapper;


    @Override
    public Result RegisterEmail(EmailRegister registerInfo) {//用户注册
        Map Result = new HashMap();
        //账号是否被注册过
        LambdaQueryWrapper<User> findUserWrapper = new LambdaQueryWrapper();
        findUserWrapper.eq(User::getEmail, registerInfo.getAccount());
        List<User> isUserExist = userMapper.selectList(findUserWrapper);
        if (isUserExist != null && !isUserExist.isEmpty()) {
            return new Result(Code.Message_Failure, Code.Code_Create_Failure, "该邮箱已被注册!");
        }
        //对用户的密码进行加密存储
        registerInfo.setPassword(DesUtil.getEncryptString(registerInfo.getPassword()));
        registerInfo.setStartTime(TimeUtil.DateTimeString());
        //返回用户的ID
        userMapper.CreateUser(registerInfo);

        log.info("用户Phone: " + registerInfo.getAccount() + "注册");
        //用户权限关系对象
        Userrightrelation userrightrelation = Userrightrelation.builder()
                .userId(registerInfo.getUserId())
                // .state(1L)默认用户状态1不可用
                .rightTime(registerInfo.getStartTime())
                .build();

        try {
            //根据用户的权限查找权限ID
            Long right_id = rightMapper.FindRight(registerInfo.getAuthority());
            if (right_id != null) {
                //如果用户权限合法,
                userrightrelation.setRightId(right_id);
            } else {
                //如果用户权限不合法,默认授予'一般用户'ID:1}
                userrightrelation.setRightId(1L);
            }
        } catch (Exception e) {
            userrightrelation.setRightId(1L);
        }
        userRightRelationMapper.insert(userrightrelation);
        //返回用户info,权限ID
        Result.put("userId", registerInfo.getUserId());
        Result.put("rightId", userrightrelation.getRightId());

        return new Result(Code.Message_Ok, Code.Code_Create_OK, Result);
    }

    @Override
    public Result RegisterWX(String openid) {
        LambdaQueryWrapper<User> findUserWrapper = new LambdaQueryWrapper();
        findUserWrapper.eq(User::getOpenId, openid);
        List<User> isUserExist = userMapper.selectList(findUserWrapper);
        if (isUserExist != null && !isUserExist.isEmpty()) {
            return new Result(Code.Message_Failure, Code.Code_Create_Failure, "该微信账号已被注册!");
        }
        User registerUser = User.builder().openId(openid).startTime(TimeUtil.DateTimeString()).build();
        Integer registerResult = userMapper.RegisterWechat(registerUser);
        Userrightrelation userrightrelation = Userrightrelation.builder()
                .userId(registerUser.getUserId())//用户注册时返回的用户uid
                .rightTime(registerUser.getStartTime())//授权时间
                .rightId(1L)//权限ID
                .build();
        userRightRelationMapper.insert(userrightrelation);
        return Result.OK(Code.Code_Create_OK, registerResult);
    }

    @Override
    public Result RegisterPhone(String phoneNumber) {
        LambdaQueryWrapper<User> findUserWrapper = new LambdaQueryWrapper();
        findUserWrapper.eq(User::getPhoneNumber, phoneNumber);
        List<User> isUserExist = userMapper.selectList(findUserWrapper);
        if (isUserExist != null && !isUserExist.isEmpty()) {
            return new Result(Code.Message_Failure, Code.Code_Create_Failure, "该手机号已被注册!");
        }
        User registerUser = User.builder().phoneNumber(phoneNumber).startTime(TimeUtil.DateTimeString()).build();
        Integer registerResult = userMapper.RegisterPhone(registerUser);
        Userrightrelation userrightrelation = Userrightrelation.builder()
                .userId(registerUser.getUserId())//用户注册时返回的用户uid
                .rightTime(registerUser.getStartTime())//授权时间
                .rightId(1L)//权限ID
                .build();
        userRightRelationMapper.insert(userrightrelation);
        return Result.OK(Code.Code_Create_OK, registerResult);
    }

    @Override
    public Result Login(LoginBean loginInfo) {//邮箱号手机号密码登录
        User user = userMapper.FindByAccount(loginInfo.getAccount());//寻找用户
        if (user != null) {
            if (loginInfo.getPassword().equals(DesUtil.getDecryptString(user.getPassword())))//将用户上传的密码进行对比
            {
                Map tokenMap = new HashMap();
                User loginCount = User.builder()
                        .count(user.getCount() + 1L)
                        .userId(user.getUserId())
                        .lastLoginTime(TimeUtil.DateTimeString()).build();
                userMapper.AfterLoginAdd(loginCount);
                tokenMap.put("token", JWTUtil.getToken(user.getUserId(), user.getState()));//发放token:用户ID和用户token状态

                return Result.OK(Code.Code_Login_Success, tokenMap);
            } else return Result.Failure(Code.Code_Login_Failure, "登陆失败,密码不一致!");
        }
        return Result.Failure(Code.Code_Login_Failure, "手机号还未注册!");
    }

}
