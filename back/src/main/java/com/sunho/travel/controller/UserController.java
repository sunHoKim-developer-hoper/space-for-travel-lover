package com.sunho.travel.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sunho.travel.domain.user.UserDto;
import com.sunho.travel.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping(path ="signup")
    public ResponseEntity<Void> signUp(@RequestBody UserDto user){ //body가 없으면 Void를 주면 된다.
        userService.signUp(user, "MEMBER");
        return ResponseEntity.status(200).build();
    }
}
