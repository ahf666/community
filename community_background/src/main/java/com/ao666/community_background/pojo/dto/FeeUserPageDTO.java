package com.ao666.community_background.pojo.dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel(description = "用户缴费分页管理的数据模型") // swagger的注解
public class FeeUserPageDTO {
    private int page;
    private int pageSize;
    private Long userId;
}
