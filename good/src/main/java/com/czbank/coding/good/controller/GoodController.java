package com.czbank.coding.good.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.czbank.coding.api.Good;
import com.czbank.coding.good.mapper.GoodMapper;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("good")
@CrossOrigin
public class GoodController {

    @Resource
    private GoodMapper goodMapper;

    @Resource
    private RestTemplate restTemplate;

    @GetMapping("getList")
    public Object getList() {
        return goodMapper.selectList(null);
    }

    @GetMapping("/getGoodList")
    public Map<String, Object> getGoodList(@RequestParam Integer currentPage, @RequestParam Integer pageSize) {
        Map<String, Object> map = new HashMap<>();
        QueryWrapper<Good> qw = new QueryWrapper<>();
        qw.orderByDesc("id");
        Page<Good> page = new Page<>(currentPage, pageSize);
        map.put("page", goodMapper.selectPage(page, qw));
        return map;
    }

    @GetMapping("/getGoodListByType")
    public Map<String, Object> getGoodListByType(@RequestParam Integer currentPage, @RequestParam Integer pageSize, Good good) {
        Map<String, Object> map = new HashMap<>();
        QueryWrapper<Good> qw = new QueryWrapper<>();
        qw.orderByDesc("id");
        if (!StringUtils.isEmpty(good.getBigClassify())) {
            qw.eq("big_classify", good.getBigClassify());
        } else if (!StringUtils.isEmpty(good.getSmallClassify())) {
            qw.eq("small_classify", good.getSmallClassify());
        } else {
            map.put("msg", "类型信息为空");
            return map;
        }
        Page<Good> page = new Page<>(currentPage, pageSize);
        map.put("page", goodMapper.selectPage(page, qw));
        return map;
    }

    @GetMapping("getGood")
    public Map<String,Object> getGood(Integer id) {
        Map<String, Object> map = new HashMap<>();
        map.put("good",goodMapper.selectById(id));
        return map;
    }

    @GetMapping("getGoodListByName")
    public Map<String, Object> getGoodListLike(@RequestParam Integer currentPage, @RequestParam Integer pageSize, @RequestParam String name) {
        Map<String, Object> map = new HashMap<>();
        QueryWrapper qw = new QueryWrapper();
        qw.orderByDesc("id");
        if (!StringUtils.isEmpty(name)) {
            if (!StringUtils.isEmpty(qw.like("good_name",name))) {
                Page<Good> page = new Page<>(currentPage, pageSize);
                map.put("page", goodMapper.selectPage(page, qw));
            } else {
                map.put("msg", "查询商品为空");
            }
        } else {
            map.put("msg", "请输入查询信息");
        }
        return map;
    }
    @GetMapping("getGoodScore")
    public Integer getGoodScore(Integer id){
        Good good = new Good();
        good = goodMapper.selectById(id);
        return good.getScorePrice();
    }
}



