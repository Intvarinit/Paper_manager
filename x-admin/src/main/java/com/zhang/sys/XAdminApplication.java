package com.zhang.sys;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.CrossOrigin;

@SpringBootApplication
@MapperScan("com.zhang.*.mapper")
public class XAdminApplication {

	public static void main(String[] args) {
		SpringApplication.run(XAdminApplication.class, args);
	}

//	@Bean		//使用springSecurity会导致原有跨域注解失效
//	public PasswordEncoder passwordEncoder(){
//		return new BCryptPasswordEncoder();
//	}
}
