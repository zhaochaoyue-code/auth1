package com.hydsoft.springboot.controller;

import com.hydsoft.springboot.model.Goods;
import com.hydsoft.springboot.service.GoodsSrevice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class GoodsController {

    @Autowired
   private GoodsSrevice goodsSrevice;

    @RequestMapping(value = "/allinsert")
    public  String allinsert(){
        List<Goods> list = new ArrayList<Goods>();
        Goods goods = new Goods();
        goods.setGoodsid(4);
        goods.setGoodsname("感冒药");
        goods.setGoodstype("盒子");
        list.add(goods);
        Goods goods1 = new Goods();
        goods1.setGoodsid(5);
        goods1.setGoodsname("消炎药");
        goods1.setGoodstype("箱子");
        list.add(goods1);
        Goods goods2= new Goods();
        goods2.setGoodsid(3);
        goods2.setGoodsname("下火药");
        goods2.setGoodstype("瓶子");
        list.add(goods2);


        int a =  goodsSrevice.batchInsert(list);
        if(a>0){
            return "ok";
        }else {
            return "error";
        }


    }


    @RequestMapping(value = "/allselect")
    public Object testBatchSelect(){
        List<Long> userList =new ArrayList();
        userList.add(5L);
//        userList.add(2L);
//        userList.add(3L);
        userList.add(4L);//id


        List<Goods> list = goodsSrevice.batchSelect(userList);
        for (Goods goods : list) {
            System.out.println(goods.toString());
        }
        return list;
        //[{"goodsid":1,"goodsname":"感冒药","goodstype":"盒子"},{"goodsid":2,"goodsname":"消炎药","goodstype":"箱子"},{"goodsid":3,"goodsname":"下火药","goodstype":"瓶子"}]
    }

    @RequestMapping(value = "/allupdate")
    public String testBatchUpdate(){
        List<Goods> list = new ArrayList<Goods>();

        Goods goods = new Goods();
        goods.setGoodsid(2);
        goods.setGoodsname("消炎药aaa");
        goods.setGoodstype("箱子aaa");
        list.add(goods);

        Goods goods1 = new Goods();
        goods1.setGoodsid(3);
        goods1.setGoodsname("下火药bb");
        goods1.setGoodstype("瓶子bbb");
        list.add(goods1);

        int a = goodsSrevice.batchUpdate(list);
        if(a>0){
            return "many 成功";
        }else {
            return "error";
        }

//        return list;
    }

    @RequestMapping(value = "/alldelete")
    public String testBatchDelete(){
        List<Long> list = new ArrayList();
        list.add(2L);
        list.add(3L);
        System.out.println(list);

        int a = goodsSrevice.batchDelete(list);
        System.out.println(a);//打印影响的条数，这里输出3

        if(a>0){
            return "many  delete成功";
        }else {
            return "error";
        }
    }
}
