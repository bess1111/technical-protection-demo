package com.brt.manager.core.entity;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import java.sql.Timestamp;
import javax.persistence.*;

import java.util.List;


@Table(name="T_BUSINESS_DEVICE")
@Entity
public class BusinessDevice {

    @Id
    @GeneratedValue(generator = "sequenceGenerator")
    @GenericGenerator(
            name = "sequenceGenerator",
            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters = {@Parameter(name = "sequence_name", value = "SEQ_BUSINESS_DEVICE")}
    )
    private Long id;

    @Column
    private String name;

    @Column
    private String code;

    @Column
    private String type;

    @Column
    private Timestamp createTime;
    @Column
    private Timestamp updateTime;

    @OneToOne(optional=true,mappedBy = "businessDevice")
    private LocationDevice locationDevice;

    @OneToOne(optional=true,mappedBy = "businessDevice")
    private ReportDevice reportDevice;

    @OneToMany(cascade = CascadeType.ALL,fetch=FetchType.LAZY,mappedBy="businessDevice")
    private List<CameraDevice> cameraDevices;


    public LocationDevice getLocationDevice() {
        return locationDevice;
    }

    public void setLocationDevice(LocationDevice locationDevice) {
        this.locationDevice = locationDevice;
    }

    public ReportDevice getReportDevice() {
        return reportDevice;
    }

    public void setReportDevice(ReportDevice reportDevice) {
        this.reportDevice = reportDevice;
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

    public List<CameraDevice> getCameraDevices() {
        return cameraDevices;
    }

    public void setCameraDevices(List<CameraDevice> cameraDevices) {
        this.cameraDevices = cameraDevices;
    }
}
