package com.luckk.lizzie.service;

import com.luckk.lizzie.Goods;
import com.luckk.lizzie.OrderFunction;
import com.luckk.lizzie.spring.api.LService;

import java.util.Random;

/**
 * @FileName: OrderService
 * @Author: LuckKun
 * @Email: 1546165200@qq.com
 * @Date: 2022/5/12 19:10
 */
@LService
public class OrderService implements OrderFunction {


    private Random random = new Random();
    @Override
    public Goods getGoodsInfo(String id) {
        //假装通过数据库查找得到如下的结果

        Goods goods = new Goods();
        goods.setGoodsName("3090ti显卡");
        goods.setCount(random.nextInt(5000));
        return goods;
    }
}
