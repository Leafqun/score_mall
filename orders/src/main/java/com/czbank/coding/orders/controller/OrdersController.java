package com.czbank.coding.orders.controller;

import api.Orders;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.czbank.coding.orders.mapper.OrdersMapper;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("orders")
public class OrdersController {
    @Resource
    private OrdersMapper ordersMapper;

    @GetMapping("insertOrders")
    public Map<String, Object> ordersInsert(Orders orders) {
        Map<String, Object> map = new HashMap<>();
        //增加，付款成功后添加订单信息
        orders.setCreateTime(LocalDateTime.now());
        if (ordersMapper.insert(orders) == 1) {
            map.put("msg", "success");
        } else {
            map.put("msg", "fail");
        }
        return map;
    }

    @RequestMapping(value = "/updateOrders", method = {RequestMethod.GET, RequestMethod.POST})
    public Map<String, Object> updateOrders(Orders orders) {
        Map<String, Object> map = new HashMap<>();
        if (ordersMapper.updateById(orders) == 1) {
            map.put("msg", "success");
        } else {
            map.put("msg", "更新失败");
        }
        return map;
    }


    //查找订单，用户通过订单号查找
    @GetMapping("getOrdresList")
    public Object getList(){
        return (Object) ordersMapper.selectList(null);
    }


    @GetMapping("selectOrdersByID")
    public Object selectById (@RequestParam Integer accountId){
        return  ordersMapper.selectById(accountId);
    }

    @GetMapping("getOrdersListByUserId")
    public Map<String, Object> getOrdersListByUserId(@RequestParam Integer currentPage, @RequestParam Integer pageSize, @RequestParam Integer userId) {
        Map<String, Object> map = new HashMap<>();
        QueryWrapper<Orders> qw = new QueryWrapper<Orders>();
        Page<Orders> page = new Page<Orders>(currentPage, pageSize);
        qw.eq("account_id", userId);
        map.put("page", ordersMapper.selectPage(page, qw));
        return map;
    }

    //删除订单，用户需要删除时
    @GetMapping("deleteOrdersById")
      public Map<String, Object> ordersDeleteById(Integer id) {
        Map<String, Object> map = new HashMap<>();
            if (ordersMapper.deleteById(id) == 1) {
                map.put("msg", "success");
            } else {
                map.put("msg", "更新失败");
            }
        return map;
       }


    /*
    //修改订单，
    @GetMapping("update")//订单ID和物品ID不为空，可修改物品数量
    public Map<String,Object> update(GoodOrders goodOrders){
        Map<String,Object> map = new HashMap<>();

        QueryWrapper<GoodOrders> qw = new QueryWrapper<>();

        if(goodOrders.getGoodId()!=null && goodOrders.getOrdersId()!=null){
            map.put("orders_id", goodOrders.getOrdersId());
            map.put("good_id", goodOrders.getGoodId());
            qw.allEq(map);
        }
        else {
            map.put("msg", "请求参数为空");
        }

        GoodOrders goodOrders1 = goodOrdersMapper.selectOne(qw);
        if (goodOrders1 == null) {
            Map<String,Object> map2 = new HashMap<>();
            map2.put("msg", "未找到对象，修改失败");
            return map2;
        }
        goodOrders1.setNumber(goodOrders.getNumber());
        goodOrders1.setCreatedBy("tt");
//        goodOrders1.setGoodId(10);
        Map<String,Object> map1 = new HashMap<>();
        map1.put("update",goodOrdersMapper.updateById(goodOrders1));
        return map1;
    }
    */


}




