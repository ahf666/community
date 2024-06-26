package com.ao666.community_background;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@MapperScan("com.ao666.community_background.server.mapper")
@EnableTransactionManagement //开启注解方式的事务管理
@EnableCaching // 开启缓存
@Slf4j
@EnableScheduling // 开启任务调度
public class CommunityBackgroundApplication {

    public static void main(String[] args) {
        SpringApplication.run(CommunityBackgroundApplication.class, args);
    }

}
