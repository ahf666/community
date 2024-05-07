package com.ao666.community_background.pojo.dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel(description = "当前车辆分页查询时传递的数据模型") // swagger的注解
public class CurrentCarDTO {
    private int page;
    private int pageSize;
}
