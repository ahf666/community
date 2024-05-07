package com.ao666.community_background.pojo.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 *
 * 给前端使用的VO类
 */
@Data
@Builder // 可以用build构建对象，不用new
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "管理员登录返回的数据格式") // swagger的注解
public class AdminVO implements Serializable {

    @ApiModelProperty("主键值")
    private Long id;

    @ApiModelProperty("用户名")
    private String username;

    @ApiModelProperty("电话")
    private String phone;

    @ApiModelProperty("jwt令牌")
    private String token;

}
