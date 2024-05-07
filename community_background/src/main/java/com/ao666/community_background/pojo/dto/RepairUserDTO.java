package com.ao666.community_background.pojo.dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.security.PrivateKey;

@Data
@ApiModel(description = "用户报修时传递的数据模型") // swagger的注解
public class RepairUserDTO {
    private Long userId;
    private Long houseId;
    private Long typeId;
    private String image;
    private String details;
}
