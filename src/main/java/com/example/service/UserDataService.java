package com.example.service;

import com.example.dao.UserDataMapper;
import com.example.domain.UserData;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserDataService {

    private final UserDataMapper userDataMapper;

    public UserDataService(UserDataMapper userDataMapper) {
        this.userDataMapper = userDataMapper;
    }

    @Transactional
    public void saveUserData(UserData userData) {
        userDataMapper.insertUserData(userData); // 단일 데이터 삽입
    }

    @Transactional
    public void insertBulkUserData(List<UserData> userDataList) {
        userDataMapper.insertBulkUserData(userDataList); // 데이터 리스트 삽입
    }
}