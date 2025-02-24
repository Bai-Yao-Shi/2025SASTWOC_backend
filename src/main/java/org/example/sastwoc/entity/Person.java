package org.example.sastwoc.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@TableName("person")
@Data
public class Person {
    @TableId(value = "id", type = IdType.AUTO)
    Integer id;
    Integer comId;
    String name;
    Integer captainId;
    Integer isCaptain;
    Integer isSecondaryAdmin;
    Integer isPrimaryAdmin;
    Integer isJudge;
    Integer teamId;
    Integer academyId;
    //String teamName;
    String studentId;
    String phone;
    //String judgeId;
    String userCode;
    Integer status;







}

