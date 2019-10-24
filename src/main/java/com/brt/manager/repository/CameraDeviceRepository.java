package com.brt.manager.repository;

import com.brt.manager.core.entity.CameraDevice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CameraDeviceRepository  extends JpaRepository<CameraDevice, Long> {

    List<CameraDevice> findCameraDeviceByBusinessDeviceId(Long businessDeviceId);

}
