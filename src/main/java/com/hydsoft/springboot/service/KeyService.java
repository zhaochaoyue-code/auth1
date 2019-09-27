package com.hydsoft.springboot.service;

import com.hydsoft.springboot.model.Key;

public interface KeyService {

    public Key getKeyByID(int id);
    public int updateKey(Key key);
    public  int deleteKey(int id);
    public int insertKey(Key key);
}
