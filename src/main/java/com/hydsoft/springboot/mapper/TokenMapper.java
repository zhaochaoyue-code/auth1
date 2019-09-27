package com.hydsoft.springboot.mapper;

import ch.qos.logback.core.util.SystemInfo;
import com.hydsoft.springboot.model.Token;

import java.util.List;

public interface TokenMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Token record);

    int insertSelective(Token record);

    Token selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Token record);

    int updateByPrimaryKey(Token record);
    int updateByTokenAndStatus(Token record);

//    int addBatchToken(List<Token> list);
//    int addBatchToken(List<Token> tokenList);

    int addBatchToken(Token record);

    List<Integer> tokenIds();//


}