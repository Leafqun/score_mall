package com.czbank.coding.user.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.czbank.coding.api.User;
import com.czbank.coding.user.mapper.UserMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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

        System.out.println("result:" + result);
        return userMapper.selectList(null);
    }

    @GetMapping("update")
    public Object userUpdate() {

        User user = new User();
        user.setId(2);
        user.setNickname("王国浩");
        user.setAddress("杭州");
        user.setBankaccount("92843");
        user.setMail("12314132@");
        user.setPassword("231434");
        user.setPhone("23414");
        userMapper.updateById(user);
        Integer result = userMapper.updateById(user);
        return userMapper.selectList(null);
    }

    /**
     * 通过ID查询
     */
    @GetMapping("selectByID")
    public Object userSelectByID() {
        User user = userMapper.selectById(2);
        return user;
    }

//    /**
//     * 通过多个属性查询
//     * 无法查阅相同字段的多条数据
//     */
////    @GetMapping("selectMore")
////
////    public  void userSelectMore(){
////        User user = new User();
////        user.setName("");
////        userMapper.selectOne(user);
////    }

    /**
     * 批量查询ID
     */

    @GetMapping("seleteBatchID")
    public Object userSeletctBatchID() {
        List<Integer> idList = new ArrayList<>();
        idList.add(1);
        idList.add(2);
        List<User> emps = userMapper.selectBatchIds(idList);
        return emps;
    }
    /**
     *
     * @param currentPage
     * @param pageSize
     * @return
     * 分页查找
     */
    @GetMapping("selectPage")
    public Map<String, Object> userSelectPage(@RequestParam Integer currentPage, @RequestParam Integer pageSize) {

        Map<String, Object> map = new HashMap<>();
//        QueryWrapper<User> qw = new QueryWrapper<>();
//        Page<User> page = new Page<>(currentPage, pageSize);
        map.put("page",userMapper.selectPage(new Page<>(currentPage,pageSize),null));
        return map;
    }

    /**
     * 条件查找,姓名+手机号
     */
    @GetMapping("selectCon")
    public Object userSelectCon(String name, String phone,Integer currentPage, Integer pageSize){
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        return userMapper.selectPage(new Page<>(currentPage,pageSize),wrapper
                .eq("name",name)
                .eq("phone",phone));

    }
    /**
     * 通过Map条件查询
     */

    @GetMapping("seleteMap")
    public Object userSeletctMap() {
        Map<String, Object> columnMap = new HashMap<>();
        columnMap.put("NICKNAME", "王不是国浩");
        List<User> emps = userMapper.selectByMap(columnMap);
        return "条件查询成功";
    }

    /**
     * 根据ID删除
     */
    @GetMapping("deleteByID")
    public Object userDeleteByID() {
        userMapper.deleteById(7);
        return "Sucessfully delete";
    }
    /**
     * 分页查找
     */
//    @GetMapping("/getGoodList")
//    public Map<String, Object> getGoodList(@RequestParam Integer currentPage, @RequestParam Integer pageSize) {
//        Map<String, Object> map = new HashMap<>();
//        QueryWrapper<Good> qw = new QueryWrapper<>();
//        qw.orderByDesc("id");
//        Page<Good> page = new Page<>(currentPage, pageSize);
//        map.put("page", goodMapper.selectPage(page, qw));
//        return map;
//    }




    /**
     * 登录（伪）
     */
    @GetMapping ("login")
    public String login(String phone, String password) {
        String page = "fail";
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("phone",phone);
        User user = userMapper.selectOne(queryWrapper);
        if (user != null && password.equals(user.getPassword())) {
            page = "Sucessfully login";
    }
        return page;
    }
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



