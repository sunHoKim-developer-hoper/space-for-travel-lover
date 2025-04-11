package com.sunho.travel.repository.user;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sunho.travel.enrity.user.User;

public interface UserRepository extends JpaRepository<User, String>{
}