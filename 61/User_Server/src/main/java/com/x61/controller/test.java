package com.x61.controller;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v2")
public class test {

    @RequestMapping("/test")
    public String test() {
        System.out.println("OK");
        return "OK";
    }


}
