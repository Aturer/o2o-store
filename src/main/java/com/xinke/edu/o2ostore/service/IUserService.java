package com.xinke.edu.o2ostore.service;

import com.xinke.edu.o2ostore.core.RestResponse;
import com.xinke.edu.o2ostore.dto.user.UserDto;
import com.xinke.edu.o2ostore.model.User;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @Description
 * @Author lqq
 * @Date 2023/02/26 20:26
 */
public interface IUserService {

    RestResponse<List<User>> getAllUsers();

    RestResponse<String> login(User user);

    RestResponse<String> register(User user);
    RestResponse<String> code(String email, HttpSession session);

    RestResponse<String> retrieve(UserDto dto,HttpSession session);
    RestResponse<String> modify(UserDto dto);
}
