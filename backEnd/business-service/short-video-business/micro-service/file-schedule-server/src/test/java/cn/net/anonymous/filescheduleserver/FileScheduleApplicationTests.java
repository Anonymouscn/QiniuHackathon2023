package cn.net.anonymous.filescheduleserver;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class FileScheduleApplicationTests {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    /** redis test */
    @Test
    void contextLoads() {
        redisTemplate.boundValueOps("test").set("Hello anonymous!");
        System.out.println(redisTemplate.boundValueOps("test").get());
    }
}