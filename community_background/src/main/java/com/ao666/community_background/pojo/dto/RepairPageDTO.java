package com.ao666.community_background.pojo.dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel(description = "报修分页查询时传递的数据模型") // swagger的注解
public class RepairPageDTO {
    private int page;
    private int pageSize;
    private String name; // 发起报修的用户
    private Long typeId; // 维修类型Id
    private int status;
}
