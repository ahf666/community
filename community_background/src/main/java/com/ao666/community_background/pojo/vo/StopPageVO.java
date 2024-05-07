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
@ApiModel(description = "车位管理分页查询返回的数据格式") // swagger的注解
public class StopPageVO {
    private Long id;
    private String place;
    private int status;
    private String name; // 所属用户的姓名
    private String username; // 用户名
    private String phone; // 手机号
    private int sex; // 性别
    private String carLicense;
    private double rentMonth;
}
