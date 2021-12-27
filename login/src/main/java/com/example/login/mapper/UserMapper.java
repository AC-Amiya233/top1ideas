package com.example.login.mapper;

import com.example.login.entity.Account;
import com.example.login.entity.Homepage;
import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface UserMapper {
    Account findByAccount(String account);
    void updateLatestLoginInfo(Account account);
    void registerUser(Account account);
}
