package com.ao666.community_background.pojo.vo;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder // 可以用build构建对象，不用new
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "用户房屋信息返回的数据格式") // swagger的注解
public class HouseVO {
    private Long id;
    private int building;
    private int cell;
    private int floor;
    private String doorplate;
}
