// config/TelegramProperties.java
package com.example.alerts.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import java.util.List;

@Configuration
@ConfigurationProperties(prefix = "telegram")
public class TelegramProperties {
  private String botToken;
  private List<String> chatIds;
  private String parseMode = "HTML";
  private boolean disableWebPagePreview = true;
  private boolean disableNotification = false;
  private String apiUrl = "https://api.telegram.org";
  
  
  // getters/setters...
  public String getBotToken() {
    return this.botToken;
  }

  public void setBotToken(String botToken) {
    this.botToken = botToken;
  }



  public List<String> getChatIds() {
    return chatIds;
} 
public void setChatIds(List<String> chatIds) {
        this.chatIds = chatIds;
    }




  public String getParseMode() {
    return this.parseMode;
  }

  public void setParseMode(String parseMode) {
    this.parseMode = parseMode;
  }


  public Boolean getDisableWebPagePreview(){
    return this.disableWebPagePreview;

  }  
  public void setDisableWebPagePreview(Boolean disableWebPagePreview){
    this.disableWebPagePreview = disableWebPagePreview;
  }

  public Boolean getDisableNotification(){
    return this.disableNotification;

  }  
  public void setDisableNotification(Boolean disableNotification){
    this.disableNotification = disableNotification;
  }

  public String getApiUrl() {
    return this.parseMode;
  }

  public void setApiUrl(String apiUrl) {
    this.apiUrl = apiUrl;
  }

public boolean isDisableNotification() {
    return false;
}

public Object isDisableWebPagePreview() {
    return null;
}



}
