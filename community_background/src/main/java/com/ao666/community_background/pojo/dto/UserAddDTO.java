package com.ao666.community_background.pojo.dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel(description = "添加用户的数据模型") // swagger的注解
public class UserAddDTO {
    private String name;
    private String phone;
    private int sex;
    private int building;
    private int cell;
    private int floor;
    private String doorplate;
}
