package vn.hoidanit.jobhunter.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class StaticResourcesWebConfiguration
        implements WebMvcConfigurer {
    @Value("${file.upload-file.base-path}")
    private String basePath;
    //
    //thay the' "/storage/** = /basePath/** -> storage == vs basePath"
    //vd: file:///C:/workplace/backend/java/spring-rest-hoidanit/source/upload/hoidanit.pdf
    //=> storage = file:///C:/workplace/backend/java/spring-rest-hoidanit/source/upload/
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/storage/**")
                .addResourceLocations(basePath);
    }
}