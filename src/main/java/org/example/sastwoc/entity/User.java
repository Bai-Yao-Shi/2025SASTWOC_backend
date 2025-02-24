package org.example.sastwoc.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.UUID;

@TableName("user")

@Data
public class User {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private String user_code;
    private String password;
    private String salt= UUID.randomUUID().toString().replaceAll("-", "");
}
