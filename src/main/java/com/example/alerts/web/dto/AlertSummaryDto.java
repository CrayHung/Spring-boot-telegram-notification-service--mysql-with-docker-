// src/main/java/com/example/alerts/web/dto/AlertSummaryDto.java
package com.example.alerts.web.dto;

import java.time.OffsetDateTime;

public class AlertSummaryDto {
  public Long id;
  public String type;          // WARNING / REPAIR
  public String title;
  public String service;
  public String link;
  public OffsetDateTime createdAt;
  public Integer deviceCount;
  public Integer offlineCount;
}
