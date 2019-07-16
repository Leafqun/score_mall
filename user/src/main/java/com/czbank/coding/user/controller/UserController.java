package com.czbank.coding.user.controller;

import api.Address;
import api.Card;
import api.User;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import com.czbank.coding.user.mapper.AddressMapper;
import com.czbank.coding.user.mapper.CardMapper;
import com.czbank.coding.user.mapper.UserMapper;
import com.czbank.coding.user.service.UserService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

/**
 *
 */
@RestController
@RequestMapping("user")
public class UserController {
    @Resource
    private UserMapper userMapper;

    @Resource
    private CardMapper cardMapper;

    @Resource
    private AddressMapper addressMapper;

    @Resource
    private UserService userService;

    @Resource
    private RestTemplate template;

    private static Pattern NUMBER_PATTERN = Pattern.compile("^[-\\+]?[\\d]*$");

    @GetMapping("getCardListByUserId")
    public Map<String, Object> getCardListByUserId(@RequestParam Integer userid) {
        Map<String, Object> map = new HashMap<>();
        QueryWrapper<Card> qw = new QueryWrapper<>();
        qw.eq("user_id", userid);
        map.put("cardList", cardMapper.selectList(qw));
        return map;
    }

    @GetMapping("/getFirstAddress")
    public Map<String, Object> getFirstAddress(@RequestParam Integer userid) {
        Map<String, Object> map = new HashMap<>();
        QueryWrapper<Address> qw = new QueryWrapper<>();
        Map<String, Object> m = new HashMap<>();
        m.put("user_id", userid);
        m.put("first", 1);
        qw.allEq(m);
        map.put("address", addressMapper.selectOne(qw));
        return map;
    }

    @GetMapping("getAddressListByUserId")
    public Map<String, Object> getAddressListByUserId(@RequestParam Integer userid, @RequestParam Integer currentPage, @RequestParam Integer pageSize) {
        Map<String, Object> map = new HashMap<>();
        QueryWrapper<Address> qw = new QueryWrapper<>();
        qw.orderByDesc("first");
        qw.eq("user_id", userid);
        Page<Address> page = new Page<>(currentPage, pageSize);
        map.put("page", addressMapper.selectPage(page, qw));
        return map;
    }

    @GetMapping("insertAddress")
    public Map<String, Object> insertAddress(Address address) {
        Map<String, Object> map = new HashMap<>();
        System.out.println(address);
        if (addressMapper.insert(address) == 0) {
            map.put("msg", "插入失败");
        } else {
            map.put("msg", "success");
        }
        return map;
    }

    @RequestMapping(value = "updateAddress", method = {RequestMethod.GET, RequestMethod.POST})
    public Map<String, Object> updateAddress(Address address) {
        Map<String, Object> map = new HashMap<>();
        if (userService.updateFirst(address) == 1) {
            map.put("msg", "success");
        } else {
            map.put("msg", "更新失败");
        }
        return map;
    }

    @GetMapping("update")
    public Map<String, Object> userUpdate(@RequestParam String nickname, @RequestParam String address,
                                          @RequestParam String bankaccount, @RequestParam String mail
            , @RequestParam String name, @RequestParam String password, @RequestParam String phone,
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
        map.put("修改成功", userMapper.selectById(id));
        return map;
    }

    /**
     * 通过ID查询
     */
    @GetMapping("selectByID")
    public Map<String, Object> userSelectByID(Integer id) {
        Map<String, Object> map = new HashMap<>();
        User user = userMapper.selectById(id);
        map.put("user",user);
        map.put("Sucess", user);
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

    @GetMapping("login")
    public Map<String, Object> login(@RequestParam String phone, @RequestParam String password) {
        Map<String, Object> map = new HashMap<>();
        QueryWrapper<User> qw = new QueryWrapper<>();
        if (NUMBER_PATTERN.matcher(phone).matches()) {
            qw.eq("phone", phone);
        } else {
            qw.eq("nickname", phone);
        }
        qw.select("id", "nickname", "password", "avatar", "phone", "score");
        User user = userMapper.selectOne(qw);
        if (user == null) {
            map.put("msg", "手机号未注册或者用户名未注册");
            return map;
        }
        if (!password.equals(user.getPassword())) {
            map.put("msg", "密码错误");
            return map;
        }
        user.setPassword(null);
        map.put("msg", "success");
        map.put("user", user);
        return map;
    }


    @GetMapping("pay")
    public Map<String,Object> pay(@RequestParam Integer userid,@RequestParam Integer goodid,@RequestParam String password) {
        Map<String, Object> map = new HashMap<>();
        Map<String, Object> map1 = new HashMap<>();
        QueryWrapper<User> qw = new QueryWrapper<>();
        Integer goodScore;
        User user = userMapper.selectById(userid);
        map1.put("id", user.getId());
        map1.put("password", user.getPassword());
        qw.allEq(map1);
        if (password.equals(user.getPassword())) {
            goodScore = template.getForEntity("http://good/good/getGoodScore?id={goodid}", Integer.class, goodid).getBody();
            user.setScore(user.getScore() - goodScore);
            userMapper.updateById(user);
            if (user.getScore()>= 0) {
                qw.select("id", "score");
                map.put("score", userMapper.selectOne(qw));
            } else {
                map.put("msg", "积分余额不足");
            }
        }
         else {
            map.put("msg", "密码错误");
        }
         map.put("msg", "success");
        return map;

    }

}




