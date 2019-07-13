package com.czbank.coding.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.czbank.coding.api.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {


}
//泛型指定的就是当前Mapper接口所操作的实体类类型