package com.example.login.service.impl;

import com.example.login.entity.Account;
import com.example.login.entity.Homepage;
import com.example.login.mapper.UserMapper;
import com.example.login.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public Account findByAccount(String account) {
        return userMapper.findByAccount(account);
    }

    @Override
    public void updateLatestLoginInfo(Account account) {
        userMapper.updateLatestLoginInfo(account);
    }

    @Override
    public void registerUser(Account account) {
        userMapper.registerUser(account);
    }
}
