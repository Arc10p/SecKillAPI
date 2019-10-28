package com.study.conchighperf;

import com.study.conchighperf.Mapper.SuceessOrderMapper;
import com.study.conchighperf.RedisService.CacheSkuService;
import com.study.conchighperf.Service.SkuService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

@SpringBootTest
public class TempTest {
    @Autowired
    SkuService skuService;
    @Autowired
    SuceessOrderMapper suceessOrderMapper;
    @Autowired
    CacheSkuService cacheSkuService;
    @Autowired
    RedisTemplate redisTemplate;
    @Test
    public void o(){
        System.out.println(redisTemplate.opsForValue().get("hello"));
        ;

    }
}
