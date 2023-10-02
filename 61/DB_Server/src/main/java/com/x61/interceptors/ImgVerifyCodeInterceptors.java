package com.x61.interceptors;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.x61.bean.Result;
import com.x61.code.Code;
import com.x61.utils.GetRequestJsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@Slf4j
@Order(0)
public class ImgVerifyCodeInterceptors implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        String sessionAttribute = (String) session.getAttribute("SESSION_VERIFY_CODE");
        String method = request.getMethod();
        if (method.equals("OPTIONS")) {
            return true;
        }
        JSONObject json = GetRequestJsonUtil.getRequestJsonObject(request);//用户验证码及用户信息JSON
        String verifyCode = (String) json.get("verifyCode");
        if (sessionAttribute != null && verifyCode.equalsIgnoreCase(sessionAttribute)) {//校验验证码
            return true;
        }
        response.getWriter().println(JSON.toJSONString(Result.Failure(Code.Code_Login_Failure, "IncorrectCode!")));//验证码不通过
        return false;
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
