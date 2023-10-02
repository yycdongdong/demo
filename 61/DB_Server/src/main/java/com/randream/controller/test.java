package com.randream.controller;


import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", allowCredentials = "true")
@RestController
@RequestMapping("/v2")
public class test {

    @RequestMapping("/test")
    public String test() {
        System.out.println("OK");
        return "OK";
    }


}
