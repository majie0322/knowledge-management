package com.management;

import com.management.interceptor.Interceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;

/**
 * 启动类
 */
@SpringBootApplication
@MapperScan("com.management.dao")
public class App {
	public static void main(String[] args) {
		SpringApplication.run(App.class, args);
	}

	/**
	 * 拦截器
	 * @param registry
	 */
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new Interceptor()).addPathPatterns("/neo/**");
	}
}
