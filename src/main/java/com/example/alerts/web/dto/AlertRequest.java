package com.example.alerts.web.dto;

import com.fasterxml.jackson.databind.JsonNode;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public class AlertRequest {

  @NotNull
  private AlertType type;                 // WARNING / REPAIR

  @NotBlank
  private String title;                   // 例如：DB 連線異常

  @NotNull
  @Valid
  private List<DeviceReport> message;     // 你給的 message[] 陣列

  private String service;                 // 可選
  private String link;                    // 可選
  private Boolean silent;                 // 可選

  // 由後端塞原始 JSON（不從請求反序列化）
  private JsonNode raw;

  // ===== getters / setters =====
  public AlertType getType() { return type; }
  public void setType(AlertType type) { this.type = type; }

  public String getTitle() { return title; }
  public void setTitle(String title) { this.title = title; }

  public List<DeviceReport> getMessage() { return message; }
  public void setMessage(List<DeviceReport> message) { this.message = message; }

  public String getService() { return service; }
  public void setService(String service) { this.service = service; }

  public String getLink() { return link; }
  public void setLink(String link) { this.link = link; }

  public Boolean getSilent() { return silent; }
  public void setSilent(Boolean silent) { this.silent = silent; }

  public JsonNode getRaw() { return raw; }
  public void setRaw(JsonNode raw) { this.raw = raw; }
}
