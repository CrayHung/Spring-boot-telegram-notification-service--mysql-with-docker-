// src/main/java/com/example/alerts/persistence/entity/AlertRecord.java
package com.example.alerts.persistence.entity;

import com.example.alerts.web.dto.AlertType;
import com.fasterxml.jackson.databind.JsonNode;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.OffsetDateTime;

@Entity
@Table(name = "alert_record")
public class AlertRecord {

  public AlertRecord() {}

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false, length = 16)
  private AlertType type;

  @Column(nullable = false, length = 255)
  private String title;

  @Column(length = 255)
  private String service;

  @Column(length = 1024)
  private String link;

  @CreationTimestamp
  @Column(nullable = false)
  private OffsetDateTime createdAt;

  // MySQL JSON 欄位：Hibernate 6 可直接用 JsonNode
  @JdbcTypeCode(SqlTypes.JSON)
  @Column(columnDefinition = "json", nullable = false)
  private JsonNode rawPayload;

  // getters/setters

  public Long getId() { return id; }
  public void setId(Long id) { this.id = id; }

  public AlertType getType() { return type; }
  public void setType(AlertType type) { this.type = type; }

  public String getTitle() { return title; }
  public void setTitle(String title) { this.title = title; }

  public String getService() { return service; }
  public void setService(String service) { this.service = service; }

  public String getLink() { return link; }
  public void setLink(String link) { this.link = link; }

  public OffsetDateTime getCreatedAt() { return createdAt; }
  public void setCreatedAt(OffsetDateTime createdAt) { this.createdAt = createdAt; }

  public JsonNode getRawPayload() { return rawPayload; }
  public void setRawPayload(JsonNode raw) { this.rawPayload = raw; }
}
