package com.xunge.comm.job;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@ComponentScan(basePackages = { "com.xunge" })
@ImportResource(value = { "classpath:config/spring-thread.xml" })
@EnableScheduling
public class MultiThreadConfig {
}
