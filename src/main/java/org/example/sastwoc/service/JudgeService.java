package org.example.sastwoc.service;

import org.example.sastwoc.entity.JudgeData;
import org.example.sastwoc.entity.ScoreRepository;
import org.springframework.stereotype.Service;

@Service
public interface JudgeService {
    //评分
    void judge(ScoreRepository score);

    //修改评分
    void editScore(ScoreRepository score);
}
