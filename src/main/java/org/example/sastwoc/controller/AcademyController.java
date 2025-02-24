package org.example.sastwoc.controller;

import org.example.sastwoc.DTO.JudgeCreateDto;
import org.example.sastwoc.DTO.JudgeDeleteDto;
import org.example.sastwoc.entity.Person;
import org.example.sastwoc.entity.Team;
import org.example.sastwoc.exception.SystemException;
import org.example.sastwoc.service.AdminService;
import org.example.sastwoc.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import java.util.List;




@RestController
@RequestMapping("/academy")

public class AcademyController {
    @Autowired
    private AdminService adminService;
    @Autowired
    private HttpServletRequest httpServletRequest;


    @PostMapping("/captain")
    public Result captainCreate(@RequestBody Person person) {
        try{
            adminService.createCaptain(person);
            return Result.success();
        }catch (SystemException e){
            return Result.fail(e.getCode(),e.getMessage());
        }


    }

    @GetMapping("/team")
    public Result teamGet(@RequestParam int comId) {
        List<Team> teams=adminService.getTeams(comId);
        return Result.success(teams);
    }

    @PostMapping("/judge")
    public Result judgeCreate(@RequestBody JudgeCreateDto judge) {
        adminService.createJudge(judge);
        return Result.success();
    }

    @DeleteMapping("/judge")
    public Result judgeDelete(@RequestBody JudgeDeleteDto judge) {
        adminService.deleteJudge(judge);
        return Result.success();
    }

    @GetMapping("/judge")
    public Result judgeGet(@RequestParam int comId) {
        return Result.success(adminService.getJudgeList(comId));
    }
}


