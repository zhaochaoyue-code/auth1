package com.hydsoft.springboot.service.impl;

import com.hydsoft.springboot.mapper.GoodsMapper;
import com.hydsoft.springboot.model.Goods;
import com.hydsoft.springboot.service.GoodsSrevice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GoodsServiceImpl implements GoodsSrevice {

    @Autowired
    private GoodsMapper goodsMapper;


    @Override
    public int add(Goods goods) {
        return 0;
    }

    @Override
    public int batchInsert(List<Goods> list) {
        return goodsMapper.batchinsert(list);
    }

    @Override
    public List<Goods> batchSelect(List<Long> list) {
        return goodsMapper.batchSelect(list);
    }

    @Override
    public int batchUpdate(List<Goods> list) {
        return goodsMapper.batchUpdate(list);
    }

    @Override
    public int batchDelete(List<Long> list) {
        return goodsMapper.batchDelete(list);
    }
}
