package org.example.sastwoc.service.Impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.example.sastwoc.DTO.LoginUserDto;
import org.example.sastwoc.DTO.RegisterUserDto;
import org.example.sastwoc.entity.Person;
import org.example.sastwoc.entity.User;
import org.example.sastwoc.entity.Works;
import org.example.sastwoc.exception.SystemException;
import org.example.sastwoc.mapper.PersonMapper;
import org.example.sastwoc.mapper.UserMapper;
import org.example.sastwoc.service.UserService;
import org.example.sastwoc.utils.JwtUtils;
import org.example.sastwoc.vo.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.Map;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
   @Autowired
   private UserMapper userMapper;
   @Autowired
   private PersonMapper personMapper;

   @Override
   public void register(RegisterUserDto dto) {
      //创建User对象
      User user = new User();

      //加密密码
      String password = dto.getPassword();
      String salt = user.getSalt();
      String md5Password = DigestUtils.md5DigestAsHex((password + salt).getBytes());


      QueryWrapper<User> queryWrapper = new QueryWrapper<>();
      queryWrapper.eq("user_code", dto.getUserCode());
      User existingPerson=userMapper.selectOne(queryWrapper);
      user.setUser_code(dto.getUserCode());
      user.setPassword(md5Password);
      user.setSalt(salt);
      if(existingPerson!=null){
            throw new SystemException(Status.USER_EXIST);
      }else{
          userMapper.insert(user);
      }



   }

@Override
public String login(LoginUserDto dto) {
    // 查询用户

    User user = userMapper.selectOne(Wrappers.<User>query().eq("user_code", dto.getUserCode()));

    // 用户不存在，返回 null
    if (user == null) {
        throw new SystemException(Status.USER_NOT_EXIST);
    }

    user.setUser_code(dto.getUserCode());

    // 计算 MD5 加密后的密码
    String md5Password = DigestUtils.md5DigestAsHex((dto.getPassword() + user.getSalt()).getBytes());

    // 验证密码
    if (!md5Password.equals(user.getPassword())) {
        return null;
    }


    // 生成并返回 JWT Token
    return JwtUtils.createJwt(user);
}

    @Override
    public void addOrUpdateUser(Person person) {

        //查询userCode如果存在则更新，不存在则插入
        QueryWrapper<Person> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_code", person.getUserCode());
        Person existingPerson=personMapper.selectOne(queryWrapper);

        int count = Math.toIntExact(userMapper.selectCount(null));
        if (count == 0) {
            // 表为空，重置自增值为 1
            userMapper.resetAutoIncrement();
        }



        if (existingPerson != null) {
            existingPerson.setName(person.getName());
            existingPerson.setStudentId(person.getStudentId());
            existingPerson.setAcademyId(person.getAcademyId());
            existingPerson.setPhone(person.getPhone());
            personMapper.updateById(existingPerson);
        }else {
            personMapper.insert(person);
        }

    }


}