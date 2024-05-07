package com.ao666.community_background.pojo.dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel(description = "用户租借车位传递的数据模型") // swagger的注解
public class BuyStopDTO {
    private Long id; // 车位id
    private Long userId;
    private String carLicense;
    private int status;
}
