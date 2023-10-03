package com.x61.filters;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//@Configuration
//@Order(-1)
//@WebFilter(filterName = "CorsFilter ", urlPatterns = "/*")
public class CorsFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String origin = request.getHeader("Origin");
        if (origin == null) {
            origin = request.getHeader("Referer");
        }
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
        response.setHeader("Access-Control-Allow-Origin", origin);//这里不能写*，*代表接受所有域名访问，如写*则下面一行代码无效。谨记
        response.setHeader("Access-Control-Allow-Credentials", "true");//true代表允许携带cookie
        response.setHeader("Access-Control-Allow-Headers", "Content-Type,Authorization");
//        resp.setHeader("Set-Cookie", "HttpOnly;Secure;SameSite=None");
        chain.doFilter(servletRequest, servletResponse);
    }
}