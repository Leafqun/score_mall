package com.czbank.coding.good.controller;

import com.czbank.coding.api.Good;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.czbank.coding.good.mapper.GoodMapper;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("good")
@CrossOrigin
public class GoodController {

    @Resource
    private GoodMapper goodMapper;

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

    @GetMapping("/getGood")
    public Map<String, Object> getGood(Integer id) {
        Map<String, Object> map = new HashMap<>();
        map.put("good", goodMapper.selectById(id));
        return map;
    }
}
