package org.example.linfinityfirst.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    // ⚠️ 이 경로를 실제 이미지가 저장된 폴더 경로로 정확히 지정해야 합니다. (Windows 경로 예시)
    private final String UPLOAD_PATH = "file:///C:/linfinity_shop/images/";

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 브라우저가 '/images/' 경로로 요청하면,
        registry.addResourceHandler("/images/**")
                // 실제 서버의 로컬 폴더에서 파일을 찾도록 매핑합니다.
                .addResourceLocations(UPLOAD_PATH);
    }
}