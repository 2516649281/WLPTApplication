package com.chunfeng.config;

import com.chunfeng.interceptor.LoginInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 业务层配置类
 *
 * @author by 春风能解释
 * <p>
 * 2022/10/20
 */
@Configuration
@Slf4j
@MapperScan("com.chunfeng.mapper")
@EnableCaching
public class ServiceConfig implements WebMvcConfigurer {

    /**
     * 拦截器注入
     */
    @Autowired
    private LoginInterceptor loginInterceptor;

    /**
     * 拦截器配置
     *
     * @param registry 注册器
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns(
                        "/error",
                        "/user/login",
                        "/user/register",
                        "/node/**"
                );
        log.info("拦截器准备就绪!");
    }

    /**
     * 跨域配置
     *
     * @param registry 注册器
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("*")//为指定的路径模式启用跨域请求处理
                .allowedOrigins("*")//请求来源
                .allowedHeaders("*")//请求头
                .allowedMethods("*");//请求方法
        log.info("跨域准备就绪!");
    }
}
