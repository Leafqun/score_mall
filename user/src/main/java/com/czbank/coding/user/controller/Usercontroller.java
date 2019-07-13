package com.czbank.coding.user.controller;

import com.czbank.coding.api.User;
import com.czbank.coding.user.mapper.UserMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 */
@RestController
@RequestMapping("user")
@Controller
public class Usercontroller {
    @Resource
    private UserMapper userMapper;
    @GetMapping("insert")
    public Object userInsert() {
    //增加
        User user = new User();
//        user.setId(123);
        user.setNickname("w");
        user.setAddress("w");
        user.setBankaccount("w");
        user.setMail("w");
        user.setName("w");
        user.setPassword("w");
        user.setPhone("w");
        userMapper.insert(user);
        Integer result = userMapper.insert(user);

        System.out.println("result:"+ result);
        return userMapper.selectList(null);
    }

    @GetMapping("update")
    public  Object userUpdate(){

        User user = new User();
        user.setId(2);
        user.setNickname("王国浩");
        user.setAddress("杭州");
        user.setBankaccount("92843");
        user.setMail("12314132@");
        user.setName("王不是国浩");
        user.setPassword("231434");
        user.setPhone("23414");
        userMapper.updateById(user);
        Integer result = userMapper.updateById(user);
        return userMapper.selectList(null);
    }
    /**通过ID查询*/
    @GetMapping("selectByID")
    public Object userSelectByID(){
         User user = userMapper.selectById(7);
         return user;
    }

    /**
     * 通过多个属性查询
     * 无法查阅相同字段的多条数据
     */
//    @GetMapping("selectMore")
//
//    public  void userSelectMore(){
//        User user = new User();
//        user.setName("");
//        userMapper.selectOne(user);
//    }

    /**
     *  批量查询
     */

    @GetMapping("seleteBatchID")
    public Object userSeleteBatchID(){
        List<Integer>idList = new ArrayList<>();
        idList.add(1);
        idList.add(2);
        List<User> emps = userMapper.selectBatchIds(idList);
        return emps;
    }

    /**
     * 通过Map条件查询
     */

    @GetMapping("seleteMap")
    public Object userSeleteMap(){
        Map<String,Object> columnMap = new HashMap<>();
        columnMap.put("NICKNAME","的");
        List<User> emps = userMapper.selectByMap(columnMap);
        return emps;
    }







//    @GetMapping("getList")
//  public Object getList() {
////        return userMapper.selectList(null);
////    }

//    @GetMapping("/getGoodList")
//    public Map<String, Object> getGoodList(@RequestParam Integer currentPage, @RequestParam Integer pageSize) {
//        Map<String, Object> map = new HashMap<>();
//        QueryWrapper<Good> qw = new QueryWrapper<>();
//        qw.orderByDesc("id");
//        Page<Good> page = new Page<>(currentPage, pageSize);
//        map.put("page", page);
//        return map;


}
