// src/main/java/com/example/alerts/service/TelegramService.java
package com.example.alerts.service;

import com.example.alerts.config.TelegramProperties;
import com.example.alerts.telegram.TelegramClient;
import com.example.alerts.web.dto.AlertRequest;
import com.example.alerts.web.dto.AlertType;
import org.apache.commons.text.StringEscapeUtils;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@Service
public class TelegramService {

  private final TelegramClient client;
  private final TelegramProperties props;

  public TelegramService(TelegramClient client, TelegramProperties props) {
    this.client = client;
    this.props = props;
  }

  public Map<String, String> broadcast(AlertRequest req) {
    String html = buildHtml(req);
    Map<String, String> result = new LinkedHashMap<>();
    boolean silent = Boolean.TRUE.equals(req.getSilent());

    for (String chatId : props.getChatIds()) {
      try {
        client.sendMessage(chatId, html, silent);
        result.put(chatId, "ok");
      } catch (Exception ex) {
        result.put(chatId, "error: " + ex.getMessage());
      }
    }
    return result;
  }

  private String buildHtml(AlertRequest req) {
    String emoji = req.getType() == AlertType.WARNING ? "⚠️" : "🧯";
    String typeText = req.getType() == AlertType.WARNING ? "警告" : "錯誤維修";

    StringBuilder sb = new StringBuilder();
    // sb.append("<b>").append(emoji).append(" ").append(typeText).append("</b>\n");
    sb.append("<b>").append(" ").append(typeText).append("</b>\n");
    if (req.getService()!=null && !req.getService().isBlank())
      sb.append("<b>服務：</b>").append(escape(req.getService())).append("\n");
    sb.append("<b>標題：</b>").append(escape(req.getTitle())).append("\n");

    long offline = req.getMessage().stream().filter(d -> "offline".equalsIgnoreCase(d.getStatus())).count();
    sb.append("<b>裝置數：</b>").append(req.getMessage().size())
      .append("（offline: ").append(offline).append("）\n");

    int max = Math.min(5, req.getMessage().size());
    for (int i=0; i<max; i++) {
      var d = req.getMessage().get(i);
      sb.append("• <b>").append(escape(d.getDeviceId())).append("</b>：")
        .append(escape(d.getStatus()));
      if (d.getAlerts()!=null && !d.getAlerts().isEmpty()) {
        sb.append("，alerts=").append(d.getAlerts().size());
      }
      sb.append("\n");
    }
    if (req.getMessage().size() > 5) {
      sb.append("… 其餘 ").append(req.getMessage().size()-5).append(" 台略\n");
    }

    sb.append("<b>時間：</b>").append(OffsetDateTime.now());
    if (req.getLink()!=null && !req.getLink().isBlank()) {
      String url = escape(req.getLink());
      sb.append("\n 連結：<a href=\"").append(url).append("\">").append(url).append("</a>");
    }
    return sb.toString();
  }

  private String escape(String s) {
    return s == null ? "" : StringEscapeUtils.escapeHtml4(s);
  }
}
