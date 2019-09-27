package com.hydsoft.springboot.service;

import com.hydsoft.springboot.model.Goods;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface GoodsSrevice {

    int add(Goods goods);//单个



    int batchInsert(@Param("list") List<Goods> list);//批量插入
    List<Goods> batchSelect(List<Long> list);//按ID批量查询//按ID查询
    int batchUpdate(List<Goods> list);//批量更新
    int batchDelete(List<Long> userInfoId);
}
