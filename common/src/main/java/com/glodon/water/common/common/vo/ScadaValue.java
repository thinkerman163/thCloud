package com.glodon.water.common.common.vo;

import java.io.Serializable;
import java.util.Date;

public class ScadaValue implements Serializable {
    /** 变量意义 */
    private static final long serialVersionUID = 3718724804924849442L;


    public ScadaValue() {

    }

    /**
     *
     * @param pointId   采集点id
     * @param peroidTime    采集周期
     * @param version       数据版本
     */
    public ScadaValue(Integer pointId, Date peroidTime, Byte version) {
        super();
        this.pointId = pointId;
        this.peroidTime = peroidTime;
        this.version = version;
    }

    private Integer  dicId;

    public Integer getDicId() {
        return dicId;
    }

    public void setDicId(Integer dicId) {
        this.dicId = dicId;
    }
    private Integer  monitorPointId;

    public Integer getMonitorPointId() {
        return monitorPointId;
    }

    public void setMonitorPointId(Integer monitorPointId) {
        this.monitorPointId = monitorPointId;
    }

    /**
     * id
     */
    private Long id;

    /**
     * 采集点id
     */
    private Integer pointId;

    /**
     * 数值
     */
    private Double value;

    /**
     * 采集周期起始时间
     */
    private Date peroidTime;

    /**
     * 创建采集周期起始时
     */ 
    private Date createPeroidTime;

    /**
     * 创建时间；该是为MySQL服务器时间，不应为业务服务器时间
     */ 
    private Date createAt;

    /**
     * 数据版本：0, 自动; 1 手动
     */
    private Byte version;

    /**
     * 创建人
     */
    private Integer creator;

    /**
     * 是否合格:0,不合格;1,合格
     */
    private Boolean isQualified;

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
     * @param id
     *            id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取采集点id
     *
     * @return point_id - 采集点id
     */
    public Integer getPointId() {
        return pointId;
    }

    /**
     * 设置采集点id
     *
     * @param pointId
     *            采集点id
     */
    public void setPointId(Integer pointId) {
        this.pointId = pointId;
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
     * @param value
     *            数值
     */
    public void setValue(Double value) {
        this.value = value;
    }

    /**
     * 获取采集周期起始时间
     *
     * @return peroid_time - 采集周期起始时间
     */
    public Date getPeroidTime() {
        return peroidTime;
    }

    /**
     * 设置采集周期起始时间
     *
     * @param peroidTime
     *            采集周期起始时间
     */
    public void setPeroidTime(Date peroidTime) {
        this.peroidTime = peroidTime;
    }

    /**
     * 获取创建采集周期起始时
     *
     * @return create_peroid_time - 创建采集周期起始时
     */
    public Date getCreatePeroidTime() {
        return createPeroidTime;
    }

    /**
     * 设置创建采集周期起始时
     *
     * @param createPeroidTime
     *            创建采集周期起始时
     */
    public void setCreatePeroidTime(Date createPeroidTime) {
        this.createPeroidTime = createPeroidTime;
    }

    /**
     * 获取创建时间；该是为MySQL服务器时间，不应为业务服务器时间
     *
     * @return create_at - 创建时间；该是为MySQL服务器时间，不应为业务服务器时间
     */
    public Date getCreateAt() {
        return createAt;
    }

    /**
     * 设置创建时间；该是为MySQL服务器时间，不应为业务服务器时间
     *
     * @param createAt
     *            创建时间；该是为MySQL服务器时间，不应为业务服务器时间
     */
    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    /**
     * 获取数据版本：0, 自动; 1 手动
     *
     * @return version - 数据版本：0, 自动; 1 手动
     */
    public Byte getVersion() {
        return version;
    }

    /**
     * 设置数据版本：0, 自动; 1 手动
     *
     * @param version
     *            数据版本：0, 自动; 1 手动
     */
    public void setVersion(Byte version) {
        this.version = version;
    }

    /**
     * 获取创建人
     *
     * @return creator - 创建人
     */
    public Integer getCreator() {
        return creator;
    }

    /**
     * 设置创建人
     *
     * @param creator
     *            创建人
     */
    public void setCreator(Integer creator) {
        this.creator = creator;
    }

    /**
     * 是否合格:0,不合格;1,合格
     */
    public Boolean getIsQualified() {
        return isQualified;
    }

    /**
     * 是否合格:0,不合格;1,合格
     */
    public void setIsQualified(Boolean isQualified) {
        this.isQualified = isQualified;
    }

    @Override
    public String toString() {
        return String.format("scadaValue id %d pointId %d value %.4f", this.getId(),
                this.getPointId(), this.getValue());
    }
}