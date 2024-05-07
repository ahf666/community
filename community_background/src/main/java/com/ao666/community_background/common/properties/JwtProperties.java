package com.ao666.community_background.common.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "community.jwt") // springboot的注解，表示当前类是一个配置属性类，加载yml中的community:jwt
@Data
public class JwtProperties {

    /**
     * 管理端员工生成jwt令牌相关配置,在application.yml里面
     */
    private String adminSecretKey;
    private long adminTtl;
    private String adminTokenName;

    /**
     * 用户端生成jwt令牌相关配置
     */
    private String userSecretKey;
    private long userTtl;
    private String userTokenName;

}
