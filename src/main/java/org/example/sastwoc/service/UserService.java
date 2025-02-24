package org.example.sastwoc.service;

import org.example.sastwoc.DTO.LoginUserDto;
import org.example.sastwoc.DTO.RegisterUserDto;
import org.example.sastwoc.entity.Person;
import org.example.sastwoc.entity.User;

public interface UserService {
    void register(RegisterUserDto user);

    String login(LoginUserDto dto);

    //添加或更新个人信息
    void addOrUpdateUser(Person person);

}
