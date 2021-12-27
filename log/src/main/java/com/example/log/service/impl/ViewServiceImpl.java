package com.example.log.service.impl;

import com.example.log.entity.View;
import com.example.log.mapper.ViewMapper;
import com.example.log.service.ViewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ViewServiceImpl implements ViewService {
    @Autowired
    private ViewMapper viewMapper;

    @Override
    public void insertView(View view) {
        viewMapper.insertView(view);
    }
}
