package com.ao666.community_background.pojo.dto;


import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel(description = "用户分页查询时传递的数据模型") // swagger的注解
public class UserCarPageDTO {
    private int page;
    private int pageSize;
    private String name;
    private String carLicense;
}
