package com.sunho.travel.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sunho.travel.domain.user.UserDto;
import com.sunho.travel.service.ExchangeRateService;
import com.sunho.travel.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/public/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final ExchangeRateService exchangerateService;

    @PostMapping(path = "signup")
    public ResponseEntity<Void> signUp(@RequestBody UserDto user) { // body가 없으면 Void를 주면 된다.
        userService.signUp(user, "MEMBER");
        return ResponseEntity.status(200).build();
    }

    @GetMapping(path = "test")
    public Map<String,String> postMethodName() {
        return exchangerateService.getExchangeRates();
    }
}
