package org.example.sastwoc;

import org.example.sastwoc.entity.Competition;
import org.example.sastwoc.mapper.CompetitionMapper;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)  // 告诉 JUnit 使用 Spring Runner
@SpringBootTest  // 启动 Spring Boot 上下文
public class GetTest {

    @Autowired
    private CompetitionMapper competitionMapper;

    @Test
    public void test() {
        List<Competition> competitions = competitionMapper.selectList(null);
        for (Competition competition : competitions) {
            System.out.println(competition);
        }
    }
}
