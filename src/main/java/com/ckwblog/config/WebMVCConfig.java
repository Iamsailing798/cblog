package com.ckwblog.config;

import com.ckwblog.handler.AdminInterceptor;
import com.ckwblog.handler.LoginInterceptor;
import org.apache.logging.log4j.core.appender.db.AbstractDatabaseAppender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
class WebMVCConfig implements WebMvcConfigurer{

    @Autowired
    private LoginInterceptor loginInterceptor;

    @Autowired
    private AdminInterceptor adminInterceptor;

    public void addCorsMappings(CorsRegistry registry)
    {
        //跨域配置
        registry.addMapping("/**").allowedOrigins("https://blog.mszlu.com","http://blog1.mszlu.com","http://localhost:8080");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //拦截test接口，后续实际遇到需要拦截的接口时，在配置为真正的拦截接口
        registry.addInterceptor(loginInterceptor)
                .addPathPatterns("/test")
                .addPathPatterns("/blogList")
                .addPathPatterns("/type")
                .addPathPatterns("/admin")
                .addPathPatterns("/comments/create/change")
                .addPathPatterns("/articles/publish");//接口加入拦截器

        registry.addInterceptor(adminInterceptor)
                .addPathPatterns("/articles/publish")
                .addPathPatterns("/blogList")
                .addPathPatterns("/categorys/list")
                .addPathPatterns("/tags/list")
                .addPathPatterns("/comments/commentList");
    }
}

