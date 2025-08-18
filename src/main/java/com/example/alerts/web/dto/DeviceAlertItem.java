// web/dto/DeviceAlertItem.java
package com.example.alerts.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class DeviceAlertItem {
  @NotBlank private String type;         // e.g. lowBattery, shutdown
  @NotBlank private String message;      // e.g. 電量低於 20%
  
  // 允許 null/空字串，Controller 會用 now() 補
  private String timestamp;    
  // getters/setters...

  public String getType() {
    return type;
}

public void setType(String type) {
    this.type = type;
}


public String getMessage() {
    return message;
}

public void setMessage(String message) {
    this.message = message;
}


public String getTimestamp() {
    return timestamp;
}

public void setTimestamp(String timestamp) {
    this.timestamp = timestamp;
}

}
