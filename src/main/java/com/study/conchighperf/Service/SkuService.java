package com.study.conchighperf.Service;

import com.study.conchighperf.Mapper.SkuStockMapper;
import com.study.conchighperf.Mapper.SuceessOrderMapper;
import com.study.conchighperf.POJO.SuceessOrder;
import com.study.conchighperf.Service.redis.RedisService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;

@Service
@Slf4j
public class SkuService {


    @Autowired
    SkuStockMapper skuStockMapper;
    @Autowired
    RedisService redisService;

    @Autowired
    SuceessOrderMapper suceessOrderMapper;
    public int queryStock(int id){
    return skuStockMapper.findStockById(id);
    }

@Autowired
PlatformTransactionManager transactionManager;
    public void Order(int skuid,int userid){
        TransactionDefinition transactionDefinition=new DefaultTransactionDefinition();
        TransactionStatus status=transactionManager.getTransaction(transactionDefinition);
        try {

            //先查redis再查rdbms
            if (redisService.queryStock(skuid)==0)
                return;

            int stock=skuStockMapper.findStockById(skuid);
            if (stock==0)
                return;
            skuStockMapper.updateStock(skuid,stock-1);
            transactionManager.commit(status);
        }catch (DataAccessException e){
            System.out.println("Error in creating record, rolling back");
            transactionManager.rollback(status);
            throw e;
        }


    }
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public void lowPerfOrder(int skuid,int userid)  {
        int stock=skuStockMapper.findStockById(skuid);
        if (stock>0)
            skuStockMapper.updateStock(skuid,stock-1);
        if(stock!=skuStockMapper.findStockById(skuid)+1){
            log.warn("事务失败！！！！！"+stock);
        }else {
            suceessOrderMapper.insert(skuid,userid);
        }
    }

}
