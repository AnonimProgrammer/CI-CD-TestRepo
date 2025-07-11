package com.javaadvanced.ultimatespringboot.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class Controller {

    @GetMapping("/hello")
    public ResponseEntity<String> sayHello(){
        String welcome = "Hello, World!";
        return ResponseEntity.ok(welcome);
    }

}
