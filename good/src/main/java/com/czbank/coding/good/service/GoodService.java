package com.czbank.coding.good.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.czbank.coding.api.Good;

import java.util.List;
import java.util.Map;

public interface GoodService {
    public Map<String, Object> selectGoodListByType(Page<Good> page, Good good);
}
