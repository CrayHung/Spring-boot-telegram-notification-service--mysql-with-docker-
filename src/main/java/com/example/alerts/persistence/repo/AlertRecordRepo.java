// persistence/repo/AlertRecordRepo.java
package com.example.alerts.persistence.repo;

import com.example.alerts.persistence.entity.AlertRecord;
import org.springframework.data.jpa.repository.JpaRepository;


public interface AlertRecordRepo extends JpaRepository<AlertRecord, Long> {}
