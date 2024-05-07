package com.ao666.community_background.pojo.dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel(description = "车位分页查询时传递的数据模型") // swagger的注解
public class StopPageDTO {
    private int page;
    private int pageSize;
    private String place; // 地区
    private int status; // 是否已出租
    private String name; // 所属用户的名字
    private String carLicense; // 车牌号
}
