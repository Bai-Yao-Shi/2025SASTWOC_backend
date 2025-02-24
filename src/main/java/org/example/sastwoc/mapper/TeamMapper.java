package org.example.sastwoc.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.example.sastwoc.entity.Team;

import java.util.List;

@Mapper
public interface TeamMapper extends BaseMapper<Team> {

}
