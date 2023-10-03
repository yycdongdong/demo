package com.x61.controller;


import com.x61.bean.Result;
import com.x61.bean.User;
import com.x61.code.Code;
import com.x61.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RequestMapping("/v2")
@RestController
public class UserInfoController {

    @Autowired
    UserMapper userMapper;

    @GetMapping("/getUserInfo")
    public Result GetUserInfo(HttpServletRequest request) {
        User UserInfo = userMapper.FindByUserId((Long) request.getAttribute("userId"));
        UserInfo.setPassword(null);
        return Result.OK(Code.Code_Select_OK, UserInfo);
    }

}
