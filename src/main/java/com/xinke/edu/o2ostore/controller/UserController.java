package com.xinke.edu.o2ostore.controller;

import com.xinke.edu.o2ostore.core.RestResponse;
import com.xinke.edu.o2ostore.dto.user.UserDto;
import com.xinke.edu.o2ostore.model.User;
import com.xinke.edu.o2ostore.service.impl.UserService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @Description
 * @Author lqq
 * @Date 2023/02/26 20:36
 */

@RestController
@RequestMapping("/api")
public class UserController {

    @Resource
    private UserService userService;

    @GetMapping("/getUsers")
    public RestResponse<List<User>> getUsers() {
        return userService.getAllUsers();
    }

    @PostMapping("/login")
    public RestResponse<String> login(@Validated @RequestBody User user) {
        return userService.login(user);
    }

    @PostMapping("/register")
    public RestResponse<String> register(@Validated @RequestBody User user) {
        return userService.register(user);
    }

    @GetMapping("/code")
    public RestResponse<String> code(@Validated @RequestParam String email, HttpSession session) {
        return userService.code(email,session);
    }
    @PostMapping("/retrieve")
    public RestResponse<String> retrieve(@Validated @RequestBody UserDto dto,HttpSession session){
        return userService.retrieve(dto,session);
    }
    @PostMapping("/modify")
    public RestResponse<String> modify(@Validated @RequestBody UserDto dto){
        return userService.modify(dto);
    }
}
