package org.example.sastwoc.service;

import org.example.sastwoc.DTO.MemberInformation;
import org.example.sastwoc.entity.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CaptainService {

    //添加成员
    public void addMember(Member member);
    public void addMember(Member member, Integer teamId);

    //删除成员
    public void deleteMember(int id);

    //更新队伍信息
    public Team updateInformation(TeamInformation teamInformation);

    //获取比赛列表，仅自己比赛
    public List<Competition> getCompetitions(int captain_id);

    //获取成员列表
    public List<MemberInformation>  getMemberList(Integer teamId);

    //修改成员信息
    public void editMember(Member member);

    //获取队伍信息
    public TeamModel getTeamInformation(Integer teamId);


}
