package com.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 회원가입 후 메인페이지로
 */
@Controller
public class MainController {
    @GetMapping(value = "/")
    public String main(){
        return "main";
    }
}
