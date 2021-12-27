package com.example.user.service;

import com.example.user.entity.Homepage;

import java.util.List;

public interface HomePageService {
    Homepage findHomepageById(Long id);
    List<Homepage> findHomepageByNickname(String nickname);
    void insertHomePage(Long id);
    void updateHomePage(Homepage homepage);
}