package com.czbank.coding.orders.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.czbank.coding.api.Orders;
import com.czbank.coding.orders.mapper.OrdersMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("orders")
public class OrdersController {
    @Resource
    private OrdersMapper ordersMapper;

    //增加，付款成功后添加订单信息
    @GetMapping("insertOrders")
    public Map<String, Object> ordersInsert(@RequestParam Integer account_id, @RequestParam LocalDateTime createtime, @RequestParam String address,
                                            @RequestParam String phone, @RequestParam String note
            , @RequestParam String status) {

        Orders orders = new Orders();
        Map<String, Object> map = new HashMap<>();
        orders.setAccountId(account_id);
        orders.setCreatedTime(createtime);
        orders.setAdrress(address);
        orders.setPhone(phone);
        orders.setNote(note);
        orders.setStatus(status);

        ordersMapper.insert(orders);
        return (Map<String, Object>) ordersMapper.selectList(null);
    }


    //查找订单，用户通过订单号查找
    @GetMapping("getOrdresList")
    public Object getList(){
        return (Object) ordersMapper.selectList(null);
    }

    @GetMapping("selectOrdersByID")
    public Object selectById (@RequestParam Integer account_id){
        return  ordersMapper.selectById(account_id);
    }

    @GetMapping("getListPage")
    public Map<String, Object> getOrdersList(@RequestParam Integer currentPage, @RequestParam Integer pageSize) {
        Map<String, Object> map = new HashMap<>();
        QueryWrapper<Orders> qw = new QueryWrapper<Orders>();
        Page<Orders> page = new Page<Orders>(currentPage, pageSize);
        map.put("page", (Object) ordersMapper.selectPage(page, qw));
        return map;
    }

    //删除订单，用户需要删除时
    @GetMapping("deleteOrdersById")
      public String ordersDeleteByID(Integer account_id) {
            ordersMapper.deleteById(account_id);
            return "订单删除成功";
       }


    /*
    //修改订单
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




