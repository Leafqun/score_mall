package com.czbank.coding.goodorders.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.czbank.coding.api.GoodOrders;
import com.czbank.coding.goodorders.mapper.GoodOrdersMapper;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("goodorders")
public class GoodOrdersController {

    @Resource
    private GoodOrdersMapper goodOrdersMapper;

    //查找
    @GetMapping("getList")
    public Object getList(){
        return  goodOrdersMapper.selectList(null);
    }

    @GetMapping("getListPage")
    public Map<String,Object> getListPage(@RequestParam Integer currentPage, @RequestParam Integer pageSize){
        Map<String,Object> map = new HashMap<>();
        QueryWrapper<GoodOrders> qw = new QueryWrapper<GoodOrders>();
        Page<GoodOrders> page = new Page<GoodOrders>(currentPage,pageSize);
        map.put("goodOrders",goodOrdersMapper.selectPage(page,qw));
        return map;
    }

    @GetMapping("selectById")
    public Object selectById (@RequestParam Integer id){
        return goodOrdersMapper.selectById(id);
    }

    @GetMapping("selectByType")
    public Object selectByType (GoodOrders goodOrders){
        QueryWrapper<GoodOrders> qw = new QueryWrapper<>();
        if(goodOrders.getOrdersId()!=null){
            qw.eq("orders_id",goodOrders.getOrdersId());
        }
        else if (goodOrders.getGoodId()!=null){
            qw.eq("good_id",goodOrders.getGoodId());
        }
        else {
            return "未提供相应信息";
        }
        return goodOrdersMapper.selectOne(qw);
    }

    @GetMapping("selectByTypeByPage")
    public Map<String,Object> selectByTypeByPage
            (@RequestParam Integer currentPage, @RequestParam Integer pageSize,GoodOrders goodOrders){
        Map<String,Object> map = new HashMap<>();
        QueryWrapper<GoodOrders> qw = new QueryWrapper<>();

        if(goodOrders.getOrdersId()!=null){
            qw.eq("orders_id",goodOrders.getOrdersId());
        }
        else if (goodOrders.getGoodId()!=null){
            qw.eq("good_id",goodOrders.getGoodId());
        }
        else {
            map.put("fail",null);
            return map;
        }
        Page<GoodOrders> page = new Page<GoodOrders>(currentPage,pageSize);
        map.put("goodOrders",goodOrdersMapper.selectPage(page,qw));
        return map;
    }

    //删除
    @GetMapping("deleteById")
    public  Map<String,Object> deleteById(@RequestParam Integer id){
        Map<String,Object> map =  new HashMap<>();
        map.put("delete",goodOrdersMapper.deleteById(id));
        return map;
    }

    @GetMapping("deleteByType")//需要修改
    public Map<String,Object> deleteByType(GoodOrders goodOrders){
        Map<String,Object> map = new HashMap<>();

        QueryWrapper<GoodOrders> qw = new QueryWrapper<>();
        if(goodOrders.getOrdersId()!=null){
            qw.eq("orders_id",goodOrders.getOrdersId());
        }
        else if (goodOrders.getGoodId()!=null){
            qw.eq("good_id",goodOrders.getGoodId());
        }
        else {
            map.put("fail ",null);
            return map;
        }
        map.put("delete",goodOrdersMapper.delete(qw));
        return map;
    }


}
