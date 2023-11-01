package cn.net.anonymous.fileworkflowserver;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.redisson.api.RAtomicLong;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import util.RedisUtil;

import java.util.concurrent.CountDownLatch;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class FileWorkflowServerApplicationTests {

//    @Autowired
//    private RedisUtil redisUtil;

    @Autowired
    private RedissonClient redissonClient;

    @Test
    void contextLoads() throws InterruptedException {
        RAtomicLong count = redissonClient.getAtomicLong("test");
        CountDownLatch latch = new CountDownLatch(4);
        count.delete();
        for(int s = 0; s < 4; ++s) {
            new Thread(() -> {
                for(int i = 0 ; i < 5000; ++i) {
                    if(count.incrementAndGet() == 20000) System.out.println("任务完成");
                }
                latch.countDown();
            }).start();
        }
        latch.await();
        System.out.println(count.get());
    }
}