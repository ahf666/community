package com.ao666.community_background.pojo.vo;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder // 可以用build构建对象，不用new
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "用户缴费分页查询返回的数据格式") // swagger的注解
public class FeeUserPageVO {
    private Long id;
    private Long typeId; // 前端传枚举
    private Long houseId;
    private Long stopId;
    private BigDecimal amount; // 费用
    private int status;
}
