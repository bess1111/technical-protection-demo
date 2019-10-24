package com.brt.manager.repository;

import com.brt.manager.core.entity.BusinessDevice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;



public interface BusinessDeviceRepository extends JpaRepository<BusinessDevice, Long> {

    BusinessDevice findByCode(String code);

    @Query(value="select count(*) from T_BUSINESS_DEVICE",nativeQuery=true)
    Integer getBusinessDeviceNumber();
}
