package com.example.papers.service;

import com.example.papers.entity.Paper;

import java.util.List;

public interface PaperService {
    List<Paper> findAll(Integer pageNumber, Integer pageSize);

    List<Paper> findById(Long id);

    List<Paper> findByKeyword(String keyword, Integer pageNumber, Integer pageSize);

    List<Paper> findByPublish(String publish);

    int insertBatch(List<Paper> papers);

    int update(List<Paper> papers);
}
