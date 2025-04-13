package com.sunho.travel.service.serviceImpl;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.sunho.travel.domain.user.User;
import com.sunho.travel.domain.user.UserDto;
import com.sunho.travel.repository.user.UserRepository;
import com.sunho.travel.service.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserRepository userRepository;

    @Override
    public void signUp(UserDto userDto, String role) {
        userDto.setPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));
        userDto.setRole(role);
        User user = userDto.toEntity();
        userRepository.save(user);
    }
}
