package com.randream.service.interfaces;

import com.baomidou.mybatisplus.extension.service.IService;
import com.randream.bean.EmailRegister;
import com.randream.bean.Result;
import com.randream.bean.SoftLoginBean;
import com.randream.bean.User;

public interface IUserService extends IService<User> {
    public Result RegisterEmail(EmailRegister registerInfo/*phone用户注册信息*/);

    public Result RegisterPhone(String phoneNumber);

    public Result RegisterWX(String openid);

    public Result Login(SoftLoginBean loginInfo);
}
