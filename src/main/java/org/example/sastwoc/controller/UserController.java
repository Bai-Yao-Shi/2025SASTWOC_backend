package org.example.sastwoc.controller;

import org.apache.tomcat.util.http.parser.Authorization;
import org.example.sastwoc.DTO.LoginUserDto;
import org.example.sastwoc.DTO.RegisterUserDto;
import org.example.sastwoc.entity.Person;
import org.example.sastwoc.entity.Token;
import org.example.sastwoc.entity.User;
import org.example.sastwoc.mapper.PersonMapper;
import org.example.sastwoc.service.UserService;
import org.example.sastwoc.utils.JwtUtils;
import org.example.sastwoc.vo.Result;
import org.example.sastwoc.vo.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@RestController
@ResponseBody
@RequestMapping("")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private PersonMapper personMapper;
    @Autowired
    private HttpServletRequest httpServletRequest;

    @PostMapping("/register")
    public Result register(@RequestBody RegisterUserDto dto) {
        userService.register(dto);
        return Result.success();
    }

    @PostMapping("/login")
    public Result login(@RequestBody LoginUserDto dto) {

        Token token=new Token();
        token.setToken(userService.login(dto));
                return Optional.ofNullable(userService.login(dto))
               .map(user -> Result.success(token))
               .orElse(Result.fail(Status.FAIL.getCode(), "用户名或密码错误！"));
    }

    @PostMapping("/user/userdetail")
    public Result userdetail(@RequestBody Person person) {
        String authorization=httpServletRequest.getHeader("Authorization");
        String token = authorization.replace("Bearer ", "");
        String userCode = JwtUtils.resolveJwt(token).getUser_code();
        person.setUserCode(userCode);

        userService.addOrUpdateUser(person);
        return Result.success();
    }
}