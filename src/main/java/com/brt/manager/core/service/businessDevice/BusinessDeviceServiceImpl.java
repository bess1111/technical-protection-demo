package com.brt.manager.core.service.businessDevice;

import com.brt.manager.core.entity.BusinessDevice;
import com.brt.manager.core.entity.CameraDevice;
import com.brt.manager.core.entity.LocationDevice;
import com.brt.manager.core.entity.ReportDevice;
import com.brt.manager.repository.BusinessDeviceRepository;
import com.brt.manager.repository.CameraDeviceRepository;
import com.brt.manager.repository.LocationDeviceRepository;
import com.brt.manager.repository.ReportDeviceRepository;
import com.brt.manager.excption.CustomBusinessException;
import com.brt.manager.rest.vo.businessDevice.ViewBusinessDevice;
import com.brt.manager.rest.vo.businessDevice.ViewCameraDevice;
import com.brt.manager.rest.vo.businessDevice.ViewLocationDevice;
import com.brt.manager.rest.vo.businessDevice.ViewReportDevice;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Service
public class BusinessDeviceServiceImpl implements BusinessDeviceService{

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private BusinessDeviceRepository businessDeviceRepository;

    @Autowired
    private CameraDeviceRepository cameraDeviceRepository;

    @Autowired
    private LocationDeviceRepository locationDeviceRepository;

    @Autowired
    private ReportDeviceRepository reportDeviceRepository;

    @Override
    public ViewBusinessDevice addBusinessDevice(ViewBusinessDevice viewBusinessDevice) {
        if(null==viewBusinessDevice) {
            this.logger.warn("the param of addBusinessDevice is null");
            return null;
        }

        if(null==viewBusinessDevice.getCode()||"".equals(viewBusinessDevice.getCode())) {
            throw new CustomBusinessException("code不能为空");
        }

        BusinessDevice  businessDevice = businessDeviceRepository.findByCode(viewBusinessDevice.getCode());
        if(null!= businessDevice) {
            throw new CustomBusinessException("所填写的code已存在");
        }

        LocationDevice locationDevice =new LocationDevice();
        ReportDevice reportDevice =new ReportDevice();
        Timestamp currentTime = new Timestamp(System.currentTimeMillis());
        viewBusinessDevice.setCreateTime(currentTime);
        businessDevice = new BusinessDevice();
        BeanUtils.copyProperties(viewBusinessDevice,businessDevice );
        businessDevice = businessDeviceRepository.save(businessDevice);

        if(null==businessDevice) {
            this.logger.warn("businessDevice can not be found");
            return null;
        }

        BeanUtils.copyProperties(businessDevice,viewBusinessDevice);


        //insert camera device
        List<ViewCameraDevice> viewCameraDevices = viewBusinessDevice.getViewCameraDevices();

        if (viewCameraDevices!=null&&!viewCameraDevices.isEmpty()){
            List<CameraDevice> cameraDevices = this.getCameraDevicesByViewCameraDevices(viewCameraDevices,businessDevice,0);
            cameraDevices =cameraDeviceRepository.saveAll(cameraDevices);
            if (null==cameraDevices||0==cameraDevices.size()){
                this.logger.warn("cameraDevice can not be found");
                return null;
            }
            viewCameraDevices = this.getViewCameraDevicesByCameraDevices(cameraDevices);
            viewBusinessDevice.setViewCameraDevices(viewCameraDevices);
        }



        //insert location device
        ViewLocationDevice viewLocationDevice = viewBusinessDevice.getViewLocationDevice();
        if (null!=viewLocationDevice){
            BeanUtils.copyProperties(viewLocationDevice,locationDevice);
            locationDevice.setCreateTime(currentTime);
            locationDevice.setBusinessDevice(businessDevice);
            locationDevice= locationDeviceRepository.save(locationDevice);
            if(null==locationDevice) {
                this.logger.warn("locationDevice can not be found");
                return null;
            }
            BeanUtils.copyProperties(locationDevice,viewLocationDevice);
            viewBusinessDevice.setViewLocationDevice(viewLocationDevice);
        }

        //insert ReportDevice
        ViewReportDevice viewReportDevice = viewBusinessDevice.getViewReportDevice();
        if (null!=viewReportDevice){
            BeanUtils.copyProperties(viewReportDevice,reportDevice);
            reportDevice.setCreateTime(currentTime);
            reportDevice.setBusinessDevice(businessDevice);
            reportDevice = reportDeviceRepository.save(reportDevice);

            if(null==reportDevice) {
                this.logger.warn("reportDevice can not be found");
                return null;
            }

            BeanUtils.copyProperties(reportDevice,viewReportDevice);
            viewBusinessDevice.setViewReportDevice(viewReportDevice);
        }
        return viewBusinessDevice;
    }


    @Override
    public ViewBusinessDevice updateBusinessDevice(ViewBusinessDevice viewBusinessDevice, Long businessDeviceId) {
        if(null==viewBusinessDevice||null==businessDeviceId) {
            this.logger.warn("the param of updateCourse is null");
            return null;
        }

        BusinessDevice businessDevice = businessDeviceRepository.findByCode(viewBusinessDevice.getCode());
        if(null==businessDevice) {
             throw new CustomBusinessException("没有匹配当前code的设备");
        }

        Optional<BusinessDevice> businessDeviceOpt  = businessDeviceRepository.findById(businessDeviceId);
        if( null ==businessDeviceOpt ) {
            this.logger.warn("businessDevice can not be found");
            return null;
        }

        viewBusinessDevice.setCreateTime(businessDevice.getCreateTime());
        viewBusinessDevice.setId(businessDeviceId);
        BeanUtils.copyProperties(viewBusinessDevice, businessDevice);
        Timestamp currentTime = new Timestamp(System.currentTimeMillis());
        businessDevice.setUpdateTime(currentTime);
        businessDevice = businessDeviceRepository.save(businessDevice);

        if (null == businessDevice){
            this.logger.warn("businessDevice can not be found");
            return null;
        }

        //update viewCameraDevices
        List<ViewCameraDevice> viewCameraDevices = viewBusinessDevice.getViewCameraDevices();
        if (null!=viewCameraDevices){
            List<CameraDevice> cameraDevices = cameraDeviceRepository.findCameraDeviceByBusinessDeviceId(businessDeviceId);
            if ( 0 != viewCameraDevices.size()) {
                cameraDeviceRepository.deleteAll(cameraDevices);
               cameraDevices = getCameraDevicesByViewCameraDevices(viewCameraDevices,businessDevice,1);
                cameraDevices = cameraDeviceRepository.saveAll(cameraDevices);
                if( null == cameraDevices ||0 == cameraDevices.size()) {
                    this.logger.warn("cameraDevice can not be found");
                    return null;
                }
                viewCameraDevices = getViewCameraDevicesByCameraDevices(cameraDevices);
            }
        }

        //update reportDevice
        ViewReportDevice viewReportDevice = viewBusinessDevice.getViewReportDevice();

        if (null != viewReportDevice){
            ReportDevice reportDevice = reportDeviceRepository.findByBusinessDeviceId(businessDeviceId);
            if (null!= reportDevice){
                viewReportDevice.setId(reportDevice.getId());
                viewReportDevice.setCreateTime(reportDevice.getCreateTime());
                viewReportDevice.setUpdateTime(currentTime);
            }else {
                viewReportDevice.setCreateTime(currentTime);
            }
            BeanUtils.copyProperties(viewReportDevice,reportDevice);
            reportDevice.setBusinessDevice(businessDevice);
            reportDevice = reportDeviceRepository.save(reportDevice);
            if (null == reportDevice){
                this.logger.warn("cameraDevice can not be found");
                return null;
            }
            BeanUtils.copyProperties(reportDevice,viewReportDevice);
        }

        //update LocationDevice
        ViewLocationDevice viewLocationDevice = viewBusinessDevice.getViewLocationDevice();


        if (null != viewLocationDevice){
            LocationDevice locationDevice = locationDeviceRepository.findByBusinessDeviceId(businessDeviceId);
            if (null!=locationDevice){
                viewLocationDevice.setId(locationDevice.getId());
                viewLocationDevice.setCreateTime(locationDevice.getCreateTime());
                viewBusinessDevice.setUpdateTime(currentTime);
            }else{
                viewBusinessDevice.setCreateTime(currentTime);
            }
            BeanUtils.copyProperties(viewLocationDevice,locationDevice);
            locationDevice.setBusinessDevice(businessDevice);
            locationDevice = locationDeviceRepository.save(locationDevice);
            if (null == locationDevice){
                this.logger.warn("cameraDevice can not be found");
                return null;
            }

            BeanUtils.copyProperties(locationDevice,viewLocationDevice);

        }

        //po to vo
        viewBusinessDevice = this.getViewBusinessDeviceByBusinessDevice(businessDevice);
        viewBusinessDevice.setViewCameraDevices(viewCameraDevices);
        viewBusinessDevice.setViewLocationDevice(viewLocationDevice);
        viewBusinessDevice.setViewReportDevice(viewReportDevice);
        return viewBusinessDevice;
    }

    @Override
    public Boolean deleteBusinessDevice(Long businessDeviceId) {
        if(null == businessDeviceId) {
            this.logger.info("param is null");
            return false;
        }

        BusinessDevice businessDevice = this.getBusinessDevicesById(businessDeviceId);
        if(null == businessDevice) {
            this.logger.info("businessDevice does not exsis");
            return false;
        }

        List<CameraDevice> cameraDevices =  cameraDeviceRepository.findCameraDeviceByBusinessDeviceId(businessDeviceId);
        if (null != cameraDevices){
            if (0!=cameraDevices.size()){
                cameraDeviceRepository.deleteAll(cameraDevices);
            }
        }
        LocationDevice locationDevice  =locationDeviceRepository.findByBusinessDeviceId(businessDeviceId);
        if (null!=locationDevice){
            locationDeviceRepository.deleteById(locationDevice.getId());
        }

         ReportDevice reportDevice = reportDeviceRepository.findByBusinessDeviceId(businessDeviceId);
        if (null != reportDevice){
            reportDeviceRepository.deleteById(reportDevice.getId());
        }

        businessDeviceRepository.deleteById(businessDeviceId);
        return true;

    }

    @Override
    public ViewBusinessDevice getBusinessDeviceById(Long businessDeviceId) {
        if(null==businessDeviceId) {
            this.logger.warn("the param of getBusinessDeviceId is null");
            return null;
        }
        //find
        Optional<BusinessDevice> businessDevice = businessDeviceRepository.findById(businessDeviceId);

        if(!businessDevice.isPresent()) {
            this.logger.warn("businessDevice can not be found");
            return null;
        }

        //po to vo
        ViewBusinessDevice viewBusinessDevice = this.getViewBusinessDeviceByBusinessDevice(businessDevice.get());

        List<CameraDevice>cameraDevices = cameraDeviceRepository.findCameraDeviceByBusinessDeviceId(businessDeviceId);
        if (cameraDevices!=null&&!cameraDevices.isEmpty()){
            List<ViewCameraDevice> viewCameraDevices = getViewCameraDevicesByCameraDevices(cameraDevices);
            viewBusinessDevice.setViewCameraDevices(viewCameraDevices);
        }

        ReportDevice reportDevice = reportDeviceRepository.findByBusinessDeviceId(businessDeviceId);
        if (null != reportDevice){
            ViewReportDevice viewReportDevice = new ViewReportDevice();
            BeanUtils.copyProperties(reportDevice,viewReportDevice);
            viewBusinessDevice.setViewReportDevice(viewReportDevice);
        }

        LocationDevice locationDevice = locationDeviceRepository.findByBusinessDeviceId(businessDeviceId);
        if (null != locationDevice){
            ViewLocationDevice viewLocationDevice =new ViewLocationDevice();
            BeanUtils.copyProperties(locationDevice,viewLocationDevice);
            viewBusinessDevice.setViewLocationDevice(viewLocationDevice);
        }

        return viewBusinessDevice;
    }

    @Override
    public Page<ViewBusinessDevice> getAllBusinessDeviceByPage(Integer pageNumber, Integer pageSize) {

        Sort sort = new Sort(Direction.DESC, "createTime");
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        Page<BusinessDevice> page = businessDeviceRepository.findAll(pageable);

        if (null==page) {
            return null;
        }
        if(null==page.getContent()||page.getContent().isEmpty()) {
            this.logger.warn("BusinessDevice can not be found");
            return null;
        }

        List<BusinessDevice> businessDevices = page.getContent();
        List<ViewBusinessDevice> viewBusinessDevice = this.getViewBusinessDeviceByBusinessDevices(businessDevices);
        Page<ViewBusinessDevice> pageViewBusinessDevice = new PageImpl<>(viewBusinessDevice, pageable, businessDevices.size());
        return pageViewBusinessDevice;
    }

    @Override
    public Integer getBusinessDeviceNumber() {
        Integer businesDeviceCount = businessDeviceRepository.getBusinessDeviceNumber();
        if (null == businesDeviceCount){
            this.logger.warn("BusinessDevice can not be found");
            return null;
        }
        return businesDeviceCount;
    }


    private BusinessDevice getBusinessDevicesById(Long businessDeviceId) {
        Optional<BusinessDevice> optClasses = businessDeviceRepository.findById(businessDeviceId);
        return optClasses.get();
    }

    private ViewBusinessDevice getViewBusinessDeviceByBusinessDevice(BusinessDevice businessDevice) {
        if (null == businessDevice){
            this.logger.warn("BusinessDevice can not be found");
            return null;
        }

        ViewBusinessDevice viewBusinessDevice = new ViewBusinessDevice();
        BeanUtils.copyProperties(businessDevice, viewBusinessDevice);
        return viewBusinessDevice;
    }

    //ViewBusinessDevice po to vo
    private List<ViewBusinessDevice> getViewBusinessDeviceByBusinessDevices(List<BusinessDevice> businessDevices) {
        if (null == businessDevices || businessDevices.isEmpty()){
            this.logger.warn("businessDevices can not be found");
            return null;
        }
        List<ViewBusinessDevice> viewBusinessDevices = new ArrayList<>();
        for (BusinessDevice businessDevice : businessDevices) {
            ViewBusinessDevice viewBusinessDevice = new ViewBusinessDevice();
            BeanUtils.copyProperties(businessDevice, viewBusinessDevice);
            viewBusinessDevices.add(viewBusinessDevice);
        }
        return viewBusinessDevices;
    }

    //CameraDevices vo to po
    //type==0 is insert type==1 is update
    private List<CameraDevice> getCameraDevicesByViewCameraDevices(List<ViewCameraDevice> viewCameraDevices,BusinessDevice businessDevice,Integer type) {
        if (null == viewCameraDevices||viewCameraDevices.isEmpty()){
            this.logger.warn("viewCameraDevices can not be found");
            return null;
        }
        if (null == businessDevice){
            this.logger.warn("businessDevice can not be found");
            return null;
        }
        Timestamp currentTime = new Timestamp(System.currentTimeMillis());
        List<CameraDevice> cameraDevices = new ArrayList<>();
        if (type==0){
            for (ViewCameraDevice viewCameraDevice : viewCameraDevices) {
                CameraDevice cameraDevice = new CameraDevice();
                BeanUtils.copyProperties(viewCameraDevice, cameraDevice);
                cameraDevice.setCreateTime(currentTime);
                cameraDevice.setBusinessDevice(businessDevice);
                cameraDevices.add(cameraDevice);
            }
        }
        if (type==1){
            for (ViewCameraDevice viewCameraDevice : viewCameraDevices) {
                CameraDevice cameraDevice = new CameraDevice();
                BeanUtils.copyProperties(viewCameraDevice, cameraDevice);
                cameraDevice.setCreateTime(businessDevice.getCreateTime());
                cameraDevice.setUpdateTime(currentTime);
                cameraDevice.setBusinessDevice(businessDevice);
                cameraDevices.add(cameraDevice);
            }
        }

        return cameraDevices;
    }

    //CameraDevices po to vo
    private List<ViewCameraDevice> getViewCameraDevicesByCameraDevices( List<CameraDevice> cameraDevices ) {
        if (null == cameraDevices||cameraDevices.isEmpty()){
            this.logger.warn("cameraDevices can not be found");
            return null;
        }
        List<ViewCameraDevice> viewCameraDevices = new ArrayList<>();
        for (CameraDevice cameraDevice : cameraDevices) {
            ViewCameraDevice viewCameraDevice = new ViewCameraDevice();
            BeanUtils.copyProperties(cameraDevice, viewCameraDevice);
            viewCameraDevices.add(viewCameraDevice);
        }
        return viewCameraDevices;
    }

}
