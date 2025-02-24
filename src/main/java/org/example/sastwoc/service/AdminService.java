package org.example.sastwoc.service;

import org.example.sastwoc.DTO.JudgeCreateDto;
import org.example.sastwoc.DTO.JudgeDeleteDto;
import org.example.sastwoc.DTO.SecondaryAdminCreateDto;
import org.example.sastwoc.entity.Competition;
import org.example.sastwoc.entity.JudgeInformation;
import org.example.sastwoc.entity.Person;
import org.example.sastwoc.entity.Team;
import org.springframework.stereotype.Service;

import java.util.List;

public interface AdminService {
    public void addCompetition(Competition competition);

    //获取比赛列表，超管和二管可以看到所有比赛
    public List<Competition> getCompetitions();

//    //获取队伍列表
//    public List<Team> getTeams(int comId);
//
//    //根据comId返回name列表
//    public List<String> getNamesByComId(int comId);


    //获取队伍列表
    public List<Team> getTeams(int comId);

    //创建队长
    public void createCaptain(Person person);

    //创建评委
    public void createJudge(JudgeCreateDto judge);

    //删除评委
    public void deleteJudge(JudgeDeleteDto judge);

    //创建二级管理员
    public void createSecondaryAdmin(SecondaryAdminCreateDto secondaryAdmin);

    //删除二级管理员
    public void deleteSecondaryAdmin(SecondaryAdminCreateDto secondaryAdmin);

    //获取评委列表
    public List<JudgeInformation> getJudgeList(Integer comId);
}
