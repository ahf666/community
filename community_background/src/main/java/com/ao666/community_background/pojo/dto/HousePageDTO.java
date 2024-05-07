package com.ao666.community_background.pojo.dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel(description = "房屋分页查询时传递的数据模型") // swagger的注解
public class HousePageDTO {
    private int page;
    private int pageSize;
    private String name; // 所属用户的名字
    private int building; // 楼栋号
    private int cell; // 单元
    private int floor; // 楼层
    private int status; // 出售状态
}
