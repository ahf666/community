package com.ao666.community_background.pojo.vo;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder // 可以用build构建对象，不用new
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "缴费-房屋详情查询返回的数据格式") // swagger的注解
public class DetailsHouseVO {
    // 楼栋信息
    private int building;
    private int cell;
    private int floor;
    private String doorplate;
}
