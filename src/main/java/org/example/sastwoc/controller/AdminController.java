package org.example.sastwoc.controller;

import org.example.sastwoc.DTO.JudgeCreateDto;
import org.example.sastwoc.DTO.JudgeDeleteDto;
import org.example.sastwoc.DTO.SecondaryAdminCreateDto;
import org.example.sastwoc.entity.Competition;
import org.example.sastwoc.entity.Member;
import org.example.sastwoc.service.AdminService;
import org.example.sastwoc.utils.JwtUtils;
import org.example.sastwoc.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;


@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private AdminService adminService;
    @Autowired
    private HttpServletRequest httpServletRequest;

    //创建比赛
    @PostMapping("/competition")
    public Result competitionCreate(@RequestBody Competition competition){


        //String userCode = (JwtUtils.resolveJwt(httpServletRequest.getHeader("token"))).getUser_code();

        String authorization=httpServletRequest.getHeader("Authorization");
        String token = authorization.replace("Bearer ", "");
        String userCode = JwtUtils.resolveJwt(token).getUser_code();


        competition.setWorkCode(userCode);
        adminService.addCompetition(competition);
        return Result.success(competition);
    }

    //创建二级管理员
    @PostMapping("/academy")
    public Result secondaryAdminCreate(@RequestBody SecondaryAdminCreateDto secondaryAdmin){
        adminService.createSecondaryAdmin(secondaryAdmin);
        return Result.success();
    }

    //删除二级管理员
    @DeleteMapping("/academy")
    public Result secondaryAdminDelete(@RequestBody SecondaryAdminCreateDto secondaryAdmin){
        adminService.deleteSecondaryAdmin(secondaryAdmin);
        return Result.success();
    }

    //创建评委
    @PostMapping("/judge")
    public Result judgeCreate(@RequestBody JudgeCreateDto judge){
        adminService.createJudge(judge);
        return Result.success();
    }

    //删除评委
    @DeleteMapping("/judge")
    public Result judgeDelete(@RequestBody JudgeDeleteDto judge){
        adminService.deleteJudge(judge);
        return Result.success();
    }

    //获取评委列表
    @GetMapping("/judge")
    public Result judgeGet(@RequestParam int comId) {
        return Result.success(adminService.getJudgeList(comId));
    }






}
