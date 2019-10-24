package com.brt.manager.repository;

import com.brt.manager.core.entity.LocationDevice;
import org.springframework.data.jpa.repository.JpaRepository;


public interface LocationDeviceRepository extends JpaRepository<LocationDevice, Long> {

    LocationDevice findByBusinessDeviceId(Long businessDeviceId);
}
