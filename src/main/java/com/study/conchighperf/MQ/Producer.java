package com.study.conchighperf.MQ;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.study.conchighperf.MQ.MQPOJO.OrderAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Producer {
    static Gson gson=new Gson();
    @Autowired
    ProducerTemplate producerTemplate;
            public void send(OrderAction orderAction){

                producerTemplate.Send( gson.toJson(orderAction).getBytes());
            }
}
