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
 * 超过24小时的小区内车辆需要持久化到mysql数据库
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("tb_current_car")
public class CurrentCar {
    @TableId(type = IdType.AUTO) // 插入时可以自增类型的主键返回
    private Long id;
    private String carLicence;
    private Long userId;
    private LocalDateTime entryTime;
}
