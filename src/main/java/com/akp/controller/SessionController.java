package com.akp.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * author Aashish Patel
 */
@RestController
public class SessionController {

    @GetMapping("/api/rest/hello")
    public String sayHello() {
        return "Hello World";
    }
}
