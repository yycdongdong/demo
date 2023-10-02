package com.randream.bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SoftLoginBean {
    @NotBlank(message = "密码不能为空")
    @Length(min = 6, max = 16, message = "密码长度必须是6-16个字符")
    private String password;
    @Length(min = 5, max = 30, message = "超过账号最大长度")
    @NotBlank(message = "账号不能为空")
    private String account;
}
