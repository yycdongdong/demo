package com.x61.utils;


import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class JWTUtil {
    //设置过期时间
    private static final long EXPIRE_DATE = 2592000000L;//一个月
    //token秘钥
    private static final String TOKEN_SECRET = "randream2022";

    public static String getToken(Long userId, Long state) {//String name,

        String token = "";
        try {
            //过期时间
            Date date = new Date(System.currentTimeMillis() + EXPIRE_DATE);
            //秘钥及加密算法
            Algorithm algorithm = Algorithm.HMAC256(TOKEN_SECRET);
            //设置头部信息
            Map<String, Object> header = new HashMap<>();
            header.put("typ", "JWT");
            header.put("alg", "HS256");
            //携带username，password信息，生成签名
            token = JWT.create()
                    .withHeader(header)
                    //.withClaim("name",name)
                    .withClaim("userId", userId)
                    .withClaim("state", state)
                    .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRE_DATE))//过期
                    .sign(algorithm);
        } catch (Exception e) {
            log.error("JWT生成失败");
            e.printStackTrace();
            return null;
        }
        return token;
    }

    public static Map verify(String token) {
        /**
         * @desc 验证token，通过返回true
         * @params [token]需要校验的串
         **/
        Map result = new HashMap();
        try {
            Algorithm algorithm = Algorithm.HMAC256(TOKEN_SECRET);
            JWTVerifier verifier = JWT.require(algorithm).build();
            DecodedJWT jwt = verifier.verify(token);
            result.put("userId", jwt.getClaim("userId").asLong());
            result.put("isSuccess", true);
            result.put("state", jwt.getClaim("state").asLong());
            return result;
        } catch (Exception e) {
            log.error(String.valueOf(e.getClass()));
            e.printStackTrace();
            result.put("isSuccess", false);
            result.put("userId", null);
            result.put("state", null);
            return result;
        }
    }
}
