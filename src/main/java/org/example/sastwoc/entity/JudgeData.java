package org.example.sastwoc.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("works")
public class JudgeData {
    Integer id;
    String judgeId;
    String workCode;
    Integer teamId;
    Integer comId;
    Integer score;
    String comment;
}
