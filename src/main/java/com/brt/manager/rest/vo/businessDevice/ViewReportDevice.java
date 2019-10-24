package com.brt.manager.rest.vo.businessDevice;
import java.sql.Timestamp;

public class ViewReportDevice {
    private Long id;


    private String name;


    private String code;

    private Long businessDeviceId;

    private Timestamp createTime;

    private Timestamp updateTime;


    private String businessDeviceName;

    private String businessDeviceCode;

    private String businessDeviceType;


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

    public Long getBusinessDeviceId() {
        return businessDeviceId;
    }

    public void setBusinessDeviceId(Long businessDeviceId) {
        this.businessDeviceId = businessDeviceId;
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

    public String getBusinessDeviceName() {
        return businessDeviceName;
    }

    public void setBusinessDeviceName(String businessDeviceName) {
        this.businessDeviceName = businessDeviceName;
    }

    public String getBusinessDeviceCode() {
        return businessDeviceCode;
    }

    public void setBusinessDeviceCode(String businessDeviceCode) {
        this.businessDeviceCode = businessDeviceCode;
    }

    public String getBusinessDeviceType() {
        return businessDeviceType;
    }

    public void setBusinessDeviceType(String businessDeviceType) {
        this.businessDeviceType = businessDeviceType;
    }
}
