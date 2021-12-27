package com.example.login.service;

import com.example.login.entity.Account;
import com.example.login.entity.Homepage;
import org.springframework.beans.factory.annotation.Autowired;

public interface UserService {
    Account findByAccount(String account);
    void updateLatestLoginInfo(Account account);
    void registerUser(Account account);
}
