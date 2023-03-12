package com.xinke.edu.o2ostore.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xinke.edu.o2ostore.core.RestResponse;
import com.xinke.edu.o2ostore.dto.user.UserDto;
import com.xinke.edu.o2ostore.mapper.UserMapper;
import com.xinke.edu.o2ostore.model.User;
import com.xinke.edu.o2ostore.service.IUserService;
import com.xinke.edu.o2ostore.util.MailHelper;
import com.xinke.edu.o2ostore.util.MyTokenUtil;
import com.xinke.edu.o2ostore.util.ThreadFactoryBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.servlet.http.HttpSession;
import java.security.GeneralSecurityException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @Description
 * @Author lqq
 * @Date 2023/02/26 20:25
 */
@Slf4j
@Service
public class UserService implements IUserService {
    @Resource
    private UserMapper userMapper;

    @Resource
    ThreadFactoryBuilder threadFactoryBuilder;
    static MailHelper helper;
    static {
        try {
            helper = new MailHelper("smtp.qq.com", "2574807195@qq.com", "mpzghvbhybylebjf");
        } catch (GeneralSecurityException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public RestResponse<List<User>> getAllUsers() {
        List<User> User = userMapper.selectAllUser();
        return RestResponse.ok(User);
    }

    @Override
    public RestResponse<String> login(User user) {
        User user1 = null;
        String token = null;
        if (StringUtils.hasLength(user.getPassword()) && StringUtils.hasLength(user.getAccount())) {
            QueryWrapper<User> wrapper = new QueryWrapper<>();
            wrapper.eq("account", user.getAccount());
            wrapper.eq("password", user.getPassword());
            user1 = userMapper.selectOne(wrapper);
            log.info(user1.toString());
            token = MyTokenUtil.sign(user1);
        }
        return user1 != null ? RestResponse.ok(token, "登录成功") : RestResponse.failed("登录失败,请检查账号密码是否正确");
    }

    @Override
    public RestResponse<String> register(User user) {

        int insert = 0;
        Map<String, Object> queryMap = new HashMap<>(1);
        if(!StringUtils.hasLength(user.getAccount()) || !StringUtils.hasLength(user.getPassword()) || !StringUtils.hasLength(user.getEmail())){
                return RestResponse.failed("注册失败,请填写必填字段");
        }
        queryMap.put("email", user.getEmail());
        List<User> users = userMapper.selectByMap(queryMap);
        if (users.size() < 1) {
            insert = userMapper.insert(user);
        }
        return insert > 0 ? RestResponse.ok("注册成功") : RestResponse.failed("注册失败,该邮箱已被注册");
    }


    @Override
    public RestResponse<String> code(String email,HttpSession session) {
        if(!StringUtils.hasLength(email)){
            return RestResponse.failed("邮箱不能为空!");
        }
        Map<String, Object> queryMap = new HashMap<>(1);
        queryMap.put("email", email);
        List<User> users = userMapper.selectByMap(queryMap);
        if (users.size() < 1) {
            return RestResponse.failed("邮箱未被注册！无法找回密码,请使用已注册邮箱或重新注册");
        }
        ThreadPoolExecutor executor = threadFactoryBuilder.newThread();
        String code = String.format("%06d", (int) (Math.random() * 1000000));
        session.setAttribute("code", code);
        session.setMaxInactiveInterval(60);
        executor.execute(() -> {
            try {
                helper.sendMail(email, "[o2o零售店]服务", "找回密码,您的验证码为："+ code);
            } catch (MessagingException e) {
                throw new RuntimeException(e);
            }
        });
        return RestResponse.ok("验证码已发送，请查收！");
    }

    @Override
    public RestResponse<String> retrieve(UserDto dto,HttpSession session) {
        if(!StringUtils.hasLength(dto.getEmail())&&!StringUtils.hasLength(dto.getCode())){
            return RestResponse.failed("邮箱或验证码不能为空!");
        }else if(!StringUtils.hasLength(dto.getPassword())){
            return RestResponse.failed("密码不能为空!");
        }
        String sessionCode = (String) session.getAttribute("code");
        if(!StringUtils.hasLength(sessionCode)){
            return RestResponse.failed("验证码已过期请重新获取!");
        }
        String code = dto.getCode();
        if(!sessionCode.equals(code)){
            return RestResponse.failed("验证码不正确,请重新输入!");
        }
        String email = dto.getEmail();
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("email",email);
        User model = userMapper.selectOne(wrapper);
        if(model==null){
            return RestResponse.failed("邮箱未注册!");
        }
        model.setPassword(dto.getPassword());
        userMapper.updateById(model);
        session.setAttribute("code",null);
        return RestResponse.ok("修改成功!");
    }

    @Override
    public RestResponse<String> modify(UserDto dto) {
        if(!StringUtils.hasLength(dto.getPassword())){
            return RestResponse.failed("密码不能为空!");
        }
        User user = userMapper.selectById(dto.getId());
        user.setPassword(dto.getPassword());
        userMapper.updateById(user);
        return RestResponse.ok("修改成功!");
    }
}
