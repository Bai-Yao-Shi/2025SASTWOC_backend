package org.example.sastwoc;

import org.example.sastwoc.entity.Competition;
import org.example.sastwoc.entity.User;
import org.example.sastwoc.mapper.CompetitionMapper;
import org.example.sastwoc.utils.JwtUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class MybatisPlusTest {

    @Autowired
    private CompetitionMapper competitionMapper;

    @Test
    public void testInsert() {
        User user = new User();
        user=JwtUtils.resolveJwt("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VyQ29kZSI6IjQwMjIwIn0.u4sxMbrfp1Md85vQfj_6HRLvSP5EIDnW8lZ16-7zeAg");
        System.out.println(user);
    }
}
