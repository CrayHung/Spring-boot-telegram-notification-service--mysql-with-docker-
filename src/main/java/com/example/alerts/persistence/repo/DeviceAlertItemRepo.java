// src/main/java/com/example/alerts/persistence/repo/DeviceAlertItemRepo.java
package com.example.alerts.persistence.repo;

import com.example.alerts.persistence.entity.DeviceAlertItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

// public interface DeviceAlertItemRepo extends JpaRepository<DeviceAlertItemEntity, Long> {

//   List<DeviceAlertItemEntity> findAll();
//   List<DeviceAlertItemEntity> findByDeviceReportId(Long deviceReportId);
// }
@Repository
public interface DeviceAlertItemRepo extends JpaRepository<DeviceAlertItemEntity, Long> {
    // 直接查詢所有
    List<DeviceAlertItemEntity> findAll();

    // 如果想依照 deviceReportId 查
    List<DeviceAlertItemEntity> findByDeviceReportId(Long deviceReportId);
}