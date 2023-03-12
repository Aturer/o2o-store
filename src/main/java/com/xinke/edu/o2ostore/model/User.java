package com.xinke.edu.o2ostore.model;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * @Description
 * @Author lqq
 * @Date 2023/02/26 20:13
 */
@Data
@TableName("User")
public class User implements Serializable {
    @TableId(type = IdType.AUTO)
    private Integer id;

    private String account;

    private String password;

    private String email;

}
