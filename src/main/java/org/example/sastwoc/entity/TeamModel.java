package org.example.sastwoc.entity;

import lombok.Data;

@Data

public class TeamModel {
    Integer id;
    Integer comId;
    String name;
    Integer captainId;
    String captainName;
    Integer status;
    String memberNames;
    String instructorNames;
}
