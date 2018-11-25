package com.glodon.water.common.common.vo;

import java.io.Serializable;

import com.glodon.water.common.common.enumpo.RedisDataStructEnum;
import com.glodon.water.common.common.enumpo.RedisDataTypeEnum;

/**
 * Created by zoul on 2016/12/30.
 */
public class RedisRegInfoVo implements Serializable {
    private static final long serialVersionUID = 32052948756741391L;
    //缓存名称
    private String serviceName;
    //缓存键值
    private String key;
    //缓存注册键值
    private String regKey;
    //缓存键值(后缀)
    private String sufKey;
    //有效时间（秒）
    private int activeTime;
    //缓存描述
    private String remark;
    //处理类型 0不需处理，1自动处理，2,自定义处理
    private int dealType;
    //数据结构  0kv、2hs、3ls、4zs
    private int struct;
    //数据分类  1、静态2、实时 3、查询 0其他
    private int dataType;
    
    /**
     * 更新相关属性
     */
    public void updata() {
    	this.getKey();
    	this.getRegKey();
    	if(relTables==null)this.setRelTables("");
    }  
    
    /**
     * 关联的表名
     */
    private String relTables;
    
    /**
     * 影响的时段
     */
    private String relduration;
    

    public int getStruct() {
        return struct;
    }
   
    public void setStruct(int struct) {
        this.struct = struct;
    }
    
    public int getDataType() {
        return dataType;
    }
   
    public void setDataType(int dataType) {
        this.dataType = dataType;
    }
    
    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }
    
    public String getRegKey() {
    	regKey=RedisDataStructEnum.KEYVALUE.getName()+"_"+RedisDataTypeEnum.REGINFO.getName()
    			+"_"+this.serviceName+"_"+this.sufKey;
        return regKey;
    }  
    
    public void setRegKey(String regKey) {
        this.regKey = regKey;
    }
    
    
    public String getKey() {
    	key=RedisDataStructEnum.getName(this.struct)+"_"+RedisDataTypeEnum.getName(this.dataType)
    			+"_"+this.serviceName+"_"+this.sufKey;
        return key;
    }  
    
    public void setKey(String key) {
        this.key = key;
    }
    
    public void setSufKey(String sufKey) {
        this.sufKey = sufKey;
    }
    
    public String getSufKey() {
        return sufKey;
    }
    
    /**
     * 关联的表名，逗号间隔
     */
    public String getRelTables() {    	
        return relTables;
    }  
    /**
     * 关联的表名，逗号间隔
     */
    public void setRelTables(String relTables) {
        this.relTables = relTables.toLowerCase();
    }

    /**
     * 关联的时间段，逗号间隔，开始时间，结束时间
     */
    public String getRelduration() {
    	 return relduration;
    }   
    
   

    /**
     * 关联的时间段，逗号间隔，开始时间，结束时间
     */
    public void setRelduration(String relduration) {
        this.relduration = relduration;
    }
    
    public String getRemark() {
        return remark;
    }   
    public void setRemark(String remark) {
        this.remark = remark;
    }
    
    /**
     * 处理类型，0不需处理，1自动处理，2,自定义处理
     */
    public int getdealType() {
        return dealType;
    }
    /**
     * 处理类型，0不需处理，1自动处理，2,自定义处理
     */
    public void setdealType(int dealType) {
        this.dealType = dealType;
    }
    
    
   
    public int getActiveTime() {
        return activeTime;
    }
   
    public void setActiveTime(int activeTime) {
        this.activeTime = activeTime;
    }
}
