package com.example.papers.mapper;

import com.example.papers.entity.Paper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PaperMapper {
    List<Paper> findAll(Integer pageNumber, Integer pageSize);

    List<Paper> findById(Long id);

    List<Paper> findByKeyword(String keyword, Integer pageNumber, Integer pageSize);

    List<Paper> findByPublish(String publish);

    int insertBatch(List<Paper> papers);

    int update(List<Paper> papers);
}
