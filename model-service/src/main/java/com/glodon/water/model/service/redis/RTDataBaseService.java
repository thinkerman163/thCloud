
package com.glodon.water.model.service.redis;


import java.util.Date;
import java.util.Map;

import com.glodon.water.common.common.enumpo.RedisDataStructEnum;
import com.glodon.water.common.common.enumpo.RedisDataTypeEnum;
import com.glodon.water.common.common.enumpo.RedisRealTimeDataTypeEnum;
import com.glodon.water.common.common.vo.RedisRegInfoVo;
import com.glodon.water.common.common.vo.ScadaValue;
import com.glodon.water.common.util.DateUtil;
import com.glodon.water.model.util.RedisUtil;
import com.google.gson.Gson;
import com.google.gson.internal.Primitives;
import com.google.gson.reflect.TypeToken;



/**
* @Description 实时数据service基类
* @author (作者) zoul-c
* @date (开发日期) 2017年1月13日 上午9:58:43
* @company (开发公司) 广联达软件股份有限公司
* @copyright (版权) 本文件归广联达软件股份有限公司所有
* @version (版本) V1.0
* @since (该版本支持的JDK版本) 1.7
* @modify (修改) 第N次修改：时间、修改人;修改说明
* @Review (审核人) 审核人名称
*/
public class RTDataBaseService {

    /**
     * @Description 保存实时数据缓存
     * @author zoul-c
     * @date key缓存对象名，pointId采集点，type当前还是上一周期，枚举RedisRealTimeDataTypeEnum，val 值
     */
	public void setRealtimeData(String key,Integer pointId,String type,ScadaValue val)
	{
		Gson gson = new Gson();
		String listJson = gson.toJson(val);

		// 建立缓存Service
		RedisBaseService rbs = new RedisBaseService();
		// 缓存信息
		RedisRegInfoVo rri = new RedisRegInfoVo();
		rri.setDataType(RedisDataTypeEnum.REALTIME.getIndex());// 实时信息,需要使用枚举类型
		rri.setStruct(RedisDataStructEnum.KEYVALUE.getIndex());// 缓存的数据类型
		rri.setServiceName(key);// 缓存服务名字(表名)
		// 缓存条件
		String sufKey = type+":pointId:" + pointId;//类型+pointId
		rri.setSufKey(sufKey);

		// 注册缓存信息
		rri.setdealType(0);// 不需要系统自动清除
		rri.setActiveTime(24*60 * 60); // 保存一天
		// 注册缓存,并赋值
		rbs.setRedisRegInfo(rri, listJson);
	}
	 /**
     * @Description 得到实时数据缓存
     * @author zoul-c
     * @date key缓存对象名，pointId采集点，type当前还是上一周期，枚举RedisRealTimeDataTypeEnum
     */
	public ScadaValue getRealtimeData(String key,Integer pointId,String type)
	{

		// 建立缓存Service
		RedisBaseService rbs = new RedisBaseService();
		// 缓存信息
		RedisRegInfoVo rri = new RedisRegInfoVo();
		rri.setDataType(RedisDataTypeEnum.REALTIME.getIndex());// 实时信息,需要使用枚举类型
		rri.setStruct(RedisDataStructEnum.KEYVALUE.getIndex());// 缓存的数据类型
		rri.setServiceName(key);// 缓存服务名字(表名)
		// 缓存条件
		String sufKey = type+":pointId:" + pointId;//类型+pointId
		rri.setSufKey(sufKey);

		// 取值
		String listJson = rbs.getKvValue(rri.getKey());
		Gson gson = new Gson();
		ScadaValue val = new ScadaValue();
		// 如果没有，自动更新缓存
		if (listJson!=null && listJson.length()>0) {
		 //读到缓存
			val = gson.fromJson(listJson,ScadaValue.class);
		}

		return val;

	}


	
	 /**
     * @Description 保存实时数据缓存
     * @author zoul-c
     * @date key缓存对象名，pointId采集点，type当前还是上一周期，枚举RedisRealTimeDataTypeEnum，val 值
     */
	public void setRealtimeData(String key,String pointId,String type,String val)
	{	
		String listJson = val;

		// 建立缓存Service
		//RedisBaseService rbs = new RedisBaseService();
		// 缓存信息
		RedisRegInfoVo rri = new RedisRegInfoVo();
		rri.setDataType(RedisDataTypeEnum.REALTIME.getIndex());// 实时信息,需要使用枚举类型
		rri.setStruct(RedisDataStructEnum.KEYVALUE.getIndex());// 缓存的数据类型
		rri.setServiceName(key);// 缓存服务名字(表名)
		// 缓存条件
		String sufKey = type+":pointId:" + pointId;//类型+pointId
		rri.setSufKey(sufKey);
	
		// 缓存信息
		rri.setdealType(0);// 不需要系统自动清除
		rri.setActiveTime(24*60 * 60); // 保存一天							
		// 不注册缓存,直接赋值		
		rri.updata();
        String rdkey=rri.getKey();      
		RedisUtil.setValue(rdkey, rri.getActiveTime(),listJson); 		
		
	}
	 /**
     * @Description 得到实时数据缓存
     * @author zoul-c
     * @date key缓存对象名，pointId采集点，type当前还是上一周期，枚举RedisRealTimeDataTypeEnum
     */
	public String getRealtimeDataKey(String key,String pointId,String type)
	{		
		
		// 缓存信息
		RedisRegInfoVo rri = new RedisRegInfoVo();
		rri.setDataType(RedisDataTypeEnum.REALTIME.getIndex());// 实时信息,需要使用枚举类型
		rri.setStruct(RedisDataStructEnum.KEYVALUE.getIndex());// 缓存的数据类型
		rri.setServiceName(key);// 缓存服务名字(表名)
		// 缓存条件
		String sufKey = type+":pointId:" + pointId;//类型+pointId
		rri.setSufKey(sufKey);
	
		// 缓存信息
		rri.setdealType(0);// 不需要系统自动清除
		rri.setActiveTime(24*60 * 60); // 保存一天							
		// 不注册缓存,直接赋值		
		rri.updata();
        String rdkey=rri.getKey();
		return rdkey; 		
	}
	
	
	 /**
     * @Description 得到实时数据缓存
     * @author zoul-c
     * @date key缓存对象名，pointId采集点，type当前还是上一周期，枚举RedisRealTimeDataTypeEnum
     */
	public String getRealtimeData(String key,String pointId,String type)
	{
		
		// 建立缓存Service
		RedisBaseService rbs = new RedisBaseService();
		// 缓存信息
		RedisRegInfoVo rri = new RedisRegInfoVo();
		rri.setDataType(RedisDataTypeEnum.REALTIME.getIndex());// 实时信息,需要使用枚举类型
		rri.setStruct(RedisDataStructEnum.KEYVALUE.getIndex());// 缓存的数据类型
		rri.setServiceName(key);// 缓存服务名字(表名)
		// 缓存条件
		String sufKey = type+":pointId:" + pointId;//类型+pointId
		rri.setSufKey(sufKey);

		// 取值
		String listJson = rbs.getKvValue(rri.getKey());
		
		
		return listJson;
		
	}
	
	
	/**
     * @Description 得到实时数据缓存
     * @author zoul-c
	 * @param <T>
     * @date key缓存对象名，pointId采集点，type当前还是上一周期，枚举RedisRealTimeDataTypeEnum
     */
	public <T> T getRealtimeDataForObject(String key,Integer pointId,String type,Class<T> classOfT)
	{

		// 建立缓存Service
		RedisBaseService rbs = new RedisBaseService();
		// 缓存信息
		RedisRegInfoVo rri = new RedisRegInfoVo();
		rri.setDataType(RedisDataTypeEnum.REALTIME.getIndex());// 实时信息,需要使用枚举类型
		rri.setStruct(RedisDataStructEnum.KEYVALUE.getIndex());// 缓存的数据类型
		rri.setServiceName(key);// 缓存服务名字(表名)
		// 缓存条件
		String sufKey = type+":pointId:" + pointId;//类型+pointId
		rri.setSufKey(sufKey);

		// 取值
		String listJson = rbs.getKvValue(rri.getKey());
		Gson gson = new Gson();

		// 如果没有，自动更新缓存
		if (listJson!=null && listJson.length()>0) { //读到缓存
			Object val = gson.fromJson(listJson,classOfT);
			 return Primitives.wrap(classOfT).cast(val);
		}
		
		return null;


	}


	 /**
     * @Description 保存实时数据缓存
     * @author zoul-c
     * @date key缓存对象名，pointId采集点，type当前还是上一周期，枚举RedisRealTimeDataTypeEnum，val 值
     */
	public void setRealtimeDataForObject(String key,Integer pointId,String type,Object val)
	{
		Gson gson = new Gson();
		String listJson = gson.toJson(val);

		// 建立缓存Service
		RedisBaseService rbs = new RedisBaseService();
		// 缓存信息
		RedisRegInfoVo rri = new RedisRegInfoVo();
		rri.setDataType(RedisDataTypeEnum.REALTIME.getIndex());// 实时信息,需要使用枚举类型
		rri.setStruct(RedisDataStructEnum.KEYVALUE.getIndex());// 缓存的数据类型
		rri.setServiceName(key);// 缓存服务名字(表名)
		// 缓存条件
		String sufKey = type+":pointId:" + pointId;//类型+pointId
		rri.setSufKey(sufKey);

		// 注册缓存信息
		rri.setdealType(0);// 不需要系统自动清除
		rri.setActiveTime(24*60 * 60); // 保存一天
		// 注册缓存,并赋值
		rbs.setRedisRegInfo(rri, listJson);
	}



	/**
	 * @Description 得到数据缓存
	 * @author luxw
	 * @param <T>
	 * @date
	 */
	public <T> T getDataForObject(String serviceName,String key,String typeName,Class<T> classOfT)
	{

		// 建立缓存Service
		RedisBaseService rbs = new RedisBaseService();
		// 缓存信息
		RedisRegInfoVo rri = new RedisRegInfoVo();
		rri.setDataType(RedisDataTypeEnum.REALTIME.getIndex());// 实时信息,需要使用枚举类型
		rri.setStruct(RedisDataStructEnum.KEYVALUE.getIndex());// 缓存的数据类型
		rri.setServiceName(serviceName);// 缓存服务名字(表名)
		// 缓存条件
		String sufKey = typeName+":key:" + key;//类型+pointId
		rri.setSufKey(sufKey);

		// 取值
		String listJson = rbs.getKvValue(rri.getKey());
		Gson gson = new Gson();

		// 如果没有，自动更新缓存
		if (listJson!=null && listJson.length()>0) {
			Object val = gson.fromJson(listJson,classOfT);
			return Primitives.wrap(classOfT).cast(val);
		}
		
		return null;


	}

	/**
	 * @Description 得到数据缓存(Map类型)
	 * @author luxw
	 * @date
	 */
	public Map<String,Object> getDataForMap(String serviceName, String key, String typeName)
	{

		// 建立缓存Service
		RedisBaseService rbs = new RedisBaseService();
		// 缓存信息
		RedisRegInfoVo rri = new RedisRegInfoVo();
		rri.setDataType(RedisDataTypeEnum.REALTIME.getIndex());// 实时信息,需要使用枚举类型
		rri.setStruct(RedisDataStructEnum.KEYVALUE.getIndex());// 缓存的数据类型
		rri.setServiceName(serviceName);// 缓存服务名字(表名)
		// 缓存条件
		String sufKey = typeName+":key:" + key;//类型+pointId
		rri.setSufKey(sufKey);

		// 取值
		String listJson = rbs.getKvValue(rri.getKey());
		if (listJson!=null && listJson.length()>0) {
		Gson gson = new Gson();
		Map<String, Object> chartdata = gson.fromJson(listJson,
				new TypeToken<Map<String, Object>>() {
				}.getType());
		   return chartdata;
		}
		
			return null;		
	}


	/**
	 * @Description 保存数据缓存
	 * @author luxw
	 * @date
	 */
	public void setDataForObject(String serviceName,String key,String typeName,Object val)
	{
		Gson gson = new Gson();
		String listJson = gson.toJson(val);

		// 建立缓存Service
		RedisBaseService rbs = new RedisBaseService();
		// 缓存信息
		RedisRegInfoVo rri = new RedisRegInfoVo();
		rri.setDataType(RedisDataTypeEnum.REALTIME.getIndex());// 实时信息,需要使用枚举类型
		rri.setStruct(RedisDataStructEnum.KEYVALUE.getIndex());// 缓存的数据类型
		rri.setServiceName(serviceName);// 缓存服务名字(表名)
		// 缓存条件
		String sufKey =typeName+":key:" + key;//类型+pointId
		rri.setSufKey(sufKey);

		// 注册缓存信息
		rri.setdealType(0);// 不需要系统自动清除
		rri.setActiveTime(24*60 * 60); // 保存一天
		// 注册缓存,并赋值
		rbs.setRedisRegInfo(rri, listJson);
	}
	 /**
     * @Description 得到当天实时数据缓存
     * @author zoul-c
     * @date key缓存对象名，pointId采集点，type当前还是上一周期，枚举RedisRealTimeDataTypeEnum,日期 today
     */
	public ScadaValue getRealtimeData(String key,Integer pointId,String type,Date today)
	{
		ScadaValue val =getRealtimeData(ScadaValue.class.getName(),pointId,RedisRealTimeDataTypeEnum.REALTIME.getName());

		if(val==null||val.getCreatePeroidTime()==null)val=null;
		else  //日期不相等返回空
		{
		String s_today=DateUtil.date2String(today,"yyyyMMdd");
		String s_PeroidTime=DateUtil.date2String(val.getCreatePeroidTime(),"yyyyMMdd");
		//日期不相等
		if(!s_PeroidTime.equalsIgnoreCase(s_today))val=null;
		}
		return val;

	}
}
