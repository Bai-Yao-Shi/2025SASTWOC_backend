package org.example.sastwoc.service.Impl;

import org.example.sastwoc.entity.ScoreRepository;
import org.example.sastwoc.mapper.ScoreMapper;
import org.example.sastwoc.service.JudgeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class JudgeServiceImpl implements JudgeService {
    @Autowired
    private ScoreMapper scoreMapper;


    //评分
    @Override
    public void judge(ScoreRepository score) {
        scoreMapper.insertOrUpdate(score);
    }

    //修改评分
    @Override
    public void editScore(ScoreRepository score) {
        scoreMapper.insertOrUpdate(score);
    }
}
