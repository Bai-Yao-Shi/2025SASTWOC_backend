package org.example.sastwoc.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
import org.example.sastwoc.entity.Works;

@Mapper
public interface WorksMapper extends BaseMapper<Works> {
    @Update("ALTER TABLE works AUTO_INCREMENT = 1")
    void resetAutoIncrement();
}
