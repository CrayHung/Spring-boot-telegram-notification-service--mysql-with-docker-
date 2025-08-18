// src/main/java/com/example/alerts/persistence/entity/DeviceAlertItemEntity.java
package com.example.alerts.persistence.entity;

import jakarta.persistence.*;
import java.time.OffsetDateTime;

@Entity
@Table(name = "device_alert_item",
  indexes = {
    @Index(name = "idx_item_report", columnList = "device_report_id"),
    @Index(name = "idx_item_type", columnList = "type")
})
public class DeviceAlertItemEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "device_report_id", nullable = false)
  private Long deviceReportId;

  @Column(nullable = false, length = 64)
  private String type;

  @Column(nullable = false, length = 512)
  private String message;

  @Column(name = "ts", nullable = false)
  private OffsetDateTime ts;

  // ===== getters/setters =====

  public Long getId() { return id; }
  public void setId(Long id) { this.id = id; }

  public Long getDeviceReportId() { return deviceReportId; }
  public void setDeviceReportId(Long deviceReportId) { this.deviceReportId = deviceReportId; }

  public String getType() { return type; }
  public void setType(String type) { this.type = type; }

  public String getMessage() { return message; }
  public void setMessage(String message) { this.message = message; }

  public OffsetDateTime getTs() { return ts; }
  public void setTs(OffsetDateTime ts) { this.ts = ts; }

  /** ✅ 最後防線：入庫前若 ts 為 null，自動補上現在時間 */
  @PrePersist
  public void prePersist() {
    if (this.ts == null) {
      this.ts = OffsetDateTime.now();
    }
  }
}
