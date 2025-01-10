package com.example.service;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.List;

public class UserDetailsExample {

    public CustomUserDetails createUserDetails() {
        String username = "user";
        String password = "password";

        // 권한 및 역할 추가
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_USER")); // 역할 추가
        authorities.add(new SimpleGrantedAuthority("USER_WRITE")); // 권한 추가

        return new CustomUserDetails(username, password, authorities);
    }
}