package com.study.conchighperf.Service.redis;

import com.study.conchighperf.Mapper.SkuStockMapper;
import com.study.conchighperf.POJO.SkuStock;
import com.study.conchighperf.Service.SkuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class RedisService {
    @Autowired
    RedisTemplate redisTemplate;
    @Autowired
    SkuService skuService;
    @Autowired
    SkuStockMapper skuStockMapper;
    public int queryStock(int id) {
        String key = "stock:" + id;

        if (!redisTemplate.hasKey(key))
            redisTemplate.opsForValue().set(key,skuService.queryStock(id)+"");

        return Integer.parseInt(redisTemplate.opsForValue().get(key).toString());
    }

}
