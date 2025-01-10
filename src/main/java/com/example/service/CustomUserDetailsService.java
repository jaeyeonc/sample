package com.example.service;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 여기서 사용자 정보를 데이터베이스 또는 기타 소스에서 로드
        if (username.equals("admin")) {
            return new CustomUserDetails(
                "admin",
                "{noop}password", // {noop}: 비밀번호 기본 암호화
                List.of(
                    new SimpleGrantedAuthority("ROLE_ADMIN"), // 역할
                    new SimpleGrantedAuthority("USER_READ")  // 권한
                )
            );
        } else if (username.equals("user")) {
            System.out.println("user 계정 -------");
            return new CustomUserDetails(
                "user",
                "{noop}password",
                List.of(
                    new SimpleGrantedAuthority("ROLE_USER"),  // 역할
                    new SimpleGrantedAuthority("USER_WRITE") // 권한
                )
            );
        } else {
            throw new UsernameNotFoundException("사용자를 찾을 수 없습니다.");
        }
    }
}