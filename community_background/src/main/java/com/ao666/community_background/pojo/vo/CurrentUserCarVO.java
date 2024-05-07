package com.ao666.community_background.pojo.vo;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@ApiModel("当前车辆VO")
public class CurrentUserCarVO {
    private String carLicense;
    private Long userId;
    private LocalDateTime entryTime;
}
