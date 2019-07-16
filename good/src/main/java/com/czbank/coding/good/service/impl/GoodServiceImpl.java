package com.czbank.coding.good.service.impl;

import api.Good;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.czbank.coding.good.mapper.GoodMapper;
import com.czbank.coding.good.service.GoodService;
import com.czbank.coding.good.util.EhcacheUtil;
import com.czbank.coding.good.util.JedisUtil;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@Service
public class GoodServiceImpl implements GoodService {

    @Resource
    private EhcacheUtil ehcacheUtil;

    @Resource
    private JedisUtil redisUtil;

    @Resource
    private GoodMapper goodMapper;

    @Override
    public Map<String, Object> selectGoodListByType(Page<Good> page, Good good) {
        Map<String, Object> map = new HashMap<>();
        QueryWrapper<Good> qw = new QueryWrapper<>();
        String key;
        if (!StringUtils.isEmpty(good.getBigClassify())) {
            qw.eq("big_classify", good.getBigClassify());
            key = "good_page_big" + page.getCurrent();
        } else if (!StringUtils.isEmpty(good.getSmallClassify())) {
            qw.eq("small_classify", good.getSmallClassify());
            key = "good_page_small" + page.getCurrent();
        } else {
            map.put("msg", "类别信息为空");
            return map;
        }
        qw.orderByDesc("id");
        Object p1 = ehcacheUtil.get(key, "good");
        if (p1 != null) {
            map.put("page", p1);
            return map;
        }
        Object p2 = redisUtil.get(key);
        if (p2 != null) {
            map.put("page", p2);
            return map;
        }
        IPage<Good> p3 = goodMapper.selectPage(page, qw);
        if (p3.getRecords() != null) {
            redisUtil.set(key, p3);
            ehcacheUtil.set(key, p3, "good");
        }
        map.put("page", p3);
        return map;
    }
}
