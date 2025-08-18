// persistence/entity/DeviceReportEntity.java
package com.example.alerts.persistence.entity;

import jakarta.persistence.*;

@Entity @Table(name="device_report",
  indexes = {
    @Index(name="idx_device_record", columnList="alert_record_id"),
    @Index(name="idx_device_id", columnList="device_id")
})
public class DeviceReportEntity {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name="alert_record_id", nullable=false)
  private Long alertRecordId;

  @Column(name="device_id", nullable=false, length=64)
  private String deviceId;

  @Column(nullable=false, length=16)
  private String status; // online/offline

  @Column(name="alerts_count", nullable=false)
  private Integer alertsCount;

  // getters/setters...
  public Long getId() {
    return id;
}

public void setId(Long id) {
    this.id = id;
}
public Long getAlertRecordId() {
    return alertRecordId;
}

public void setAlertRecordId(Long alertRecordId) {
    this.alertRecordId = alertRecordId;
}

public String getDeviceId() {
    return deviceId;
}

public void setDeviceId(String deviceId) {
    this.deviceId = deviceId;
}

public String getStatus() {
    return status;
}

public void setStatus(String status) {
    this.status = status;
}

public Integer getAlertsCount() {
    return alertsCount;
}

public void setAlertsCount(Integer alertsCount) {
    this.alertsCount = alertsCount;
}



}
