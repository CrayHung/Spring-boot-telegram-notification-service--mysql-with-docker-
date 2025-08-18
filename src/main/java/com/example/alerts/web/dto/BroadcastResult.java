package com.example.alerts.web.dto;

import java.util.Map;

public class BroadcastResult {

    private String status;              
    private Map<String, String> perChat;


    public BroadcastResult(String status, Map<String, String> perChat) {
        this.status = status;
        this.perChat = perChat;
      }
      

    // Getter / Setter
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Map<String, String> getPerChat() {
        return perChat;
    }

    public void setPerChat(Map<String, String> perChat) {
        this.perChat = perChat;
    }
}
