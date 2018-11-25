
package com.glodon.water.model.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.glodon.water.common.common.entity.AnalyticalAnalogData;
import com.glodon.water.common.common.enumpo.SummaryTypeEnum;


/**
 * @description 采集数据基础service
 * @author (作者) chenyin
 * @date (开发日期) 2016-08-16 16:39:23
 * @company (开发公司) 广联达软件股份有限公司
 * @copyright (版权) 本文件归广联达软件股份有限公司所有
 * @version (版本) V1.0
 * @since (该版本支持的JDK版本) 1.7
 * @modify (修改) 第N次修改：时间、修改人;修改说明
 * @review (审核人) 审核人名称
 */
public interface IAnalyticalAnalogDataService {

 

	List<AnalyticalAnalogData> getAnalogValueList(HashMap<String, Object> params, SummaryTypeEnum sumType, String newScales,
			String projects);


	String getProjectIds(HashMap<String, Object> params);


	List<Map<String, Object>> getAnalogDataListforCorp(HashMap<String, Object> params, SummaryTypeEnum sumType,
			List<HashMap<String, String>> codeNumber, String projects);


}
