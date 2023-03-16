package heedoitdox.deliverysystem.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    private static final String MAPPING_PATH_PATTERN = "/api/**";
    private static final String[] allowedMethods =
        {"GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS"};

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping(MAPPING_PATH_PATTERN)
            .allowedMethods(allowedMethods);
    }
}
