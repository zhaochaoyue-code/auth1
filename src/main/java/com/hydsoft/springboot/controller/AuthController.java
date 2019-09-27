package com.hydsoft.springboot.controller;

import com.hydsoft.springboot.model.Key;
import com.hydsoft.springboot.model.Token;
import com.hydsoft.springboot.model.User;
import com.hydsoft.springboot.service.KeyService;
import com.hydsoft.springboot.service.TokenService;
import com.hydsoft.springboot.service.UserService;
import com.hydsoft.springboot.util.EncryptionUtil;
import com.hydsoft.springboot.util.HttpClientResult;
import com.hydsoft.springboot.util.HttpClientUtils;
import net.sf.json.JSONObject;
import org.apache.log4j.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
public class AuthController {
    private static Category logger = Category.getInstance(AuthController.class);
@Autowired
private UserService userService;
@Autowired
private TokenService tokenService;

@Autowired
private KeyService keyService;

    @RequestMapping(value="/doauth")
    public Object token(int id,int carid, String time,String status,int type) throws Exception {//@RequestParam int id

            String date ="yyyyMMddHHmmssSSS";
            String url ="";
            String jsonParams ="";
        SimpleDateFormat sdf = new SimpleDateFormat(date);
        String requestime =  sdf.format(new Date());
        Date afterDate = new Date(new Date().getTime() + 3600000);
        String validate =sdf.format(afterDate);
        Date sysDate = new Date(new Date().getTime());
        String sysdate =sdf.format(sysDate);
        Date beforeDate = new Date(new Date().getTime()+600000);
        String beforedate =sdf.format(beforeDate);

        Token token = tokenService.getTokenByID(id);
        if(token ==null){
            return "没查到id是"+id+"  carid="+carid +"   的信息";
        }
        status = token.getStatus();
//        String validate = token.getTimestamp();
        logger.info(token);
        Token tok = new Token();
        Key key = new Key();
//        return  token;
//        Token token =null;
        if(type==1) { //请求token
                        User user =userService.getStudentById(1);
            return user;
//          String result =   handleToken(token,id,carid,time,status,type,requestime);

        }else if(type==2){//请求key
            String result =  handlekey(key,id,carid,time,status,type,requestime);

        }else{//token 和 key

            System.out.println("token 和 key");
            String resul1 =  handleToken(token,id,carid,time,status,type,requestime);
            String resul2 = handlekey(key,id,carid,time,status,type,requestime);

    }

        /*}else{
            //从token表中取得这个token的有效期和状态
            String validate = token.getTimestamp();
            String status = token.getStatus();
            System.out.println("有效期"+validate+"状态为"+status);
            //判断是否快要过期
            return tokenService.updateToken(token);

        }*/
//        Object obj = updateUser1();
//        return  obj;
//        return userService.getStudentById(2);
        return "result";
    }

    private String handleToken(Token token, int id, int carid, String time, String status, int type, String requestime) throws ParseException {
        if (token == null) {

            token.setCarid(id);
            token.setCarid(carid);
            token.setStatus("初始");
            token.setTimestamp(requestime);
            token.setToken(UUID.randomUUID().toString());
            int result = tokenService.insertToken(token);
            //处理完成调用V2X系统接口 SENDSERVER_URL 接口地址  jsonParams 请求参数
//            String a = HttpClientUtil.postJosnContent(url, jsonParams);


            if (result >= 1) {
                return "同步信息成功";
            } else {
                return "同步信息失败";
            }


        } else if (status.equals("处理完成") && checktime(requestime) < 0) {
            int result = tokenService.deleteToken(id);

            //处理完成调用V2X系统接口 SENDSERVER_URL 接口地址  jsonParams 请求参数
//            String a = HttpClientUtil.postJosnContent(url, jsonParams);
            if (result >= 1) {
                return "删除成功";
            } else {
                return "删除失败";
            }
        } else {
            token.setStatus("处理完成");
            token.setTimestamp(requestime);
            token.setId(id);
            String prodtoken = UUID.randomUUID().toString();
            token.setToken(prodtoken);
            tokenService.updateToken(token);

            //处理完成调用V2X系统接口 SENDSERVER_URL 接口地址  jsonParams 请求参数
//                String a = HttpClientUtil.postJosnContent(url, jsonParams);


            return "不为空,更新信息完成";
        }
    }

    private String handlekey(Key key,int id,int carid, String time,String status,int type,String requestime) throws Exception {
        if (key == null) {

            key.setCarid(id);
            key.setCarid(carid);
            key.setStatus("初始");
            key.setTimestamp(requestime);
            key.setKey(UUID.randomUUID().toString());
            int result =keyService.insertKey(key);
            //处理完成调用V2X系统接口 SENDSERVER_URL 接口地址  jsonParams 请求参数
            String  returnkey  = forwordv2x(key);
            //更新token表或者key表状态为成功返回
            //调用更新
            keyService.deleteKey(id);
//                String a = HttpClientUtil.postJosnContent(url, jsonParams);
            if (result >= 1) {
                return "同步信息成功";
            } else {
                return "同步信息失败";
            }


        } else if (status.equals("处理完成") && checktime(requestime) < 0) {
            int result = keyService.deleteKey(id);

            //处理完成调用V2X系统接口 SENDSERVER_URL 接口地址  jsonParams 请求参数
//                String a = HttpClientUtil.postJosnContent(url, jsonParams);
            if (result >= 1) {
                return "删除成功";
            } else {
                return "删除失败";
            }
        } else {
            key.setStatus("处理完成");
            key.setTimestamp(requestime);
            key.setId(id);
            String prodkey = UUID.randomUUID().toString();
            key.setKey(prodkey);
            keyService.updateKey(key);

            //处理完成调用V2X系统接口 SENDSERVER_URL 接口地址  jsonParams 请求参数
//                String a = HttpClientUtil.postJosnContent(url, jsonParams);


            return "不为空,更新信息完成";
        }
    }

    private String forwordv2x(Key key) throws  Exception{ //获取key加密返回
      Map<String, String> params = new HashMap<String, String>();
        int carid = key.getCarid();
        String scarid =String.valueOf(carid);
        String keymessage = key.getTimestamp();
        params.put("key",keymessage);
        params.put("carid",scarid);
        params.put("key","");
        params.put("keytime","");
        String url ="";
        //推送秘钥加密 和keytime加密
        //调用v2x接口地址，传递token相关参数 token（UUID） token 有效期至
        //key相关信息
        HttpClientResult httpresult = HttpClientUtils.doPost(url,params);
        int code = httpresult.getCode();
        String content = httpresult.getContent();
        logger.info("code【" + code + "】   content【" + content + "】");
        if (content.length() == 0) {
            throw new Exception("没有反馈信息");
        }
        JSONObject obj = JSONObject.fromObject(content);
        String code1 = obj.getString("code");
        if (code1.equals("200")) {
            String success = obj.getString("success");
            if ("true".equals(success)) {
                String data = obj.getString("data");
                //调用AES工具类把拿到的数据进行加密
                String  aa = EncryptionUtil.encrypt(data);

                return aa;
            }else{
                throw new Exception("反馈报错：" + obj.getString("message"));
            }
        } else {
            throw new Exception("反馈报错：" + obj.getString("msg"));
        }
    }

    private long checktime(String dbDate) throws ParseException {//token表中有数据
        //        //如果计算结果小于0 返回true 大于0返回false
        //请求时间 +超时时间-当前时间 -提前提起时间（15）
//        long requestime1="20190924133049794";

        long nd = 1000 * 24 * 60 * 60;
        long nh = 1000 * 60 *60;
        long nm = 1000 * 60;
        Date now = new Date();
        Date d3 = new Date(now .getTime());

//        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        Date d1 = df.parse(dbDate);
//        Date d2 = df.parse(vailDate);
//        Date d4 = df.parse(beforedate);

        Date afterDate = new Date(new Date().getTime() + 3600000);
        Date sysDate = new Date(new Date().getTime());
        Date beforeDate = new Date(new Date().getTime()+600000);

//        请求时间 +超时时间-当前时间 -提前提起时间（15）
        long diff = d1.getTime() + afterDate.getTime()-sysDate.getTime()-beforeDate.getTime();

        long minute = diff/nm;
        if(diff<0){//token 已过期
            return -1;
        }else{
            System.out.println("相差的分钟数为："+minute);
            return minute;
        }

    }

    @RequestMapping("/hello")
    public Object user(){
            User user =userService.getStudentById(1);
        System.out.println(user.toString());
        Object obj = updateUser1();
        return  obj;
//        return userService.getStudentById(2);
    }
//@RequestMapping("/hello")
//    public  String hh(){
//        return  "ni hoa";
//    }

    public  Object updateUser1(){
        User user = new User();
        user.setId("1");
        user.setAge(104);

        user.setUsername("赵超越44");
        int result  = userService.updateStudent(user);
        if (result >= 1) {
            return "修改成功";
        } else {
            return "修改失败";
        }


    }
@RequestMapping(value = "/update")
    public  Object updateUser(){
        User user = new User();
        user.setId("1");
        user.setAge(101);

        user.setUsername("赵超越11");
    int result  = userService.updateStudent(user);
    if (result >= 1) {
            return "修改成功";
        } else {
            return "修改失败";
        }

    }

    @RequestMapping(value = "/delete",method = RequestMethod.GET) //method = RequestMethod.GET
    public String delete( int id) {  //http://localhost:8080/delete?id=1

        int result = userService.deleteStudent(""+id+"");
        if (result >= 1) {
            return "删除成功";
        } else {
            return "删除失败";
        }
    }

    @RequestMapping(value = "/insert",method = RequestMethod.GET)
    public  User add(int id,int age,String username,String password){
        User user = new User();
        user.setId(""+id+"");
        user.setAge(age);
        user.setUsername(""+username+"");
        user.setPassword(""+password+"");
        return  userService.insertUser(user);
    }

    @RequestMapping(value = "/json")
    public String json(){
        User user =userService.getStudentById(1);
        String username = user.getUsername();
        String encryptResultStr = EncryptionUtil.encrypt(username);
        return "返回加密\r\n"+ encryptResultStr;
    }

    @RequestMapping("/batchUpdate")
    public int batchUpdate() {
        User user=new User();
        user.setAge(999);
        List<Integer> listIds=userService.listIds();
        user.setIds(listIds);
        return userService.batchUpdate(user);
    }


    @RequestMapping("/batchdelete")
    public String batchDelete() {
        User user=new User();
        List<Integer> listIds=userService.listIds();
        user.setIds(listIds);
        for(int t=0;t<listIds.size();t++){
            Integer id = listIds.get(t);
            String a=String.valueOf(id);
            System.out.println("id为"+id+" aw为"+a);
            userService.deleteStudent(String.valueOf(id));
        }

        return "成功删除多条"; //这样应该比较浪费资源 每次都是 service 调用 mapper mapp每次去找xml找到对应的sqk
       /* int  a = userService.batchDelete(user);
        if(a>0){
        return "成功删除多条";
        }else{
         return "删除失败";
        }*/
    }

}
