package com.study.conchighperf;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class ConchighperfApplication implements ApplicationRunner {

    public static void main(String[] args) {
        SpringApplication.run(ConchighperfApplication.class, args);
    }
    @Autowired
    JdbcTemplate jdbcTemplate;
    @Autowired
    ApplicationContext applicationContext;
    @Autowired
    RedisTemplate redisTemplate;
    @Override
    public void run(ApplicationArguments args) throws Exception {
        jdbcTemplate.execute("truncate suceess_order;");
        //仅开发使用 不要在redis用keys命令
        //随便插一个数据防止exception
//        redisTemplate.opsForValue().set("stock:24792384823974239",0);
//        redisTemplate.delete(redisTemplate.keys("stock:*"));
    }
}
