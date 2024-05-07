package com.ao666.community_background.pojo.dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel(description = "管理员修改用户状态传递的数据模型") // swagger的注解
public class UserStatusDTO {
    private Long id;
    private int status;
}
