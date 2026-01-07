package com.security.auth.authsecurity.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GreetingController {
    @RequestMapping(value = "/greeting", method = RequestMethod.GET)
    public ResponseEntity<String> greeting(){
        return ResponseEntity.ok("Authorization Successful!");
    }
}
