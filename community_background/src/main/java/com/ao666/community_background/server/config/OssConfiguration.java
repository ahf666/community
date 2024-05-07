package com.ao666.community_background.server.config;

import com.ao666.community_background.common.properties.AliOssProperties;
import com.ao666.community_background.common.util.AliOssUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * yml -> properties -> utils -> configuration
 */
@Configuration
@Slf4j
public class OssConfiguration {

    @Bean
    @ConditionalOnMissingBean // 当没有时才创建，保证ioc容器中这种bean只有一个
    public AliOssUtil aliOssUtil(AliOssProperties aliOssProperties){
        log.info("开始创建阿里云文件上传对象:{}", aliOssProperties);
        return AliOssUtil.builder()
                .endpoint(aliOssProperties.getEndpoint())
                .accessKeyId(aliOssProperties.getAccessKeyId())
                .accessKeySecret(aliOssProperties.getAccessKeySecret())
                .bucketName(aliOssProperties.getBucketName())
                .build();
    }
}
