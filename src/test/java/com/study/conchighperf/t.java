package com.study.conchighperf;

import com.study.conchighperf.Service.SkuService;
import org.springframework.beans.factory.annotation.Autowired;

public class t implements Runnable {

    SkuService skuService;
    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            System.out.print("开始执行事务"+Thread.currentThread()+"  i:"+i);
            long a=System.currentTimeMillis();
            skuService.Order(343,331);
            System.out.print("执行完成"+(System.currentTimeMillis()-a)+"\n");
        }
    }

    t(SkuService skuService){
        this.skuService=skuService;

    }
}
