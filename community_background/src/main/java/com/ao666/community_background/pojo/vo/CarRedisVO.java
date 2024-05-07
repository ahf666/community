package com.ao666.community_background.pojo.vo;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder // 可以用build构建对象，不用new
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "Redis查车辆返回的数据格式") // swagger的注解
public class CarRedisVO {
    private List<Car> cars; // 分页查询的子集
    private int total; // 总数
}
