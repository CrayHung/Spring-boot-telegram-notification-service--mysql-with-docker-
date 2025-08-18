package com.example.alerts.web.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import java.util.List;

public class DeviceReport {
  @NotBlank
  private String deviceId;

  @NotBlank
  private String status;              // online/offline

  @Valid
  private List<DeviceAlertItem> alerts; 

  // ===== getters / setters =====
  public String getDeviceId() { return deviceId; }
  public void setDeviceId(String deviceId) { this.deviceId = deviceId; }

  public String getStatus() { return status; }
  public void setStatus(String status) { this.status = status; }

  public List<DeviceAlertItem> getAlerts() { return alerts; }
  public void setAlerts(List<DeviceAlertItem> alerts) { this.alerts = alerts; }
}
