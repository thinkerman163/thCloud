package com.glodon.water.common.common.vo;

import java.io.Serializable;
import java.util.Date;

import com.glodon.water.common.util.DateReflectUtil;

public class PageInfoVo implements Serializable {

    /**
     * 变量意义
     */
    private static final long serialVersionUID = -9124654602781298611L;

    private Integer pageIndex = 1;

    private Integer pageSize = 10;

    private Boolean isPage;

    private String searchValue;

    // 开始时间
    private Date startTime;
    // 结束时间
    private Date endTime;

    public Integer getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(Integer pageIndex) {
        this.pageIndex = pageIndex;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Boolean getIsPage() {
        return isPage;
    }

    public void setIsPage(Boolean isPage) {
        this.isPage = isPage;
    }

    /**
     * 
     * @Description 获取模糊查询值，处理 %
     * @author youps-a
     * @date 2016年10月11日 下午4:55:16
     */
    public String getSearchValue() {
    	  if (searchValue!=null&&searchValue.length()>0) {
            return searchValue.replaceAll("%", "\\\\%");
        } else {
            return searchValue;
        }
    }

    public void setSearchValue(String searchValue) {
        this.searchValue = searchValue;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = DateReflectUtil.DateReflect(startTime);
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = DateReflectUtil.DateReflect(endTime);
    }

    @Override
    public String toString() {
        return "PageInfoVO [pageIndex=" + pageIndex + ", pageSize=" + pageSize
                + ", isPage=" + isPage + ", searchValue=" + searchValue
                + ", startTime=" + startTime + ", endTime=" + endTime + "]";
    }

}
