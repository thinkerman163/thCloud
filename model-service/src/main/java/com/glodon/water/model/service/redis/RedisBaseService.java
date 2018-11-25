
package com.glodon.water.model.service.redis;

import com.glodon.water.common.common.enumpo.RedisDataStructEnum;
import com.glodon.water.common.common.enumpo.RedisDataTypeEnum;
import com.glodon.water.common.common.vo.RedisRegInfoVo;
import com.glodon.water.model.util.RedisUtil;
import com.google.gson.Gson;


/**
 * @Description service基类
 * @author (作者) zoul-c
 * @date (开发日期) 2017年1月13日 上午9:58:43
 * @company (开发公司) 广联达软件股份有限公司
 * @copyright (版权) 本文件归广联达软件股份有限公司所有
 * @version (版本) V1.0
 * @since (该版本支持的JDK版本) 1.7
 * @modify (修改) 第N次修改：时间、修改人;修改说明
 * @Review (审核人) 审核人名称
 */
public class RedisBaseService implements IRedisInfoService{

	 protected RedisRegInfoVo redisRegInfo;
	  //注册索引名
	 private String KEY_REGSERCIVE_INDEX_KEY = "RedisBaseService";
	 //注册索引名关联表
	 private String KEY_REGSERCIVE_INDEX_FIELD_TABLE = "RedisBaseService_reltable";
		
	 public RedisRegInfoVo getRedisRegInfo() {
	        return redisRegInfo;
	    }
	 
	    @Override
	    public void setRedisRegInfo(RedisRegInfoVo redisRegInfo,String value) {
	    	redisRegInfo.updata();
		    this.redisRegInfo = redisRegInfo;
	    	try
	    	{
	      
	        //注册信息
	        String key=redisRegInfo.getRegKey();
	        Gson gson = new Gson();	  
	        String regValue= gson.toJson(redisRegInfo);
			RedisUtil.setValue(key, redisRegInfo.getActiveTime(),regValue); 
			if(redisRegInfo.getdealType()==0) //只注册，不处理
			{
				 //保存缓存 
			      this.setKvValue(value,redisRegInfo.getActiveTime());   
			      return;
			}
			//注册哈希表关联表信息
			String[] hashKey=redisRegInfo.getRelTables().split(",");
			for(int i=0;i<hashKey.length;i++)
			{
				if(hashKey[i].trim().length()>1) //表名长度大于1
				{				
				  String listRegKey=this.getRegKeybyTable(hashKey[i]);
				  //保存key的信息
				  String regKey=redisRegInfo.getKey();
				   if(listRegKey==null||listRegKey.trim().length()<1)		
				   {
					   listRegKey="|"+regKey+"|";
				   }
				   else if(listRegKey.indexOf("|"+regKey+"|")<0)
				   {
					   listRegKey=listRegKey+regKey+"|";
				   }
				   else
				   {
					   continue; //已经注册了
				   }
					//更新关联table列表
				  RedisUtil.setHash(getRegKey(),hashKey[i],listRegKey);
				}
			}
	    	} catch (Exception e) {
                e.getStackTrace();
            }
				
	      //保存缓存
	      this.setKvValue(value,redisRegInfo.getActiveTime());   
	    }
	    
	  
	    private String getRegKey() {	        
	    	
	    	return RedisDataStructEnum.HASH.getName()+"_"+RedisDataTypeEnum.REGINFO.getName()
	    			+"_"+this.KEY_REGSERCIVE_INDEX_KEY+"_"+this.KEY_REGSERCIVE_INDEX_FIELD_TABLE;			
	    }
  
	    
	    @Override
	    public String getRegKeybyTable(String tableName) {
	        
	    	//注册哈希表关联表信息
			String key= getRegKey();
			String regValue=  RedisUtil.getHash(key,tableName);
	        return regValue;
	    }
	    
	    //根据
	    @Override
	    public String getRegInfo(String key) {
	        
	    	String b_regKey=RedisDataStructEnum.KEYVALUE.getName()+"_"+RedisDataTypeEnum.REGINFO.getName()+"_";
	    	String regKey=b_regKey;
	    	if(key.length()<=6)regKey=key;
	    	else regKey=b_regKey+key.substring(7);
	    	//注册哈希表关联表信息
	    	return RedisUtil.getValue(regKey);
	    }
	    
    @Override
    public Integer clearRedisInfo(String key) {
         //清除缓存信息
    	RedisUtil.delValue(key);   	
    	
    	
    	return 0;
        
    }
    
    @Override
    public Integer clearRegInfo(String key,String tableName) {
    	
    	String b_regKey=RedisDataStructEnum.KEYVALUE.getName()+"_"+RedisDataTypeEnum.REGINFO.getName()+"_";
    	String regKey=b_regKey;
    	if(key.length()<=6)regKey=key;
    	else regKey=b_regKey+key.substring(7);   
    	 System.out.println(regKey);
    	 System.out.println(key);
         //清除注册信息
    	RedisUtil.delValue(regKey);   	
    	//清除关联table信息
    	 String listRegKey=this.getRegKeybyTable(tableName);
		
    	 System.out.println(listRegKey);
    	
    	 listRegKey=listRegKey.replace("|"+key+"|","|");
    	 if(listRegKey.trim().equals("|"))listRegKey="";		   
		//更新关联table列表
		 RedisUtil.setHash(getRegKey(),tableName,listRegKey);
		 System.out.println(listRegKey);
    	return 0;
        
    }

	@Override
	public boolean setKvValue(String value,int VALUE_TIMEOUT) {
		
		String key=redisRegInfo.getKey();
		RedisUtil.setValue(key, VALUE_TIMEOUT,value);
		return true;
	}

	@Override
	public String getKvValue(String key) {		
		return RedisUtil.getValue(key);
	}
    
    

}
