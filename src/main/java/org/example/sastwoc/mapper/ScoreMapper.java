package org.example.sastwoc.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Update;
import org.example.sastwoc.entity.ScoreRepository;

public interface ScoreMapper extends BaseMapper<ScoreRepository> {
    @Update("ALTER TABLE score AUTO_INCREMENT = 1")
    void resetAutoIncrement();
}
