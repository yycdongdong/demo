package com.randream.controller;


import com.randream.bean.Result;
import com.randream.bean.User;
import com.randream.code.Code;
import com.randream.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@CrossOrigin(origins = "*", allowCredentials = "true")
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
