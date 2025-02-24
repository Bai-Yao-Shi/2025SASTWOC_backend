package org.example.sastwoc.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
import org.example.sastwoc.entity.Person;

import java.util.List;

@Mapper
public interface PersonMapper extends BaseMapper<Person> {
    //空表自增归1
    @Update("ALTER TABLE person AUTO_INCREMENT = 1")
    void resetAutoIncrement();

    //检测captainId对应的isCaptain
    Integer testCaptain(Integer captainId);

    //根据captainId获取teamId
    Integer getTeamIdByCaptainId(@Param("captainId") Integer captainId);

    //根据teamId获取membersId并存入列表;
    List<Integer> getMembersIdByTeamId(Integer teamId);

    //寻找teamId中isInstructor=1
    List<Integer> getInstructorsIdByTeamId(Integer teamId);

    List<String> getInstructorsNameByTeamId(Integer teamId);
    //更新队长名称

    //根据memberId设置teamId和comId
    void updateTeamIdById(@Param("memberId") Integer memberId, @Param("teamId") Integer teamId);

    void updateComIdById(@Param("memberId") Integer memberId, @Param("comId") Integer comId);

    //更改isInstructor
    void setIsInstructorTrue(@Param("memberId") Integer memberId);

    void setIsInstructorFalse(@Param("memberId") Integer memberId);

    //查看队伍人数
    Integer countMembersByTeamId(@Param("teamId") Integer teamId);

    //更改isCaptain
    void setIsCaptainTrue(@Param("memberId") Integer memberId);

    void setIsCaptainFalse(@Param("memberId") Integer memberId);

    void setCaptainIdById(@Param("memberId") Integer memberId, @Param("captainId") Integer captainId);

    Integer getTeamIdById(@Param("id") Integer id);

    List<Integer> getMembersIdByTeamIdExceptCaptain(@Param("teamId") Integer teamId);

    List<String> getMembersNameByTeamIdExceptCaptain(@Param("teamId") Integer teamId);

    //清除teamId
    void setNULLTeamIdByIdExceptCaptain(@Param("id") Integer id);

    //清除comId
    void setNULLComIdByIdExceptCaptain(@Param("id") Integer id);

    void setNULLIsInstructorByIdExceptCaptain(@Param("id") Integer id);

    void setNULLIsCaptainByIdExceptCaptain(@Param("id") Integer id);

    void setNULLCaptainIdByIdExceptCaptain(@Param("id") Integer id);

    List<Integer> selectMembersIdByTeamId(@Param("teamId") Integer teamId);

    List<String> getMembersNameByTeamId(Integer teamId);

    //根据comId获取去重的teamId
    List<Integer> selectTeamIdByComId(int comId);

    String getTeamNameByTeamId(@Param("teamId") Integer teamId);

    Integer getCaptainIdByTeamId(@Param("teamId") Integer teamId);

    String getCaptainNameByTeamId(@Param("teamId") Integer teamId);

    Integer getComIdByCaptainId(@Param("captainId") Integer captainId);

    String getAcademyNameById(@Param("id") Integer id);

    Integer getIsCaptainByUserCode(@Param("userCode") String userCode);

    Integer getIsPrimaryAdminByUserCode(@Param("userCode") String userCode);

    Integer getIsSecondaryAdminByUserCode(@Param("userCode") String userCode);

    Integer getIsJudgeByUserCode(@Param("userCode") String userCode);

    Integer getIsInstructorByUserCode(@Param("userCode") String userCode);

    Integer getTeamIdByUserCode(@Param("userCode") String userCode);

    void setJudgeByUserCode(@Param("userCode") String userCode);
    void setComIdByUserCode(@Param("userCode") String userCode,@Param("comId")Integer comId);
    void deleteJudgeByUserCode(@Param("userCode") String userCode);
    void deleteComIdByUserCode(@Param("userCode") String userCode);

    void createSecondaryAdminByUserCode(@Param("userCode") String userCode,@Param("academyId")Integer academyId,@Param("comId") Integer comId);
    void deleteSecondaryAdminByUserCode(@Param("userCode") String userCode);

    List<String> getJudgeNameByComId(@Param("comId") Integer comId);
    List<String> getJudgeStudentIdByComId(@Param("comId") Integer comId);
    List<String> getJudgePhoneByComId(@Param("comId") Integer comId);

    Integer getCaptainIdByUserCode(@Param("userCode") String userCode);

    Integer getComIdByTeamId(@Param("teamId") Integer teamId);

    String getAcademyIdById(@Param("id") Integer teamId);

    Integer getStatusByTeamId(@Param("teamId") Integer teamId);

    List<String> getStudentIdByTeamId(@Param("teamId") Integer teamId);
    List<String> getInstructorStudentIdByTeamId(@Param("teamId") Integer teamId);

    void setTeamNameById(@Param("teamName") String teamName, @Param("id") Integer id);




}
