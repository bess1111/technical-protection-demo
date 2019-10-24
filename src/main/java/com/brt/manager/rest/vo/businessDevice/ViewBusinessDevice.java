package com.brt.manager.rest.vo.businessDevice;


import java.sql.Timestamp;
import java.util.List;


public class ViewBusinessDevice {

    private Long id;
    private String name;
    private String code;
    private String type;
    private Timestamp createTime;
    private Timestamp updateTime;
    private List<ViewCameraDevice> viewCameraDevices;
    private ViewLocationDevice viewLocationDevice;
    private ViewReportDevice viewReportDevice;



    public List<ViewCameraDevice> getViewCameraDevices() {
        return viewCameraDevices;
    }

    public void setViewCameraDevices(List<ViewCameraDevice> viewCameraDevices) {
        this.viewCameraDevices = viewCameraDevices;
    }

    public ViewLocationDevice getViewLocationDevice() {
        return viewLocationDevice;
    }

    public void setViewLocationDevice(ViewLocationDevice viewLocationDevice) {
        this.viewLocationDevice = viewLocationDevice;
    }

    public ViewReportDevice getViewReportDevice() {
        return viewReportDevice;
    }

    public void setViewReportDevice(ViewReportDevice viewReportDevice) {
        this.viewReportDevice = viewReportDevice;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public Timestamp getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
    }
}
