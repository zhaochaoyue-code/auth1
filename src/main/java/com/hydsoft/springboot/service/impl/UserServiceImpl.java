package com.hydsoft.springboot.service.impl;

import com.hydsoft.springboot.mapper.UserMapper;
import com.hydsoft.springboot.model.User;
import com.hydsoft.springboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class UserServiceImpl implements UserService {

@Autowired
private UserMapper userMapper;

@Override
public User getStudentById(Integer id) {

        return userMapper.selectByPrimaryKey(String.valueOf(id));
        }

@Transactional
@Override
public int updateStudent(User user) {
        int a = userMapper.updateByPrimaryKeySelective(user);
        int b =10/2;
        return a;
        }

        @Override
        public int updateStudentAndAge(User user) {
                return userMapper.updateByKeyAndStatus(user);
        }

        @Override
public int deleteStudent(String id) {
        return userMapper.deleteByPrimaryKey(String.valueOf(id));
        }

@Override
public User insertUser(User user) {
        userMapper.insert(user);
        return user;
        }

//    public int Update(User user){
//        return userMapper.Update(user);
//    }

//    public int delete(int id){
//        return userMapper.deleteByPrimaryKey(id);
//    }

        @Override
        public List<Integer> listIds() {
                return userMapper.listIds();
        }

        @Override
        public int batchUpdate(User user) {
                return userMapper.batchUpdate(user);
        }

        @Override
        public int batchDelete(User user) {
                return userMapper.batchdelete(user);
        }


}

