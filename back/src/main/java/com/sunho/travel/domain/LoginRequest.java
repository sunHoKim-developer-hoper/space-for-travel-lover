package com.sunho.travel.domain;

import lombok.Data;

@Data
public class LoginRequest {
    private String id;
    private String password;
}
