package com.czbank.coding.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.czbank.coding.api.Address;
import com.czbank.coding.user.mapper.AddressMapper;
import com.czbank.coding.user.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import javax.annotation.Resource;

@Service
public class UserServiceImpl implements UserService {

    @Resource
    private AddressMapper addressMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateFirst(Address address) {
        QueryWrapper<Address> qw = new QueryWrapper<>();
        qw.eq("first", 1);
        Address a = addressMapper.selectOne(qw);
        a.setFirst(0);
        if (addressMapper.updateById(a) == 0) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return 0;
        }
        if (addressMapper.updateById(address) == 0) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return 0;
        }
        return 1;
    }
}
