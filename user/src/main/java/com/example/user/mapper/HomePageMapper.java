package com.example.user.mapper;

import com.example.user.entity.Homepage;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface HomePageMapper {
    Homepage findHomepageById(Long id);
    List<Homepage> findHomepageByNickname(String nickname);
    void insertHomePage(Long id);
    void updateHomePage(Homepage homepage);
}