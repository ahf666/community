package com.ao666.community_background.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("tb_user")
public class User {
    @TableId(type = IdType.AUTO) // 插入时可以自增类型的主键返回
    private Long id;
    private String username;
    private String password;
    private int status;
    private String name;
    private int sex;
    private String phone;

}
