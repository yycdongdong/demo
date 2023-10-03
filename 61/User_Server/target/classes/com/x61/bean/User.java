package com.x61.bean;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@TableName("user")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @TableId(value = "user_id", type = IdType.AUTO)
    private long userId;
    @TableField("name")
    private String name;
    @TableField("password")
    private String password;
    @TableField("phone_number")
    private String phoneNumber;
    @TableField("email")
    private String email;
    @TableField("start_time")
    private String startTime;//用户创建时间
    @TableField("count")
    private long count;//登录次数默认不填为0
    @TableField("last_login_time")
    private String lastLoginTime;
    @TableField("group_id")
    private long groupId;//用户组id,默认不填为0
    @TableField("state")
    private Long state;//用户更改密码后相对token的版本
    @TableField("open_id")
    private String openId;
}
