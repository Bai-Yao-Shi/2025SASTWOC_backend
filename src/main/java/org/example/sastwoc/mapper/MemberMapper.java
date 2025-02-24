package org.example.sastwoc.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;
import org.example.sastwoc.entity.Member;

@Mapper
public interface MemberMapper extends BaseMapper <Member>{
        //空表自增归1
     @Update("ALTER TABLE member AUTO_INCREMENT = 1")
     void resetAutoIncrement();


}
