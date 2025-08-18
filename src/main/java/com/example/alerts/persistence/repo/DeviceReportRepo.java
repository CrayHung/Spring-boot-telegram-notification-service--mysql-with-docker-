// src/main/java/com/example/alerts/persistence/repo/DeviceReportRepo.java
package com.example.alerts.persistence.repo;

import com.example.alerts.persistence.entity.DeviceReportEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DeviceReportRepo extends JpaRepository<DeviceReportEntity, Long> {
  List<DeviceReportEntity> findByAlertRecordId(Long alertRecordId);
}
