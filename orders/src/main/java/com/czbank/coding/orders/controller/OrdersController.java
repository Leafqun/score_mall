package com.czbank.coding.orders.controller;

import com.czbank.coding.orders.mapper.OrdersMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("orders")
public class OrdersController {
    @Resource
    private OrdersMapper ordersMapper;

    @GetMapping("getList")
    public Object getList() {
        return ordersMapper.selectList(null);
    }



}
