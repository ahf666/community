package com.ao666.community_background.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 报修信息类
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("tb_repair")
public class Repair {
    private Long id;
    private Long userId;
    private Long typeId;
    private Long houseId;
    private int status;
    private String image;
    private String details;
}
