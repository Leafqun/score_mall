package com.czbank.coding.user.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.czbank.coding.api.User;
import com.czbank.coding.user.mapper.UserMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 *
 */
@RestController
@RequestMapping("user")
<<<<<<< HEAD
=======

>>>>>>> 5ee9134f1ebce906ff3ffa2b0bfdce6a22f9bf20
public class Usercontroller {
    @Resource
    private UserMapper userMapper;


//    @GetMapping("insert")
//    public Map<String,Object> userInsert(@RequestParam Integer nickname, @RequestParam String address,
//                                         @RequestParam String bankaccount,@RequestParam String mail
//    ,@RequestParam String name,@RequestParam String password,@RequestParam String phone) {
//        //增加即注册
//        User user = new User();
//        Map<String, Object> map = new HashMap<>();
//        user.setNickname(nickname);
//        user.setAddress(address);
//        user.setBankaccount(bankaccount);
//        user.setMail(mail);
//        user.setName(name);
//        user.setPassword(password);
//        user.setPhone(phone);
//        userMapper.insert(user);
//        return userMapper.selectList(null);
//    }

    //    public Map<String, Object> getGoodList(@RequestParam Integer currentPage, @RequestParam Integer pageSize) {
//        Map<String, Object> map = new HashMap<>();
//        QueryWrapper<Good> qw = new QueryWrapper<>();
//        qw.orderByDesc("id");
//        Page<Good> page = new Page<>(currentPage, pageSize);
//        map.put("page", goodMapper.selectPage(page, qw));
//        return map;

    @GetMapping("update")
    public Map<String,Object> userUpdate(@RequestParam String nickname, @RequestParam String address,
                                         @RequestParam String bankaccount,@RequestParam String mail
            ,@RequestParam String name,@RequestParam String password,@RequestParam String phone,
                                         @RequestParam Integer id) {
        Map<String, Object> map = new HashMap<>();
        User user = new User();
        user.setId(id);
        user.setNickname(nickname);
        user.setAddress(address);
        user.setBankaccount(bankaccount);
        user.setMail(mail);
        user.setPassword(password);
        user.setPhone(phone);
        userMapper.updateById(user);
        map.put("修改成功",userMapper.selectById(id));
        return map;
    }

    /**
     * 通过ID查询
     */
    @GetMapping("selectByID")
    public Map<String,Object> userSelectByID(Integer id) {
        Map<String, Object> map = new HashMap<>();
        User user = userMapper.selectById(id);
        map.put("Sucess",user);
        return map;
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
//
//    /**
//     * 批量查询ID
//     */

//    @GetMapping("seleteBatchID")
////    public Object userSeletctBatchID() {
////        List<Integer> idList = new ArrayList<>();
////        idList.add(1);
////        idList.add(2);
////        List<User> emps = userMapper.selectBatchIds(idList);
////        return emps;
//////    }
//    /**
//     *
//     * @param currentPage
//     * @param pageSize
//     * @return
//     * 分页查找
//     */
//    @GetMapping("selectPage")
//    public Map<String, Object> userSelectPage(@RequestParam Integer currentPage, @RequestParam Integer pageSize) {
//
//        Map<String, Object> map = new HashMap<>();
////        QueryWrapper<User> qw = new QueryWrapper<>();
////        Page<User> page = new Page<>(currentPage, pageSize);
//        map.put("page",userMapper.selectPage(new Page<>(currentPage,pageSize),null));
//        return map;
//    }

//    /**
//     * 条件查找,姓名+手机号
//     */
//    @GetMapping("selectCon")
//    public Object userSelectCon(String name, String phone,Integer currentPage, Integer pageSize){
//            QueryWrapper<User> wrapper = new QueryWrapper<>();
//            return userMapper.selectPage(new Page<>(currentPage,pageSize),wrapper
//                    .eq("name",name)
//                    .eq("phone",phone));
//
//    }
//    /**
//     * 通过Map条件查询
//     */
//
//    @GetMapping("seleteMap")
//    public Object userSeletctMap() {
//        Map<String, Object> columnMap = new HashMap<>();
//        columnMap.put("NICKNAME", "王不是国浩");
//        List<User> emps = userMapper.selectByMap(columnMap);
//        return "条件查询成功";
//    }

//    /**
//     * 根据ID删除
//     */
//    @GetMapping("deleteByID")
//    public Object userDeleteByID() {
//        userMapper.deleteById(7);
//        return "Sucessfully delete";
//    }

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
    public Map<String,Object> login(String phone, String password) {
        Map<String, Object> map = new HashMap<>();
        QueryWrapper qw = new QueryWrapper();
        map.put("fail",null);
        qw.eq("phone",phone);
        User user = userMapper.selectOne(qw);
        User conten = new User();
        if (user != null && password.equals(user.getPassword())) {
            map.clear();
            map.put("sucess",userMapper.selectMaps(qw.select("id","nickname","avatar")));
    }
        return map;
    }
}



