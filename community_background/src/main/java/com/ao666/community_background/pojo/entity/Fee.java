package com.ao666.community_background.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 缴费类
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("tb_fee")
public class Fee {
    private Long id;
    private Long userId;
    private Long typeId;
    private Long houseId;
    private Long stopId;
    private BigDecimal amount;
    private int status;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private String createUser;
    private String updateUser;
}
