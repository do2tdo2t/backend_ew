package kr.or.connect.edwith.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@ComponentScan(basePackages = { "kr.or.connect.edwith.dao",  "kr.or.connect.edwith.service"})
@Import({ DBConfig.class })
public class ApplicationConfig {

}
