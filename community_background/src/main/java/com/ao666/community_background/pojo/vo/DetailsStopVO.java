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
@ApiModel(description = "缴费-车位详情查询返回的数据格式") // swagger的注解
public class DetailsStopVO {
    // 车位信息
    private String place;
}
