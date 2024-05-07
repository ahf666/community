package com.ao666.community_background.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("tb_complain")
public class Complain implements Serializable {

    private Long id;
    private Long userId;
    private String details;
    private int status;
}
