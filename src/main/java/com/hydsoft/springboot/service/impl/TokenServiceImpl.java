package com.hydsoft.springboot.service.impl;


import com.hydsoft.springboot.mapper.TokenMapper;
import com.hydsoft.springboot.model.Token;
import com.hydsoft.springboot.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TokenServiceImpl implements TokenService {

    @Autowired
    private TokenMapper tokenMapper;

    @Override
    public Token getTokenByID(int id) {
        return tokenMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateToken(Token token) {
        return tokenMapper.updateByPrimaryKeySelective(token);
    }

    @Override
    public int updateTokenAndStatus(Token token) {
        return tokenMapper.updateByTokenAndStatus(token);
    }

    @Override
    public int deleteToken(int id) {
        return tokenMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insertToken(Token token) {
     return tokenMapper.insert(token);
    }

    @Override
    public int addBatch(Token token) {
        return tokenMapper.addBatchToken(token);
    }

    @Override
    public List<Integer> tokenIds() {
        return tokenMapper.tokenIds();
    }

   /* @Override
    public int addBatch(List<Token> token) {

//        return tokenMapper.addBatchToken(token);
        try{
//            return departmentMapper.insertDepartments(departments);
            return tokenMapper.addBatchToken(token);
        }catch (Exception e )
        {
            System.out.println("execption");
            return -1;
        }
    }*/


}
