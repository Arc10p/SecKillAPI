package com.study.conchighperf;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
@SpringBootTest
@Slf4j
public class RedisTest {
    @Autowired
    RedisTemplate redisTemplate;
    @Test
    public void connect(){
        redisTemplate.opsForValue().set("stock:24792384823974239","0");
        redisTemplate.delete(redisTemplate.keys("stock:*"));
    }
}
