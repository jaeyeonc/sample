package com.example.dao;

import com.example.domain.UserData;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserDataMapper {
    // 단일 데이터 삽입
    void insertUserData(UserData userData);

    // 여러 데이터 삽입 (배치)
    void insertBulkUserData(List<UserData> userDataList);
}