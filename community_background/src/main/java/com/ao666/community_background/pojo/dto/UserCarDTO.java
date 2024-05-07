package com.ao666.community_background.pojo.dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel(description = "用户新增车辆信息时传递的数据模型") // swagger的注解
public class UserCarDTO {
    private Long userId;
    private String carLicense;
}
