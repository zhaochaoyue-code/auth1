package com.hydsoft.springboot.service;


import com.hydsoft.springboot.model.Token;

import java.util.List;

public interface TokenService {


    public Token getTokenByID(int id);
//    public User getStudentById(Integer id);
    public int updateToken(Token token);

    public int updateTokenAndStatus(Token token);
//    public int updateStudent(User user);
    public  int deleteToken(int id);
//    public int deleteStudent(String id);

//    public User insertUser(User user);

    public int insertToken(Token token);
    public int addBatch(Token token);

    public List<Integer> tokenIds();
}
