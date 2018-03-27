package com.wukw.kindle.config;



import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan("com.wukw.kindle.repo.entity")
public class DbConfig {
}

