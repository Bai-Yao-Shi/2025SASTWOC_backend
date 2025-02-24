package org.example.sastwoc.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("score")
public class ScoreRepository {
    @TableId(value = "id", type = IdType.AUTO)
    Integer id;
    String judgeId;
    String workCode;
    Integer teamId;
    Integer comId;
    double score;
    String comment;


}
