package com.study.conchighperf.RedisService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class UserInfoService {
    @Autowired
    RedisTemplate redisTemplate;
    public int queryUserIdBySession(String session){
        return  Integer.parseInt(
                redisTemplate.opsForValue().get("session:"+session).toString()
        );
    }
    public void saveSession(String session,int userid){

        redisTemplate.opsForValue().set("session:"+session,userid+"");

    }
}
