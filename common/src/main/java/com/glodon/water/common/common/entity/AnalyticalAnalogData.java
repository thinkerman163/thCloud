package com.glodon.water.common.common.entity;

import java.io.Serializable;

public class AnalyticalAnalogData implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = -8792357033766315470L;

	/**
     * id
     */   
    private Long id;

    /**
     * 项目ID
     */   
    private Integer projectId;

    /**
     * 类型编码
     */  
    private String codeNumber;
  
    private String thingKey;

    /**
     * 采集源ID
     */   
    private Integer sourceId;

    /**
     * 结束时间
     */  
    private String peroidTime;

    /**
     * 数值
     */
    private Double value;

    /**
     * 设备id(预留)
     */   
    private Long monitorPointId;

    /**
     * 获取id
     *
     * @return id - id
     */
    public Long getId() {
        return id;
    }

    /**
     * 设置id
     *
     * @param id id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取项目ID
     *
     * @return project_id - 项目ID
     */
    public Integer getProjectId() {
        return projectId;
    }

    /**
     * 设置项目ID
     *
     * @param projectId 项目ID
     */
    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    /**
     * 获取类型编码
     *
     * @return code_number - 类型编码
     */
    public String getCodeNumber() {
        return codeNumber;
    }

    /**
     * 设置类型编码
     *
     * @param codeNumber 类型编码
     */
    public void setCodeNumber(String codeNumber) {
        this.codeNumber = codeNumber;
    }

    /**
     * @return thing_key
     */
    public String getThingKey() {
        return thingKey;
    }

    /**
     * @param thingKey
     */
    public void setThingKey(String thingKey) {
        this.thingKey = thingKey;
    }

    /**
     * 获取采集源ID
     *
     * @return source_id - 采集源ID
     */
    public Integer getSourceId() {
        return sourceId;
    }

    /**
     * 设置采集源ID
     *
     * @param sourceId 采集源ID
     */
    public void setSourceId(Integer sourceId) {
        this.sourceId = sourceId;
    }

    /**
     * 获取结束时间
     *
     * @return peroid_time - 结束时间
     */
    public String getPeroidTime() {
        return peroidTime;
    }

    /**
     * 设置结束时间
     *
     * @param peroidTime 结束时间
     */
    public void setPeroidTime(String peroidTime) {
        this.peroidTime = peroidTime;
    }

    /**
     * 获取数值
     *
     * @return value - 数值
     */
    public Double getValue() {
        return value;
    }

    /**
     * 设置数值
     *
     * @param value 数值
     */
    public void setValue(Double value) {
        this.value = value;
    }

    /**
     * 获取设备id(预留)
     *
     * @return monitor_point_id - 设备id(预留)
     */
    public Long getMonitorPointId() {
        return monitorPointId;
    }

    /**
     * 设置设备id(预留)
     *
     * @param monitorPointId 设备id(预留)
     */
    public void setMonitorPointId(Long monitorPointId) {
        this.monitorPointId = monitorPointId;
    }
}