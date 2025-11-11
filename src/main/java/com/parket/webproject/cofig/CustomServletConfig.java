package com.parket.webproject.cofig;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CustomServletConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // CSS
        registry.addResourceHandler("/css/**")
                .addResourceLocations("classpath:/static/css/");
        // JS
        registry.addResourceHandler("/js/**")
                .addResourceLocations("classpath:/static/js/");
        // IMG
        registry.addResourceHandler("/img/**")
                .addResourceLocations(
                        "classpath:/static/img/",
                        "classpath:/templates/img/");;
    }
}