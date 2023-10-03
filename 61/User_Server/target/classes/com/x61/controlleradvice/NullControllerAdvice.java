package com.x61.controlleradvice;

import com.x61.bean.Result;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class NullControllerAdvice {

    @ExceptionHandler(value = {NullPointerException.class})
    public Result handleNullException(Exception e) {
        Result resp = null;
        // BeanValidation exception

        resp = Result.Error((long) HttpStatus.BAD_REQUEST.value(), "信息不能为空，请将信息补充完整");
        return resp;
    }
}