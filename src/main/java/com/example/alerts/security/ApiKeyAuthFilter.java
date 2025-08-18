// src/main/java/com/example/alerts/security/ApiKeyAuthFilter.java
package com.example.alerts.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class ApiKeyAuthFilter extends OncePerRequestFilter {
  private final String requiredApiKey;

  public ApiKeyAuthFilter(String requiredApiKey) {
    this.requiredApiKey = requiredApiKey;
  }

  @Override
  protected boolean shouldNotFilter(HttpServletRequest request) {
    String path = request.getRequestURI();
    // 放行健康檢查/actuator
    return path.startsWith("/actuator") || "/healthz".equals(path);
  }

  @Override
  protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
      throws ServletException, IOException {

    if (!StringUtils.hasText(requiredApiKey)) {
      res.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Server API key not configured");
      return;
    }
    String key = req.getHeader("X-API-KEY");
    if (!requiredApiKey.equals(key)) {
      res.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid API key");
      return;
    }
    chain.doFilter(req, res);
  }
}
