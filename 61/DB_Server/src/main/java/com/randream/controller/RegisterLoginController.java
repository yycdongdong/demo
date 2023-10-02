package com.randream.controller;

import com.randream.bean.EmailRegister;
import com.randream.bean.LoginBean;
import com.randream.bean.Result;
import com.randream.code.Code;
import com.randream.service.interfaces.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;


@RestController
@RequestMapping("/v1")
@Validated
@CrossOrigin(origins = "*", allowCredentials = "true")
public class RegisterLoginController {
    @Autowired
    IUserService userImpl;

    @PostMapping("/registerEmail")//邮箱账号密码注册
    public Result RegisterEmail(@Validated @RequestBody EmailRegister registerInfo) {
        Result result;
        try {
            result = userImpl.RegisterEmail(registerInfo);
        } catch (Exception e) {
            e.printStackTrace();
            result = new Result(Code.Message_Error, Code.Code_Create_Failure, "ServerError");
        }
        return result;
    }

    @PostMapping("/registerWX")//微信注册
    public Result RegisterWX(@RequestBody Map openidMap) {
        Result result;
        try {
            String openid = (String) openidMap.get("openid");
            if (openid == null || openid.equals("")) {
                result = new Result(Code.Message_Error, Code.Code_Create_Failure, "openid不能为空");
                return result;
            }
            result = userImpl.RegisterWX(openid);
        } catch (Exception e) {
            e.printStackTrace();
            result = new Result(Code.Message_Error, Code.Code_Create_Failure, "ServerError");
        }
        return result;
    }

    @PostMapping("/registerPhone")//手机号注册
    public Result RegisterPhone(@RequestBody Map phoneMap) {
        Result result;
        try {
            String phoneNumber = (String) phoneMap.get("phoneNumber");
            if (phoneNumber == null || phoneNumber.equals("") || phoneNumber.length() < 11) {
                result = new Result(Code.Message_Error, Code.Code_Create_Failure, "手机号不能为空");
                return result;
            }
            result = userImpl.RegisterPhone(phoneNumber);
        } catch (Exception e) {
            e.printStackTrace();
            result = new Result(Code.Message_Error, Code.Code_Create_Failure, "ServerError");
        }

        return result;

    }

    @PostMapping("/login")//邮箱手机号密码登录
    public Result login(@Validated @RequestBody LoginBean loginInfo, HttpServletRequest request) {
        Result result;
        try {

            result = userImpl.Login(loginInfo);
        } catch (Exception e) {
            e.printStackTrace();
            result = Result.Failure(Code.Code_Login_Failure, "ServerError");
        }
        return result;

    }
}


