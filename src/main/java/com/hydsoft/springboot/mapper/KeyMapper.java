package com.hydsoft.springboot.mapper;

import com.hydsoft.springboot.model.Key;

public interface KeyMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Key record);

    int insertSelective(Key record);

    Key selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Key record);

    int updateByPrimaryKey(Key record);
}