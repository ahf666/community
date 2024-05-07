package com.ao666.community_background.pojo.dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;


@Data
@ApiModel(description = "登录时传递的数据模型") // swagger的注解
public class LoginDTO {
    private String username;
    private String password;
}
