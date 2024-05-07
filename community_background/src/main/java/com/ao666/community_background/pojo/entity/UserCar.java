package com.ao666.community_background.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 登记用户的车辆表,区别外来车辆
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("tb_user_car")
public class UserCar {
    @TableId(type = IdType.AUTO) // 插入时可以自增类型的主键返回
    private Long id;
    private String carLicense;
    private Long userId;
}
