package com.ao666.community_background.pojo.dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel(description = "用户报修查询时传递的数据模型") // swagger的注解
public class RepairUserPageDTO {
    private int page;
    private int pageSize;
    private Long userId;
}
