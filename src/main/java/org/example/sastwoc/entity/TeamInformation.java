package org.example.sastwoc.entity;

import lombok.Data;

import java.util.List;

@Data
public class TeamInformation {
    Integer id;
    Integer comId;
    String name;
    Integer captainId;
    String captainName;
    Integer status;
    String memberNames;
    String instructorNames;
    List<Integer> memberIds;
    List<Integer> instructorIds;
    String createTime;

}
