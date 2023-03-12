package com.xinke.edu.o2ostore.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xinke.edu.o2ostore.model.User;

import java.util.List;

/**
 * @Description
 * @Author lqq
 * @Date 2023/02/26 20:16
 */
public interface UserMapper extends BaseMapper<User> {
    List<User> selectAllUser();

}
