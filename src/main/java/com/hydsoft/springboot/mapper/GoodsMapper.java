package com.hydsoft.springboot.mapper;

import com.hydsoft.springboot.model.Goods;

import java.util.List;

public interface GoodsMapper {

    int insert(Goods goods);//addBatchToken
    int batchinsert(List<Goods> goodsList);//批量插入
    List<Goods> batchSelect(List<Long> list);//批量查询
    int batchUpdate(List<Goods> list);//批量更新

    int batchDelete(List<Long> userInfoId);//批量删除
}
