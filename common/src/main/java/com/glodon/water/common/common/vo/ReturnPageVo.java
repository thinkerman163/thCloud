package com.glodon.water.common.common.vo;

import java.io.Serializable;

public class ReturnPageVo implements Serializable {

    /** 变量意义 */
    private static final long serialVersionUID = 8252174733141827881L;

    /**
     * 当前页数
     */
    private Integer page = 0;

    /**
     * 总条数
     */
    private Long num = 0L;

    /**
     * 总页数
     */
    private Integer pageSum = 0;

    /**
     * 数据条数
     */
    private Integer dataSize = 0;

    /**
     * 数据
     */
    private Object data;

    /**
     * 多余数据
     */
    private Object otherData;

    public Object getOtherData() {
        return otherData;
    }

    public void setOtherData(Object otherData) {
        this.otherData = otherData;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Long getNum() {
        return num;
    }

    public void setNum(Long num) {
        this.num = num;
    }

    public Integer getPageSum() {
        return pageSum;
    }

    public void setPageSum(Integer pageSum) {
        this.pageSum = pageSum;
    }

    public Integer getDataSize() {
        return dataSize;
    }

    public void setDataSize(Integer dataSize) {
        this.dataSize = dataSize;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

}
