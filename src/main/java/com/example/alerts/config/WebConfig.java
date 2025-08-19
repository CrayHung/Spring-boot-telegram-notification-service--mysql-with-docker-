// src/main/java/com/example/alerts/config/WebConfig.java
package com.example.alerts.config;

import com.example.alerts.security.ApiKeyAuthFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

@Configuration
public class WebConfig {

  // 用方法參數注入，並給預設值，避免屬性讀不到就炸掉
  @Bean
  public FilterRegistrationBean<ApiKeyAuthFilter> apiKeyAuthFilter(
      @Value("${security.api-key:alert-key}") String apiKey) {

    FilterRegistrationBean<ApiKeyAuthFilter> reg = new FilterRegistrationBean<>();
    reg.setFilter(new ApiKeyAuthFilter(apiKey));
    reg.addUrlPatterns("/*"); // 套全域
    reg.setOrder(1); // 早於其它 Filter
    return reg;
  }

  @Bean
  public RestTemplate restTemplate(RestTemplateBuilder b) {
    return b
        .setConnectTimeout(Duration.ofSeconds(5))
        .setReadTimeout(Duration.ofSeconds(10))
        .build();
  }
}
