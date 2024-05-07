package com.ao666.community_background.pojo.dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel(description = "用户修改自身信息传递的数据模型") // swagger的注解
public class UserUpdateDTO {
    private Long id;
    private String password;
}
