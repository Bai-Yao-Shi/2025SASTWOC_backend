package org.example.sastwoc.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;
import org.example.sastwoc.entity.Competition;
@Mapper

public interface CompetitionMapper extends BaseMapper<Competition> {
    //空表自增归1
     @Update("ALTER TABLE competition AUTO_INCREMENT = 1")
     void resetAutoIncrement();

     //根据captain_id查找com_id
}
