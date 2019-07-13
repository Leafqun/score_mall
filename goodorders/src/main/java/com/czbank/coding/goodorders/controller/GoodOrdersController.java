package com.czbank.coding.goodorders.controller;


import com.czbank.coding.goodorders.mapper.GoodOrdersMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("goodorders")
public class GoodOrdersController {

    @Resource
    private GoodOrdersMapper goodOrdersMapper;

    @GetMapping("/getList")
    public Object getList(){
        return  goodOrdersMapper.selectList(null);
    }
}
