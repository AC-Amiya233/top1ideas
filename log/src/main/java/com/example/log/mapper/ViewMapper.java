package com.example.log.mapper;

import com.example.log.entity.View;
import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface ViewMapper {
    void insertView(View view);
}