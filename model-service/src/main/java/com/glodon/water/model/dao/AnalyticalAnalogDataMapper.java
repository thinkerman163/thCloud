package com.glodon.water.model.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.glodon.water.common.common.entity.AnalyticalAnalogData;

import tk.mybatis.mapper.common.Mapper;

public interface AnalyticalAnalogDataMapper extends Mapper<AnalyticalAnalogData> {

	List<Map<String, Object>> selectCodeformula(HashMap<String, Object> params);

	List<Map<String, Object>> selectDataSource(HashMap<String, Object> params);

	List<AnalyticalAnalogData> getAnalogDataList(HashMap<String, Object> params);

	List<Map<String, Object>> getAnalogDataListforMultiCode(HashMap<String, Object> params);

	List<AnalyticalAnalogData> getNormConstant(HashMap<String, Object> params);

	List<Map<String, Object>> getRedisKey(HashMap<String, Object> params);

	List<AnalyticalAnalogData> getAnalogDataforCorp(HashMap<String, Object> params);

	List<Map<String, Object>> getProjectIds(HashMap<String, Object> params);

	List<Map<String, Object>> selectDimenSource(HashMap<String, Object> map);

	List<Map<String, Object>> getAnalogDataListforCorp(HashMap<String, Object> param);

	List<AnalyticalAnalogData> getNormConstantforCorp(HashMap<String, Object> param);
}