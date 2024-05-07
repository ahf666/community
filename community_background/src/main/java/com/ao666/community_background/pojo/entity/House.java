package com.ao666.community_background.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 房屋类
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("tb_house")
public class House {
    private Long id;
    private int building;
    private int cell;
    private int floor;
    private String doorplate;
    private int status;
    private Long userId;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private String createUser;
    private String updateUser;
}
