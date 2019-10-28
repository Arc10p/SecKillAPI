package com.study.conchighperf.Controller;

import com.google.common.util.concurrent.RateLimiter;
import com.study.conchighperf.MQ.MQPOJO.OrderAction;
import com.study.conchighperf.MQ.Producer;
import com.study.conchighperf.POJO.Information;
import com.study.conchighperf.RedisService.UserInfoService;
import com.study.conchighperf.Service.SkuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("stress")
public class StressController {

    RateLimiter rateLimiter=RateLimiter.create(100);
    @Autowired
    Producer producer;
    @Autowired
    SkuService skuService;
    @Autowired
    UserInfoService userInfoService;
    @RequestMapping("lowPerf")
    public Information lowPerf(@RequestParam(value = "merchandiseID") String  id,
                             @RequestParam(value = "token")String token,
                             @RequestParam(value = "teleNum")String teleNum,
                             HttpServletRequest httpServletRequest
    ){

        skuService.lowPerfOrder(Integer.parseInt(id),Integer.parseInt(token));
        return new Information();
    }

    //拒绝策略
    @RequestMapping("placeOrder")
    public Information placeOrder(@RequestParam(value = "merchandiseID") String  id,
                                  @RequestParam(value = "token")String token,
                                  @RequestParam(value = "teleNum")String teleNum,
                                  HttpServletRequest httpServletRequest

    ){


        if(rateLimiter.tryAcquire(0, TimeUnit.MICROSECONDS)){

        int userid=userInfoService.
                queryUserIdBySession(httpServletRequest.getRequestedSessionId());
        producer.send(
                OrderAction.builder().
                        skuid(Integer.parseInt(id)).userid(userid).build()
        );
            return new Information().setInfo("success");
        }else {
            return new Information().setInfo("被限流了");
        }

    }



}
