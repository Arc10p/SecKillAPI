package com.study.conchighperf.RedisService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class CacheSkuService {
    @Autowired
    RedisTemplate redisTemplate;

    public boolean minusSku(int skuid){
        if (Integer.parseInt(redisTemplate.opsForValue().get("stock:"+skuid).toString())>0)
        {
            redisTemplate.opsForValue().decrement("stock:"+skuid);
            return true;

        }
        return false;
    }
    public int SkuStock(int skuid){
        return Integer.parseInt(redisTemplate.opsForValue().get("stock:"+skuid).toString());

    }
    public void setStock(int skuStock ,int skuid){
         redisTemplate.opsForValue().set("stock:"+skuid,skuStock+"");
    }

}
