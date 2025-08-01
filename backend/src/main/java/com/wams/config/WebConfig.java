package com.wams.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // Allow all paths
                .allowedOrigins("http://localhost:3000") // React dev server
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(true); // If you're using cookies or auth
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Optional: serve static files from /uploads/ or similar
        registry.addResourceHandler("/uploads/**")
                .addResourceLocations("file:uploads/");
    }
}
