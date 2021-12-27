package com.example.papers.service.impl;

import com.example.papers.entity.Paper;
import com.example.papers.mapper.PaperMapper;
import com.example.papers.service.PaperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaperServiceImpl implements PaperService {
    @Autowired
    private PaperMapper paperMapper;

    @Override
    public List<Paper> findAll(Integer pageNumber, Integer pageSize) {
        List<Paper> result = paperMapper.findAll(pageNumber, pageSize);
        return result;
    }

    @Override
    public List<Paper> findById(Long id) {
        return paperMapper.findById(id);
    }

    @Override
    public List<Paper> findByKeyword(String keyword, Integer pageNumber, Integer pageSize) {
        return paperMapper.findByKeyword(keyword, pageNumber, pageSize);
    }

    @Override
    public List<Paper> findByPublish(String publish) {
        return paperMapper.findByPublish(publish);
    }

    @Override
    public int insertBatch(List<Paper> papers) {
        paperMapper.insertBatch(papers);
        return papers.size();
    }

    @Override
    public int update(List<Paper> papers) {
        paperMapper.update(papers);
        return papers.size();
    }
}
