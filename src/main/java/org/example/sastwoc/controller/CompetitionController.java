package org.example.sastwoc.controller;

import org.example.sastwoc.entity.Competition;
import org.example.sastwoc.exception.SystemException;
import org.example.sastwoc.mapper.PersonMapper;
import org.example.sastwoc.service.AdminService;
import org.example.sastwoc.service.CaptainService;
import org.example.sastwoc.utils.JwtUtils;
import org.example.sastwoc.vo.Result;
import org.example.sastwoc.vo.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/competitions")
public class CompetitionController {
    @Autowired
    private AdminService adminService;
    @Autowired
    private CaptainService captainService;
    @Autowired
    private PersonMapper personMapper;

@GetMapping
public Result competitionGet(HttpServletRequest request) {
    String userCode = JwtUtils.resolveJwt(request.getHeader("token")).getUser_code();

    // 检查是否为管理员
    Integer isPrimaryAdmin = personMapper.getIsPrimaryAdminByUserCode(userCode);
    Integer isSecondaryAdmin = personMapper.getIsSecondaryAdminByUserCode(userCode);

    if ((isPrimaryAdmin != null && isPrimaryAdmin == 1) || (isSecondaryAdmin != null && isSecondaryAdmin == 1)) {
        List<Competition> competitions = adminService.getCompetitions();
        return Result.success(competitions);
    }

    // 检查是否为队长
    Integer isCaptain = personMapper.getIsCaptainByUserCode(userCode);
    if (isCaptain != null && isCaptain == 1) {
        Integer captainId = personMapper.getCaptainIdByUserCode(userCode);
        if (captainId != null) {
            List<Competition> captainCompetition = captainService.getCompetitions(captainId);
            return Result.success(captainCompetition);
        }
    }

    return Result.success();
}


}
