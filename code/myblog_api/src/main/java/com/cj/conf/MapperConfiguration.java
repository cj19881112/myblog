package com.cj.conf;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * 分离出mapper的配置，否则controller的单元测试会报错，需要sqlSessionFactory
 *
 */
@Configuration
@MapperScan("com.cj.*.dao")
public class MapperConfiguration {
}
