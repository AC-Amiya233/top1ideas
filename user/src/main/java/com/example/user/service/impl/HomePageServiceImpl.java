package com.example.user.service.impl;

import com.example.user.entity.Homepage;
import com.example.user.mapper.HomePageMapper;
import com.example.user.service.HomePageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HomePageServiceImpl implements HomePageService {
    @Autowired
    private HomePageMapper homePageMapper;

    @Override
    public Homepage findHomepageById(Long id) {
        return homePageMapper.findHomepageById(id);
    }

    @Override
    public List<Homepage> findHomepageByNickname(String nickname) {
        return homePageMapper.findHomepageByNickname(nickname);
    }

    @Override
    public void insertHomePage(Long id) {
        homePageMapper.insertHomePage(id);
    }

    @Override
    public void updateHomePage(Homepage homepage) { homePageMapper.updateHomePage(homepage); }
}
