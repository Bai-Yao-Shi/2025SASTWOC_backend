package org.example.sastwoc.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Update;
import org.example.sastwoc.entity.User;

public interface UserMapper extends BaseMapper<User> {
    @Update("ALTER TABLE user AUTO_INCREMENT = 1")
    void resetAutoIncrement();
}
