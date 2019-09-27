package com.hydsoft.springboot.service;

import com.hydsoft.springboot.model.User;

import java.util.List;

/**
 * ClassName:StudentService
 * <p>
 * Package:com.wkcto.springboot.service
 * Description:
 *
 * @Date:2018/8/7 12:20
 * @Author:蛙课网
 */
public interface UserService {

    public User getStudentById(Integer id);
    public int updateStudent(User user);
    public int updateStudentAndAge(User user);
    public int deleteStudent(String id);
    public User insertUser(User user);

    public List<Integer> listIds();
    public  int batchUpdate(User user);
    public  int batchDelete(User user);



}
