package com.glodon.water.common.common.vo;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by xus-d on 2016/8/18.
 */
public class DateVo implements Serializable {
    
    /**  变量意义 */
    private static final long serialVersionUID = 3271352948756741391L;
    
    private Date startDate;
    private Date endDate;
    private String region;  
    private String corpId;  
    private Integer projectId;  
    

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }
    
    public String getCorpId() {
    	if(corpId==null||corpId.length()==0)corpId="1";
        return corpId;
    }

    public void setCorpId(String corpId) {
        this.corpId = corpId;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}
