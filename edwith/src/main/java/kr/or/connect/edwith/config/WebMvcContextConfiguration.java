package kr.or.connect.edwith.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import kr.or.connect.edwith.interceptor.LogInterceptor;
import kr.or.connect.edwith.interceptor.LoginInterceptor;

/*
 * EnableWebMvc : 요청또는 응답 객체에 대한 자동 JSON 변환 처리.(MessageConvertor)
 * */

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = { "kr.or.connect.edwith.controller" })
public class WebMvcContextConfiguration extends WebMvcConfigurerAdapter{
	/*
	 * 리소스 경로 등록
	 * */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/css/**").addResourceLocations("/css/").setCachePeriod(31556926);
        registry.addResourceHandler("/img/**").addResourceLocations("/img/").setCachePeriod(31556926);
        registry.addResourceHandler("/js/**").addResourceLocations("/js/").setCachePeriod(31556926);
        registry.addResourceHandler("/files/**").addResourceLocations("/files/").setCachePeriod(31556926);
        
    }
 
    // default servlet handler를 사용하게 합니다.
    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }
   
    @Override
    public void addViewControllers(final ViewControllerRegistry registry) {
    		System.out.println("addViewControllers가 호출됩니다. ");
        registry.addViewController("/").setViewName("main");
    }
    
	/*
	 * 뷰 리졸버 등록
	 * */
    @Bean
    public InternalResourceViewResolver getInternalResourceViewResolver() {
        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
        resolver.setPrefix("/WEB-INF/views/");
        resolver.setSuffix(".jsp");
        return resolver;
    }

	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
		super.addArgumentResolvers(argumentResolvers);
		
	}

	/*
	 * 멀티파트 리졸버 등록
	 * */
	 @Bean
	 public MultipartResolver multipartResolver() {
	        org.springframework.web.multipart.commons.CommonsMultipartResolver multipartResolver = new org.springframework.web.multipart.commons.CommonsMultipartResolver();
	        multipartResolver.setMaxUploadSize(10485760); // 1024 * 1024 * 10
	        return multipartResolver;
	 }
	/*
	 * 인터셉터 등록
	 * */	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new LogInterceptor());
		registry.addInterceptor(new LoginInterceptor()).addPathPatterns("/edwith/api/reservations/mypage");
		registry.addInterceptor(new LoginInterceptor()).addPathPatterns("/edwith/api/reservations/review");
		
	}
    
	
}