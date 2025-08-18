// src/main/java/com/example/alerts/web/dto/DeviceAlertItemDto.java
package com.example.alerts.web.dto;

import java.time.OffsetDateTime;

public class DeviceAlertItemDto {
  public Long id;
  public String type;
  public String message;
  public OffsetDateTime ts;
}
