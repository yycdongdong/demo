package com.randream.interceptors;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Slf4j
@Order(-1)
public class FrequentInterceptors implements HandlerInterceptor {
//防止用户刷接口

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        try {

            Integer frequentCount = (Integer) session.getAttribute("frequentCount");//5秒内用户访问服务器次数
            Long frequentTimeMillis = (Long) session.getAttribute("frequentTimeMillis");//每五秒时间戳
            if (session.getAttribute("frequentCount") != null && frequentTimeMillis >= System.currentTimeMillis()) {//上次五秒超过当前时间戳
                session.setAttribute("frequentCount", ++frequentCount);

                return frequentCount <= 5 ? true : false;//接口访问次数过5不通过
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        session.setAttribute("frequentTimeMillis", System.currentTimeMillis() + 5000L);
        session.setAttribute("frequentCount", 1);

        return true;
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
