package com.ao666.community_background.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 停车位类
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("tb_stop")
public class Stop {
    private Long id;
    private String place;
    private int status;
    private Long userId;
    private String carLicense;
    private BigDecimal rentMonth;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private String createUser;
    private String updateUser;
}
