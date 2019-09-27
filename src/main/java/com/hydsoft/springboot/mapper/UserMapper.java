package com.hydsoft.springboot.mapper;

import com.hydsoft.springboot.model.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

//@Mapper
public interface UserMapper {
    int deleteByPrimaryKey(String id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);
    int updateByKeyAndStatus(User record);

    List<Integer> listIds();//获取所有的id 为一个list
    int batchUpdate(User record);
    int batchdelete(User record);

}