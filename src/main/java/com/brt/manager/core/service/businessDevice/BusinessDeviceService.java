package com.brt.manager.core.service.businessDevice;

import com.brt.manager.rest.vo.businessDevice.ViewBusinessDevice;
import org.springframework.data.domain.Page;


public interface BusinessDeviceService {
    ViewBusinessDevice addBusinessDevice(ViewBusinessDevice viewBusinessDevice);

    ViewBusinessDevice updateBusinessDevice(ViewBusinessDevice viewBusinessDevice, Long businessDeviceId);

    Boolean deleteBusinessDevice(Long businessDeviceId);

    ViewBusinessDevice getBusinessDeviceById(Long businessDeviceId);

    Page<ViewBusinessDevice> getAllBusinessDeviceByPage(Integer pageNumber,Integer pageSize);

    Integer getBusinessDeviceNumber();
}
