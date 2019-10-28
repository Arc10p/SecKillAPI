package com.study.conchighperf.Mapper;

import com.study.conchighperf.POJO.SuceessOrder;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SuceessOrderMapper {
    int deleteByPrimaryKey(Integer orderid);

    int insert(int goodid,int userid);

    int insertSelective(SuceessOrder record);

    SuceessOrder selectByPrimaryKey(Integer orderid);

    int updateByPrimaryKeySelective(SuceessOrder record);

    int updateByPrimaryKey(SuceessOrder record);

}