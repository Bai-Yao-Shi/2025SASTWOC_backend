package org.example.sastwoc.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("person")
public class Member {
    private Integer id;
    private String name;
    private String studentId;
    private Integer teamId;
    private Integer academyId;
    private String phone;
    private Integer isCaptain;
    private Integer comId;
}
