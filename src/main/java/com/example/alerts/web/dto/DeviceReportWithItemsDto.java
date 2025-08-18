// src/main/java/com/example/alerts/web/dto/DeviceReportWithItemsDto.java
package com.example.alerts.web.dto;

import java.util.List;

public class DeviceReportWithItemsDto {
  public Long id;
  public String deviceId;
  public String status;
  public Integer alertsCount;
  public List<DeviceAlertItemDto> alerts;
}
