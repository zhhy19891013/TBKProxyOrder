package com.yj;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@MapperScan("com.yj.dao")
@EnableScheduling
public class StartUpApplication {
	/**
	 * 启动类
	 * @param args
	 */
	public static void main(String[] args) {
		SpringApplication.run(StartUpApplication.class, args);
	}

}
