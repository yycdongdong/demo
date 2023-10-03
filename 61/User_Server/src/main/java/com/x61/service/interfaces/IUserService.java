package com.x61.service.interfaces;

import com.baomidou.mybatisplus.extension.service.IService;
import com.x61.bean.EmailRegister;
import com.x61.bean.LoginBean;
import com.x61.bean.Result;
import com.x61.bean.User;

public interface IUserService extends IService<User> {
    public Result RegisterEmail(EmailRegister registerInfo/*phone用户注册信息*/);

    public Result RegisterPhone(String phoneNumber);

    public Result RegisterWX(String openid);

    public Result Login(LoginBean loginInfo);
}
