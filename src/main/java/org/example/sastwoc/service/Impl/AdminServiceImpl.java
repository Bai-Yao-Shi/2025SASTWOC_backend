package org.example.sastwoc.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.example.sastwoc.DTO.JudgeCreateDto;
import org.example.sastwoc.DTO.JudgeDeleteDto;
import org.example.sastwoc.DTO.SecondaryAdminCreateDto;
import org.example.sastwoc.entity.*;
import org.example.sastwoc.exception.SystemException;
import org.example.sastwoc.mapper.CompetitionMapper;
import org.example.sastwoc.mapper.PersonMapper;
import org.example.sastwoc.mapper.TeamMapper;
import org.example.sastwoc.service.AdminService;
import org.example.sastwoc.vo.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.*;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.core.metadata.IPage;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdminServiceImpl implements AdminService{
    @Autowired
    private CompetitionMapper competitionMapper;
    @Autowired
    private TeamMapper teamMapper;
    @Autowired
    private PersonMapper personMapper;



    @Override
    public void addCompetition(Competition competition) {
        int count = Math.toIntExact(competitionMapper.selectCount(null));
        if (count == 0) {
            // 表为空，重置自增值为 1
            competitionMapper.resetAutoIncrement();
        }
        // 插入用户数据
        LocalDateTime currentDateTime = LocalDateTime.now();
        competition.setCreateTime(String.valueOf(currentDateTime));
        competitionMapper.insert(competition);

    }

    //实现获取比赛列表
    @Override
    public List<Competition> getCompetitions() {
        List<Competition> competitions = competitionMapper.selectList(null);
        return competitions;
    }

    @Override
    public List<Team> getTeams(int comId) {
        //把teamId和comId相同的放在一个列表中
        //根据comId寻找
        List<Team> teams = new ArrayList<>();
        List<Integer> teamIds=personMapper.selectTeamIdByComId(comId);
        System.out.println(teamIds);

        for(int i=0;i<teamIds.size();i++){
            Team team=new Team();
            team.setId(teamIds.get(i));
            team.setComId(comId);
            String teamName= personMapper.getTeamNameByTeamId(teamIds.get(i));
            team.setName(teamName);
            String captainName= personMapper.getCaptainNameByTeamId(teamIds.get(i));
            Integer captainId=personMapper.getCaptainIdByTeamId(teamIds.get(i));
            team.setCaptainName(captainName);
            team.setCaptainId(captainId);
            team.setStatus(0);
            //下面完成name的拼接
             List<Integer> instructorId=personMapper.getInstructorsIdByTeamId(teamIds.get(i));
             List<String> instructorName=personMapper.getInstructorsNameByTeamId(teamIds.get(i));
             String instructorNames=instructorId.stream()
                .map(item1 -> item1 + instructorName.get(instructorId.indexOf(item1)))
                .collect(Collectors.joining(","));

             List<Integer> memberId=personMapper.getMembersIdByTeamId(teamIds.get(i));
             List<String> memberName=personMapper.getMembersNameByTeamId(teamIds.get(i));
             String memberNames=memberId.stream()
                .map(item1 -> item1 + memberName.get(memberId.indexOf(item1)))
                .collect(Collectors.joining(","));

             team.setMemberNames(memberNames);
             team.setInstructorNames(instructorNames);

             teams.add(team);
        }


        return teams;
    }

    @Override
    public void createCaptain(Person person) {
        //查询是否存在userCode，存在就改is_captain和comId,不存在就抛出异常
        QueryWrapper<Person> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_code", person.getUserCode());
        Person existingPerson = personMapper.selectOne(queryWrapper);

            //分配一个不存在的teamId，但我真不会写
            Random random=new Random();
            Integer teamId=random.nextInt(10000000);

        if (existingPerson != null) {
            existingPerson.setTeamId(teamId);
            existingPerson.setIsCaptain(1);
            existingPerson.setComId(person.getComId());
            personMapper.update(existingPerson, queryWrapper);
        }else {
            throw new SystemException(Status.USER_NOT_EXIST);
        }




    }

    @Override
    public void createJudge(JudgeCreateDto judge) {
        personMapper.setJudgeByUserCode(judge.getUserCode());
        personMapper.setComIdByUserCode(judge.getUserCode(),judge.getComId());
    }

    @Override
    public void deleteJudge(JudgeDeleteDto judgeDeleteDto) {
        personMapper.deleteJudgeByUserCode(judgeDeleteDto.getUserCode());
        personMapper.deleteComIdByUserCode(judgeDeleteDto.getUserCode());

    }

    @Override
    public void createSecondaryAdmin(SecondaryAdminCreateDto secondaryAdmin) {
        personMapper.createSecondaryAdminByUserCode(secondaryAdmin.getUserCode(),secondaryAdmin.getAcademyId(),secondaryAdmin.getComId());
    }

    @Override
    public void deleteSecondaryAdmin(SecondaryAdminCreateDto secondaryAdmin) {
        personMapper.deleteSecondaryAdminByUserCode(secondaryAdmin.getUserCode());

    }

    @Override
    public List<JudgeInformation> getJudgeList(Integer comId) {
        List<JudgeInformation> judgeInformation = new ArrayList<>();
        List<String> judgeName=personMapper.getJudgeNameByComId(comId);
        List<String> studentId=personMapper.getJudgeStudentIdByComId(comId);
        List<String> phone=personMapper.getJudgePhoneByComId(comId);
        for(int i=0;i<judgeName.size();i++){
            JudgeInformation judge=new JudgeInformation();
            judge.setName(judgeName.get(i));
            judge.setStudentId(studentId.get(i));
            judge.setPhone(phone.get(i));
            judgeInformation.add(judge);
        }
        return judgeInformation;
    }


}
