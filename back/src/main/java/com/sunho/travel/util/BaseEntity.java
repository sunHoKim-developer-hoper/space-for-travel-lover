package com.sunho.travel.util;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;

@MappedSuperclass //JPA에서 이 클래스의 필드를 하위 엔티티에 매핑시켜주는 어노테이션
public abstract class BaseEntity {
    
    @Column(name = "ins_at", updatable = false)
    private LocalDateTime createAt;

    @Column(name = "ins_user", updatable = false)
    private String createBy;

    @Column(name = "mod_at", updatable = false)
    private LocalDateTime updateAt;

    @Column(name = "mod_user", updatable = false)
    private String updateBy;

    @PrePersist
    protected void onCreate(){
        this.createAt = LocalDateTime.now();
        //UserDetails 혹은 username이 null이면 System
        this.createBy = Optional.ofNullable(UserPrincipalUtil.getUserDetails()).map(UserDetails::getUsername).orElse("System"); 
        this.updateAt = LocalDateTime.now();
        this.updateBy = Optional.ofNullable(UserPrincipalUtil.getUserDetails()).map(UserDetails::getUsername).orElse("System");
    }

    @PreUpdate
    protected void onUpdate(){
        this.updateAt = LocalDateTime.now();
        this.updateBy = Optional.ofNullable(UserPrincipalUtil.getUserDetails()).map(UserDetails::getUsername).orElse("System");
    }

}
