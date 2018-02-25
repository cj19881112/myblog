package com.cj.conf;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan("com.cj.*.dao")
public class MyConfiguration {
}
