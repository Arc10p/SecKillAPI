package com.study.conchighperf;
import com.study.conchighperf.Service.redis.RedisService;
import com.study.conchighperf.Util.RandomUtil;
import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.utility.RandomString;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;

@SpringBootTest
@Slf4j
@Transactional
@Rollback(false)
public class PourData {
    @Autowired
    JdbcTemplate jdbcTemplate;
    //一下数据是在vmware下centos7 2g ram的docker的mysql镜像测试的数据 cpu intel 6c 4.9Ghz 机械硬盘+奥腾
//无优化用了49148ms插入4w数据
//使用事务以后35285ms插入4w数据
//使用合并语句（每35条插入就写入命令一次）+事务插入 2616ms插入4w  ****35条以后就开始变慢了所以定在35
//插入40w条用了25856ms
//插入500w条用了178109ms

    public void createUserData(){
        jdbcTemplate.execute("create table user(id int not null, " +
                "delete_flag  boolean not null default 0," +
                "name varchar(20)," +
                "telephone bigint," +
                "email varchar(256)," +
                "PRIMARY key (id));");
        StringBuffer sb=null;
        long start=System.currentTimeMillis();
        for (int i = 0; i <5000000 ; i++) {
            if (i%35==0){
                if (i>0)
                    jdbcTemplate.execute(sb.toString());
            sb=new StringBuffer("insert into user (id ,name,telephone,email) values (");
            sb.append(i);
            sb.append(",'"+ RandomUtil.getName()+"',"+RandomUtil.getTele()+",'"+RandomUtil.getEmail());
            sb.append("') ");
            }else {
                sb.append(",(");
                sb.append(i);
                sb.append(",'"+ RandomUtil.getName()+"',"+RandomUtil.getTele()+",'"+RandomUtil.getEmail());
                sb.append("')");
                if (i==4999999)
                    jdbcTemplate.execute(sb.toString());
            }

        }
        log.info("使用了："+(System.currentTimeMillis()-start)+"的时间来创建500w条用户数据");

    }
    public void createSku_stock(){
        jdbcTemplate.execute("create table sku_stock(id int not null, " +
                "stock  int not null default 0," +
                "max_stock int(20) not null default 100," +
                "PRIMARY key (id));");
        StringBuffer sb=null;
        long start=System.currentTimeMillis();
        for (int i = 0; i <100001 ; i++) {
            if (i%35==0){
                if (i>0)
                    jdbcTemplate.execute(sb.toString());
                sb=new StringBuffer("insert into sku_stock(id,stock,max_stock) values (");
                sb.append(i);
                sb.append(",50,100");
                sb.append(") ");
            }else {
                sb.append(",("+i);
                sb.append(",50,100");
                sb.append(") ");
                if (i==100000)
                    jdbcTemplate.execute(sb.toString());
            }

        }
        log.info("使用了："+(System.currentTimeMillis()-start)+"的时间来创建10w条商品库存");

    }
    @Test
    public void pourData(){
        log.info("begin pour");
        //createUserData();
        //createSku_stock();
    }
}
