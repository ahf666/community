package com.ao666.community_background.pojo.dto;


import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel(description = "缴费分页查询时传递的数据模型") // swagger的注解
public class FeePageDTO {
    private int page;
    private int pageSize;
    private String name; // 所属姓名
    private Long typeId; // 类型
    private int status; // 是否已缴费
}
