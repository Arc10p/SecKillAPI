package com.study.conchighperf.Mapper;

import com.study.conchighperf.POJO.SkuStock;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import org.apache.ibatis.annotations.Mapper;
@Mapper
public interface SkuStockMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SkuStock record);

    int insertSelective(SkuStock record);

    SkuStock selectByPrimaryKey(int id);

    int updateByPrimaryKeySelective(SkuStock record);

    int updateByPrimaryKey(SkuStock record);

    int findStockById(int id);
    void updateStock(int id,int stock);

}