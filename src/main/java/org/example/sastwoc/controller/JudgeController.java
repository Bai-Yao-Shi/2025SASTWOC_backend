package org.example.sastwoc.controller;

import org.example.sastwoc.entity.JudgeData;
import org.example.sastwoc.entity.ScoreRepository;
import org.example.sastwoc.service.JudgeService;
import org.example.sastwoc.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/judge")
public class JudgeController {
    @Autowired
    private JudgeService judgeService;

    //评分
    @PostMapping()
    public Result judgeRate(@RequestBody ScoreRepository score) {
        judgeService.judge(score);
        return Result.success();
    }

    //修改评分
    @PatchMapping()
    public Result judgeEdit(@RequestBody ScoreRepository score) {
        judgeService.editScore(score);
        return Result.success();
    }


}
