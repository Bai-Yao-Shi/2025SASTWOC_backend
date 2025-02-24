package org.example.sastwoc.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@TableName("works")
@Data
public class Works {
    @TableId(value = "id", type = IdType.AUTO)
    Integer id;
    String name;
    Integer teamId;
    Integer comId;
    byte[] file;
}
