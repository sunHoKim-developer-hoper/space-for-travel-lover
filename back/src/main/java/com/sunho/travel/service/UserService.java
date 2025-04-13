package com.sunho.travel.service;

import com.sunho.travel.domain.user.UserDto;

public interface UserService {
    void signUp(UserDto dto, String role);   
}
