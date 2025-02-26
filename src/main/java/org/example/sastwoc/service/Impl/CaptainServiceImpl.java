package org.example.sastwoc.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.example.sastwoc.DTO.MemberInformation;
import org.example.sastwoc.entity.*;
import org.example.sastwoc.exception.SystemException;
import org.example.sastwoc.mapper.*;
import org.example.sastwoc.service.CaptainService;
import org.example.sastwoc.vo.Status;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;



@Service
public class CaptainServiceImpl implements CaptainService {
    @Autowired
    private MemberMapper memberMapper;
    @Autowired
    private TeamMapper teamMapper;
    @Autowired
    private PersonMapper personMapper;
    @Autowired
    private DefaultErrorAttributes errorAttributes;
    @Autowired
    private CompetitionMapper competitionMapper;
    @Autowired
    private WorksMapper worksMapper;

    //添加成员
    @Override
    public void addMember(Member member) {

        //memberMapper.insert(member);
    }
    @Override
    public void addMember(Member member, Integer teamId) {
        Integer comId=personMapper.getComIdByTeamId(teamId);
        Integer captainId=personMapper.getCaptainIdByTeamId(teamId);

        Person person=new Person();
        BeanUtils.copyProperties(member,person);

        QueryWrapper<Person> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id",member.getId());
        Person existingPerson=personMapper.selectOne(queryWrapper);

        System.out.println(existingPerson);
            System.out.println(existingPerson);

        if(existingPerson!=null){
//            if(existingPerson.getTeamId()!=teamId){
//                throw new SystemException(Status.USER_ACCESS_NOT_ALLOWED);
//            }

            existingPerson.setTeamId(teamId);
            existingPerson.setComId(comId);
            existingPerson.setCaptainId(captainId);
            existingPerson.setIsCaptain(0);
            System.out.println(existingPerson);
            personMapper.updateById(existingPerson);
        }else{
            person.setTeamId(teamId);
            person.setComId(comId);
            person.setCaptainId(captainId);
            person.setIsCaptain(0);

            personMapper.insert(person);
        }


    }



    //删除成员
@Override
@Transactional
public void deleteMember(int id) {
    // 根据id查询，并修改teamId,comId，判断是否在队内
    QueryWrapper<Person> queryWrapper = new QueryWrapper<>();
    queryWrapper.eq("id", id);
    Person existingPerson = personMapper.selectOne(queryWrapper);

    if (existingPerson != null) {
        // 将comId和teamId设置为null
        //personMapper.setNULLTeamIdByIdExceptCaptain(id);
        //personMapper.setNULLComIdByIdExceptCaptain(id);
        personMapper.deleteById(existingPerson.getId());
    } else {
        // 如果找不到对应用户，则抛出异常
        throw new SystemException(Status.USER_NOT_EXIST);
    }
}

    //更新队伍信息
    @Override
    public Team updateInformation(TeamInformation teamInformation) {

        Person person=new Person();
       //teamId会根据id进行查找
        //此处修改过
        Integer teamId=personMapper.getTeamIdById(teamInformation.getCaptainId());


        person.setTeamId(teamId);
        person.setId(teamInformation.getId());
        person.setName(teamInformation.getCaptainName());
        person.setComId(teamInformation.getComId());
        person.setCaptainId(teamInformation.getCaptainId()) ;
        personMapper.updateById(person);


        //检查成员是否在队中
        List <Integer> memberIds=teamInformation.getMemberIds();
        for(int i=0;i<memberIds.size();i++){
            Integer realTeamId=personMapper.getTeamIdById(teamInformation.getCaptainId());
            Integer memberId=memberIds.get(i);
            Integer memberTeamId=personMapper.getTeamIdById(memberId);



            System.out.println(memberTeamId);
            System.out.println(realTeamId);

            if (memberTeamId == null) {
                 throw new SystemException(Status.USER_NOT_EXIST);
            }

            if(realTeamId.intValue()!=memberTeamId.intValue()){
                throw new SystemException(Status.USER_NOT_EXIST);
            }
        }


        List<Integer> originalMemberId=personMapper.selectMembersIdByTeamId(teamId);

        for(int i=0;i<originalMemberId.size();i++){
            personMapper.setNULLCaptainIdByIdExceptCaptain(originalMemberId.get(i));
            personMapper.setNULLTeamIdByIdExceptCaptain(originalMemberId.get(i));
            personMapper.setNULLComIdByIdExceptCaptain(originalMemberId.get(i));
            personMapper.setNULLIsInstructorByIdExceptCaptain(originalMemberId.get(i));
            personMapper.setNULLIsCaptainByIdExceptCaptain(originalMemberId.get(i));
        }
        personMapper.updateTeamIdById(teamInformation.getId(), teamId);


        //根据memberId设置member的teamId和comId
        System.out.println(teamInformation.getMemberIds());
            System.out.println(teamInformation.getMemberIds());
            System.out.println(teamInformation.getMemberIds());

        System.out.println(person.getTeamId());

        for(int i=0;i<teamInformation.getMemberIds().toArray().length;i++){


            personMapper.updateTeamIdById(teamInformation.getMemberIds().get(i),person.getTeamId());
            personMapper.updateComIdById(teamInformation.getMemberIds().get(i),person.getComId());
        }

        //设置instructorId
        //根据teamId找到membersId
        List<Integer> membersId=personMapper.getMembersIdByTeamId(person.getTeamId());

        //遍历列表，将队伍中的isInstructor=0改为1以及isCaptain
        for(int i=0;i<personMapper.countMembersByTeamId(person.getTeamId());i++){
            personMapper.setIsInstructorFalse(membersId.get(i));
            personMapper.setIsCaptainFalse(membersId.get(i));
            personMapper.setCaptainIdById(membersId.get(i),person.getCaptainId());
            personMapper.setTeamNameById(teamInformation.getName(), membersId.get(i));
        }
        personMapper.setIsCaptainTrue(teamInformation.getCaptainId());

        for(int i=0;i<teamInformation.getInstructorIds().toArray().length;i++){
            personMapper.setIsInstructorTrue(teamInformation.getInstructorIds().get(i));
        }

        //注意！此处未完成作品名字和队长名字
        Team team=new Team();
        team.setId(teamId);
        team.setName(teamInformation.getName());
        team.setCaptainId(teamInformation.getCaptainId());
        team.setCaptainName(teamInformation.getCaptainName());
        team.setComId(teamInformation.getComId());
        team.setStatus(0);
        //下面完成name的拼接
        List<String> instructorStudentId=personMapper.getInstructorStudentIdByTeamId(teamId);
        List<String> instructorName=personMapper.getInstructorsNameByTeamId(teamId);
        String instructorNames=instructorStudentId.stream()
                .map(item1 -> item1 + instructorName.get(instructorStudentId.indexOf(item1)))
                .collect(Collectors.joining(","));


        List<String> membersStudentId=personMapper.getStudentIdByTeamId(teamId);
        List<Integer> memberId=personMapper.getMembersIdByTeamId(teamId);
        List<String> memberName=personMapper.getMembersNameByTeamId(teamId);
        String memberNames=membersStudentId.stream()
                .map(item1 -> item1 + memberName.get(membersStudentId.indexOf(item1)))
                .collect(Collectors.joining(","));

        team.setMemberNames(memberNames);
        team.setInstructorNames(instructorNames);

        System.out.println(teamId);
        System.out.println(teamId);
        System.out.println(teamId);
        System.out.println(teamId);



        // 查询是否存在该 teamId
        QueryWrapper<Works> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("team_id", teamId);

        Works existingWork = worksMapper.selectOne(queryWrapper);  // 查询结果

         if (existingWork != null) {
            // 如果存在，则更新
            existingWork.setName(teamInformation.getName());
            existingWork.setComId(teamInformation.getComId());
            existingWork.setTeamId(teamId);
            worksMapper.update(existingWork, queryWrapper);
        } else {
            // 如果不存在，则插入
            Works newWork = new Works();
            newWork.setName(teamInformation.getName());
            newWork.setComId(teamInformation.getComId());
            newWork.setTeamId(teamId);
            worksMapper.insert(newWork);
        }

        return team;
    }




    //队长仅获取自己的比赛
    @Override
    public List<Competition> getCompetitions(int captain_id) {
        Integer comId=personMapper.getComIdByCaptainId(captain_id);
        QueryWrapper<Competition> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("id",comId);
        List competition=competitionMapper.selectList(queryWrapper);
        return competition;
    }


    //获取成员信息
    @Override
    public List<MemberInformation> getMemberList(Integer teamId) {
        List<MemberInformation> memberList=new ArrayList<>();
        Integer membersNums=personMapper.countMembersByTeamId(teamId);
        List<Integer> memberIds=personMapper.selectMembersIdByTeamId(teamId);
        List<String> memberNames=personMapper.getMembersNameByTeamId(teamId);



        for(int i=0;i<membersNums;i++){
            MemberInformation member=new MemberInformation();
            member.setStudentId(String.valueOf(memberIds.get(i)));
            member.setName(memberNames.get(i));
            //member.setAcademy(personMapper.getAcademyNameById(memberIds.get(i)));
            member.setAcademy(personMapper.getAcademyIdById(memberIds.get(i)));

            memberList.add(member);
        }

        return memberList;
    }

    //修改成员信息
    @Override
    public void editMember(Member member) {
        Person person=new Person();
        BeanUtils.copyProperties(member,person);

        QueryWrapper<Person> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", person.getId());
        Person existingPerson = personMapper.selectOne(queryWrapper);
        if (existingPerson != null) {
            existingPerson.setName(person.getName());
            existingPerson.setStudentId(person.getStudentId());
            //existingPerson.setTeamId(person.getTeamId());
            existingPerson.setAcademyId(person.getAcademyId());
            existingPerson.setPhone(person.getPhone());


            personMapper.updateById(existingPerson);
        }else{
            throw new SystemException(Status.USER_NOT_EXIST);
        }


        personMapper.updateById(person);
    }


    //获取队伍信息
    @Override
    public TeamModel getTeamInformation(Integer teamId) {
        TeamModel teamModel=new TeamModel();

        Integer comId=personMapper.getComIdByTeamId(teamId);
        String name=personMapper.getTeamNameByTeamId(teamId);
        Integer captainId=personMapper.getCaptainIdByTeamId(teamId);
        Integer status=personMapper.getStatusByTeamId(teamId);
        String captainName=personMapper.getCaptainNameByTeamId(teamId);

        List<String> instructorStudentId=personMapper.getInstructorStudentIdByTeamId(teamId);
        List<String> instructorName=personMapper.getInstructorsNameByTeamId(teamId);
        String instructorNames=instructorStudentId.stream()
                .map(item1 -> item1 + instructorName.get(instructorStudentId.indexOf(item1)))
                .collect(Collectors.joining(","));


        List<String> membersStudentId=personMapper.getStudentIdByTeamId(teamId);
        List<Integer> memberId=personMapper.getMembersIdByTeamId(teamId);
        List<String> memberName=personMapper.getMembersNameByTeamId(teamId);
        String memberNames=membersStudentId.stream()
                .map(item1 -> item1 + memberName.get(membersStudentId.indexOf(item1)))
                .collect(Collectors.joining(","));


        teamModel.setId(teamId);
        teamModel.setComId(comId);
        teamModel.setName(name);
        teamModel.setCaptainId(captainId);
        teamModel.setCaptainName(captainName);
        teamModel.setStatus(status);
        teamModel.setInstructorNames(instructorNames);
        teamModel.setMemberNames(memberNames);

        teamModel.setId(teamId);

        return teamModel;
    }



}
