package com.randream.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.randream.bean.EmailRegister;
import com.randream.bean.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserMapper extends BaseMapper<User> {
    public Integer CreateUser(@Param("user") EmailRegister user);

    public Integer RegisterPhone(@Param("user") User user);

    public Integer RegisterWechat(@Param("user") User user);

    public User FindByAccount(@Param("account") String account);

    public Integer AfterLoginAdd(@Param("user") User user);

    public User FindByUserId(@Param("userId") Long userId);
}
