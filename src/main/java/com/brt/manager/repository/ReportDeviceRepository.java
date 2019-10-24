package com.brt.manager.repository;


import com.brt.manager.core.entity.ReportDevice;
import org.springframework.data.jpa.repository.JpaRepository;
public interface ReportDeviceRepository extends JpaRepository <ReportDevice, Long> {

    ReportDevice findByBusinessDeviceId(Long businessDeviceId);
}
