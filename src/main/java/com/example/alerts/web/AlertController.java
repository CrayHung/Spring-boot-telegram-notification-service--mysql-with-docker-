// src/main/java/com/example/alerts/web/AlertController.java
package com.example.alerts.web;

import com.example.alerts.persistence.entity.AlertRecord;
import com.example.alerts.persistence.entity.DeviceAlertItemEntity;
import com.example.alerts.persistence.entity.DeviceReportEntity;
import com.example.alerts.persistence.repo.AlertRecordRepo;
import com.example.alerts.persistence.repo.DeviceAlertItemRepo;
import com.example.alerts.persistence.repo.DeviceReportRepo;
import com.example.alerts.service.TelegramService;
import com.example.alerts.web.dto.AlertRequest;
import com.example.alerts.web.dto.BroadcastResult;
import com.example.alerts.web.dto.DeviceAlertItem;
import com.example.alerts.web.dto.DeviceReport;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.OffsetDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/alerts")
public class AlertController {

  private final TelegramService telegramService;
  private final AlertRecordRepo alertRecordRepo;
  private final DeviceReportRepo deviceReportRepo;
  private final DeviceAlertItemRepo deviceAlertItemRepo;
  private final ObjectMapper objectMapper;

  public AlertController(TelegramService telegramService,
      AlertRecordRepo alertRecordRepo,
      DeviceReportRepo deviceReportRepo,
      DeviceAlertItemRepo deviceAlertItemRepo,
      ObjectMapper objectMapper) {
    this.telegramService = telegramService;
    this.alertRecordRepo = alertRecordRepo;
    this.deviceReportRepo = deviceReportRepo;
    this.deviceAlertItemRepo = deviceAlertItemRepo;
    this.objectMapper = objectMapper;
  }

  // 查詢全部告警
  @GetMapping("/list")
  public ResponseEntity<List<DeviceAlertItemEntity>> listAll() {
    List<DeviceAlertItemEntity> items = deviceAlertItemRepo.findAll();
    return ResponseEntity.ok(items);
  }

  // 依照 deviceReportId 查
  @GetMapping("/list/{reportId}")
  public ResponseEntity<List<DeviceAlertItemEntity>> listByReportId(@PathVariable Long reportId) {
    List<DeviceAlertItemEntity> items = deviceAlertItemRepo.findByDeviceReportId(reportId);
    return ResponseEntity.ok(items);
  }

  @PostMapping(path = "/send", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> send(@Valid @RequestBody AlertRequest req) {
    try {
      // 1) 保存原始 JSON
      JsonNode raw = objectMapper.valueToTree(req);

      // 2) alert_record
      AlertRecord rec = new AlertRecord();
      rec.setType(req.getType());
      rec.setTitle(req.getTitle());
      rec.setService(req.getService());
      rec.setLink(req.getLink());
      rec.setRawPayload(raw);
      AlertRecord saved = alertRecordRepo.save(rec);

      // 3) device_report + device_alert_item
      List<DeviceReport> reports = req.getMessage();
      if (reports != null) {
        for (DeviceReport d : reports) {
          DeviceReportEntity de = new DeviceReportEntity();
          de.setAlertRecordId(saved.getId());
          de.setDeviceId(d.getDeviceId());
          de.setStatus(d.getStatus());
          int count = (d.getAlerts() == null) ? 0 : d.getAlerts().size();
          de.setAlertsCount(count);
          DeviceReportEntity deSaved = deviceReportRepo.save(de);

          if (count > 0) {
            for (DeviceAlertItem it : d.getAlerts()) {
              DeviceAlertItemEntity e = new DeviceAlertItemEntity();
              e.setDeviceReportId(deSaved.getId());
              e.setType(it.getType());
              e.setMessage(it.getMessage());
              // ✅ 無論傳入是 null/空/格式錯，全部補 now()
              e.setTs(safeParseIsoOffset(it.getTimestamp()));
              deviceAlertItemRepo.save(e);
            }
          }
        }
      }

      // 4) 廣播 Telegram
      Map<String, String> perChat = telegramService.broadcast(req);
      String status = perChat.values().stream().allMatch("ok"::equals) ? "OK"
          : perChat.values().stream().anyMatch("ok"::equals) ? "PARTIAL" : "ERROR";

      return ResponseEntity.ok(new BroadcastResult(status, new LinkedHashMap<>(perChat)));

    } catch (Exception ex) {
      ex.printStackTrace();
      return ResponseEntity.internalServerError().body(Map.of("error", ex.getMessage()));
    }
  }

  /** 任何非標準 ISO-8601（或 null/空字串），一律 fallback 成現在時間 */
  private OffsetDateTime safeParseIsoOffset(String s) {
    try {
      if (s == null || s.isBlank())
        return OffsetDateTime.now();
      return OffsetDateTime.parse(s); // e.g. 2024-08-12T10:00:00Z / +08:00
    } catch (Exception ignore) {
      return OffsetDateTime.now();
    }
  }
}
