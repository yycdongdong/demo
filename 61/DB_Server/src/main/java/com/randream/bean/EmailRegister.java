package com.randream.bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EmailRegister implements Serializable {//前后端交互登陆注册

  private long userId;
  @Length(min = 1, max = 9, message = "用户名最多为九个字符")
  private String name;
  @NotBlank(message = "密码不能为空")
  @Length(min = 6, max = 16, message = "密码长度必须是6-16个字符")
  private String password;
  @Length(max = 30, message = "邮箱字符过多")
  @Email(message = "邮箱格式不正确")
  @NotBlank(message = "注册账号不能为空")
  private String account;
  private String startTime;//用户创建时间
  //private long groupId;//用户组id,默认不填为0
  private String authority;//用户权限，不是ID
  @Length(min = 4, max = 4, message = "验证码位长度为4的字符")
  @NotBlank(message = "验证码不能为空")
  private String verifyCode;
}
