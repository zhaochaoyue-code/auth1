package com.hydsoft.springboot.service.impl;

import com.hydsoft.springboot.mapper.KeyMapper;
import com.hydsoft.springboot.model.Key;
import com.hydsoft.springboot.service.KeyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class KeyServiceImp implements KeyService {

    @Autowired
    private  KeyMapper keyMapper;

    @Override
    public Key getKeyByID(int id) {
        return keyMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateKey(Key key) {
        return keyMapper.updateByPrimaryKeySelective(key);
    }

    @Override
    public int deleteKey(int id) {
        return keyMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insertKey(Key key) {
        return keyMapper.insert(key);
    }
}
