// src/main/java/com/example/alerts/telegram/TelegramClient.java
package com.example.alerts.telegram;

import com.example.alerts.config.TelegramProperties;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Component
public class TelegramClient {

  private final RestTemplate rt;
  private final String baseUrl;

  public TelegramClient(RestTemplate restTemplate, TelegramProperties props) {
    this.rt = restTemplate;
    this.baseUrl = "https://api.telegram.org/bot" + props.getBotToken();
  }

  /**
   * 使用 form-urlencoded 呼叫 Telegram /sendMessage
   * - 失敗時丟出 RuntimeException，訊息會包含 Telegram 的 description 或 HTTP response body
   */
  public void sendMessage(String chatId, String html, boolean silent) {
    String url = baseUrl + "/sendMessage";

    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

    MultiValueMap<String, String> form = new LinkedMultiValueMap<>();
    form.add("chat_id", chatId);
    form.add("text", html);
    form.add("parse_mode", "HTML");
    form.add("disable_web_page_preview", "true");
    form.add("disable_notification", silent ? "true" : "false");

    HttpEntity<MultiValueMap<String, String>> req = new HttpEntity<>(form, headers);

    try {
      ResponseEntity<String> resp = rt.postForEntity(url, req, String.class);

      // Telegram 的 body 也要看 ok 欄位
      String body = resp.getBody() == null ? "" : resp.getBody();
      if (!resp.getStatusCode().is2xxSuccessful() || !body.contains("\"ok\":true")) {
        throw new RuntimeException("Telegram returned non-ok: HTTP "
            + resp.getStatusCodeValue() + " body=" + body);
      }

    } catch (HttpStatusCodeException e) {
      // 可以取到 response body（通常含 description）
      String body = e.getResponseBodyAsString();
      throw new RuntimeException("HTTP " + e.getStatusCode().value()
          + " from Telegram: " + body, e);
    } catch (RestClientException e) {
      throw new RuntimeException("HTTP error calling Telegram: " + e.getMessage(), e);
    }
  }
}
