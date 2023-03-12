package com.xinke.edu.o2ostore.dto.user;

import lombok.Data;

/**
 * @Description
 * @Author lqq
 * @Date 2023/03/12 17:10
 */
@Data
public class UserDto {
    private Integer id;

    private String account;

    private String password;

    private String email;

    private String code;
}
