package com.czbank.coding.good.service;

import api.Good;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.List;
import java.util.Map;

public interface GoodService {
    public Map<String, Object> selectGoodListByType(Page<Good> page, Good good);
}
