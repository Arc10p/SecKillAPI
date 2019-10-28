package com.study.conchighperf.MQ;

import com.google.gson.Gson;
import com.study.conchighperf.MQ.MQPOJO.OrderAction;
import com.study.conchighperf.Mapper.SkuStockMapper;
import com.study.conchighperf.Mapper.SuceessOrderMapper;
import com.study.conchighperf.POJO.SuceessOrder;
import com.study.conchighperf.RedisService.CacheSkuService;
import com.study.conchighperf.Service.SkuService;
import com.study.conchighperf.Service.redis.RedisService;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.units.qual.A;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicInteger;

@Component
@Slf4j
public class Consumer {
    private static Gson gson=new Gson();

    @Autowired
    SuceessOrderMapper suceessOrderMapper;
    @Autowired
    SkuStockMapper skuStockMapper;
    @Autowired
    CacheSkuService cacheSkuService;
    @Autowired
    RedisService redisService;
    @Autowired
    SkuService skuService;
    @RabbitListener(queues = "queue")
    public void consume(String msg){
        //先检测redis库存 redis有库存则才打进mysql


        OrderAction orderAction=gson.fromJson(msg, OrderAction.class);
        if (redisService.queryStock(orderAction.getSkuid())>0)
        {
        skuService.lowPerfOrder(orderAction.getSkuid(),orderAction.getUserid());
        cacheSkuService.minusSku(orderAction.getSkuid());
        }

        else {
            log.warn("订单失败 无库存了");
            //失败订单 ……后续添加处理机制
        }
    }

}
