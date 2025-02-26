package org.example.sastwoc.controller;

import io.swagger.v3.oas.models.security.SecurityScheme;
import org.example.sastwoc.DTO.DeleteRequest;
import org.example.sastwoc.DTO.MemberInformation;
import org.example.sastwoc.entity.Member;
import org.example.sastwoc.entity.Team;
import org.example.sastwoc.entity.TeamInformation;
import org.example.sastwoc.entity.TeamModel;
import org.example.sastwoc.exception.SystemException;
import org.example.sastwoc.mapper.PersonMapper;
import org.example.sastwoc.service.AdminService;
import org.example.sastwoc.service.CaptainService;
import org.example.sastwoc.utils.JwtUtils;
import org.example.sastwoc.vo.Result;
import org.example.sastwoc.vo.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/captain")
public class CaptainController {
    @Autowired private CaptainService captainService;
    @Autowired private HttpServletRequest httpServletRequest;
    @Autowired
    private PersonMapper personMapper;


    //更新队伍信息
    @PostMapping("/team")
    public Result teamUpdate(@RequestBody TeamInformation teamInformation){
        Team team = captainService.updateInformation(teamInformation);
        return Result.success(team);
    }
    //添加成员
    @PostMapping("/team/{teamId}/member")
    public Result teamMemberAdd(@PathVariable Integer teamId,@RequestBody Member member){
        String authorization=httpServletRequest.getHeader("Authorization");
        String token = authorization.replace("Bearer ", "");
        String userCode = JwtUtils.resolveJwt(token).getUser_code();
        Integer myTeamId = personMapper.getTeamIdByUserCode(userCode);
        if(myTeamId.intValue()!=teamId.intValue()){
            throw new SystemException(Status.USER_ACCESS_NOT_ALLOWED);
        }
         captainService.addMember(member,teamId);
         return Result.success();
    }

    //删除成员
    @DeleteMapping("/team/{teamId}/member")
    public Result teamMemberDelete(@PathVariable Integer teamId, @RequestBody DeleteRequest request){
        String authorization=httpServletRequest.getHeader("Authorization");
        String token = authorization.replace("Bearer ", "");
        String userCode = JwtUtils.resolveJwt(token).getUser_code();
        Integer myTeamId = personMapper.getTeamIdByUserCode(userCode);
        if(myTeamId.intValue()!=teamId.intValue()){
            throw new SystemException(Status.USER_ACCESS_NOT_ALLOWED);
        }


        int id = request.getId();
            captainService.deleteMember(id);
            return Result.success();
    }

    //修改成员信息
    @PatchMapping("/team/{teamId}/member")
    public Result teamMemberUpdate(@PathVariable Integer teamId, @RequestBody Member member){
        String authorization=httpServletRequest.getHeader("Authorization");
        String token = authorization.replace("Bearer ", "");
        String userCode = JwtUtils.resolveJwt(token).getUser_code();
        Integer myTeamId = personMapper.getTeamIdByUserCode(userCode);
        if(myTeamId.intValue()!=teamId.intValue()){
            throw new SystemException(Status.USER_ACCESS_NOT_ALLOWED);
        }


        member.setTeamId(teamId);
        member.setIsCaptain(0);
        captainService.editMember(member);
        return Result.success();
    }

    //获取成员列表
    @GetMapping("/team/{teamId}/member")
    public Result teamMemberGet(@PathVariable Integer teamId){
        String authorization=httpServletRequest.getHeader("Authorization");
        String token = authorization.replace("Bearer ", "");
        String userCode = JwtUtils.resolveJwt(token).getUser_code();
        Integer myTeamId = personMapper.getTeamIdByUserCode(userCode);
        if(myTeamId.intValue()!=teamId.intValue()){
            throw new SystemException(Status.USER_ACCESS_NOT_ALLOWED);
        }


        List<MemberInformation> memberList=new ArrayList<>();
        memberList=captainService.getMemberList(teamId);
        return Result.success(memberList);
    }

    //获取队伍信息
    @GetMapping("/team")
    public Result teamGet(){
        //String userCode = (JwtUtils.resolveJwt(httpServletRequest.getHeader("token"))).getUser_code();

        String authorization=httpServletRequest.getHeader("Authorization");
        String token = authorization.replace("Bearer ", "");
        String userCode = JwtUtils.resolveJwt(token).getUser_code();


        Integer teamId=personMapper.getTeamIdByUserCode(userCode);
        TeamModel teamModel=new TeamModel();
        teamModel=captainService.getTeamInformation(teamId);
        return Result.success(teamModel);
    }
}
