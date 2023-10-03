package com.x61.filters;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.x61.bean.Result;
import com.x61.bean.User;
import com.x61.code.Code;
import com.x61.mapper.UserMapper;
import com.x61.utils.JWTUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.core.RedisTemplate;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@Slf4j
@Order(1)
@WebFilter(filterName = "JWTFilter", urlPatterns = "/v2/*")//拦截接口
public class JWTFilter implements Filter {
    @Autowired
    RedisTemplate redisTemplate;
    @Autowired
    UserMapper userMapper;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;//
        HttpServletResponse response = (HttpServletResponse) servletResponse;//
        //如果是OPTIONS请求 直接放行

        String method = request.getMethod();
        if (method.equals("OPTIONS")) {
            filterChain.doFilter(servletRequest, servletResponse);//放行
        }
        //验证JWT
        String jwt = request.getHeader("Authorization");

        Map JwtVerifyResult = JWTUtil.verify(jwt);//JwtVerifyResult:{boolean:isSuccess,Long:id->验证JWT成功返回用户id否则null}
        if ((boolean) JwtVerifyResult.get("isSuccess")) {
            Long userId = (Long) JwtVerifyResult.get("userId");
            try {
                LambdaQueryWrapper<User> userWrapper = new LambdaQueryWrapper<>();
                userWrapper.select(User::getState).eq(User::getUserId, userId);
                User userState = userMapper.selectOne(userWrapper);//获取用户登录token的版本
                if (userState.getState().equals(JwtVerifyResult.get("state")))
                {//用户更改密码后相对token的版本
                    request.setAttribute("userId", userId);
                    filterChain.doFilter(servletRequest, servletResponse); } //放行
                else response.getWriter().println(JSON.toJSONString(Result.Failure(Code.Code_Login_Failure, "身份信息已过期,请重新登录!")));
            }
            catch (Exception e) {
                e.printStackTrace();
                response.getWriter().println(JSON.toJSONString(Result.Failure(Code.Code_Login_Failure, "网络异常!")));}
        }
        else response.getWriter().println(JSON.toJSONString(Result.Failure(Code.Code_Login_Failure, "身份信息失效,请重新登录!")));

    }


}
