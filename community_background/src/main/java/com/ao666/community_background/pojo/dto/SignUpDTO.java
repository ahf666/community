package com.ao666.community_background.pojo.dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel(description = "用户注册时传递的数据模型") // swagger的注解
public class SignUpDTO {
    private String username;
    private String password;
    private String phone;
    private int sex;
}
