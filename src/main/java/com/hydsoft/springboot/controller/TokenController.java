package com.hydsoft.springboot.controller;

import com.hydsoft.springboot.model.Token;
import com.hydsoft.springboot.model.User;
import com.hydsoft.springboot.service.TokenService;
import com.hydsoft.springboot.service.UserService;
import net.sf.json.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import sun.nio.cs.US_ASCII;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.UUID;

@RestController
public class TokenController {

    @Autowired
    private TokenService tokenService;
    @Autowired
    private UserService userService;

    @RequestMapping(value = "/manyupdate")
    public  String userage(){
        User user = new User();
        user.setId("4");
        user.setAge(999);
        user.setUsername("改一下盖饭");
        int a =  userService.updateStudentAndAge(user);

        if(a>0){
            return "ok";
        }else {
            return "error";
        }
    }

    @RequestMapping(value = "/tokenupdate")
    public  String version(HttpServletResponse httpServletResponse, HttpServletRequest httpServletRequest){
        Token token = new Token();
        token.setId(4);
        token.setStatus("aaa");
        token.setVersion(2);
        token.setToken("a12345679qq");
        int a =  tokenService.updateTokenAndStatus(token);

        if(a>0){
            return "ok";
        }else {
            return "error";
        }
    }
    @RequestMapping(value = "/addall") //批量插入
    public String addBatch(){

        Token token=new Token();
        token.setStatus("新增");
        token.setToken(UUID.randomUUID().toString());
        int a =0;
        for(int i=0;i<5;i++ ){
            a = tokenService.insertToken(token);
        }
//        List<Integer> listIds=userService.listIds();
//        dto.setIds(listIds);
//        return userService.batchUpdate(dto);
    //(@RequestBody List<Department > departments
//        System.out.println(data);
//        String strlist = data;
//        List<Token> array = JSON.parseArray(strlist,Token.class);
//        int result = tokenService.addBatch(tokenList);

        if(a>0){
            return "ok";
        }else {
            return "error";
        }
//        return ResultMsg.getStrMsg(result > 0 ? "SUCCESS" : "FAILED");
//
//        return tokenService.addBatch(array);

    }
    @RequestMapping(value = "ids")
    public String ids(){
//        Object object =  tokenService.tokenIds();
//        return  object.toString();

        List<Integer> listIds=tokenService.tokenIds();
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer =null;
        for (int i= 0;i<listIds.size();i++){
            System.out.println(listIds.get(i));
        }
        return  stringBuffer.toString();
    }


}
