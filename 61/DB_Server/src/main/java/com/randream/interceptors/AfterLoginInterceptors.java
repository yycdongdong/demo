package com.randream.interceptors;


import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.randream.bean.Result;
import com.randream.bean.User;
import com.randream.code.Code;
import com.randream.mapper.UserMapper;
import com.randream.utils.TimeUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@Order(1)
public class AfterLoginInterceptors implements HandlerInterceptor {

    @Autowired
    public UserMapper userMapper;

    @Transactional
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //如果是OPTIONS请求 直接放行
        String method = request.getMethod();
        if (method.equals("OPTIONS")) {
            return true;
        }
        try {
            LambdaQueryWrapper<User> userWrapper = new LambdaQueryWrapper();
            userWrapper.select(User::getCount).eq(User::getUserId, request.getAttribute("userId"));
            User user = userMapper.selectOne(userWrapper);
            user.setCount(user.getCount() + 1L);
            user.setUserId((Long) request.getAttribute("userId"));
            user.setLastLoginTime(TimeUtil.DateTimeString());
            userMapper.AfterLoginAdd(user);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println(JSON.toJSONString(Result.Failure(Code.Code_Common_Error, "网络错误!")));
            return false;
        }

    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}
