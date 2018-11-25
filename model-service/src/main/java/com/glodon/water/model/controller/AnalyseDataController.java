package com.glodon.water.model.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.glodon.water.common.common.entity.AnalyticalAnalogData;
import com.glodon.water.common.common.enumpo.SummaryTypeEnum;
import com.glodon.water.common.common.vo.AjaxReturnVo;
import com.glodon.water.common.common.vo.DateVo;
import com.glodon.water.common.util.DateUtil;
import com.glodon.water.model.service.IAnalyticalAnalogDataService;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RefreshScope
@Controller
@RequestMapping("/Analyse")
public class AnalyseDataController  extends BaseController{
    
    @Autowired
    private IAnalyticalAnalogDataService analyticalService;
    


    /**
   	 * @Description 分析-数据(按新的公式计算)
   	 * @return Object
   	 * @author zouli
   	 * @date 2018年07月25日 上午8:38:33
   	 */
   	@RequestMapping("/getAnalyseDataforCrop")	
   	@ResponseBody
   	public Object getAnalyseDataforCrop(String codeNumber,DateVo dateVo,String sumType) throws ParseException {
   		/*
   		 * 整理输入参数
   		 */
   		// 计算周期
   		SummaryTypeEnum summaryTypeEnum = SummaryTypeEnum.BY_ALL;
   		if (sumType != null && sumType.length() > 1) {
   			summaryTypeEnum = SummaryTypeEnum.getSummaryTypeEnum(sumType);
   		}
   		// 时间
   		Date startDate = dateVo.getStartDate();
   		Date endDate = dateVo.getEndDate();
   		Date now = new Date();
   		
   		if (startDate == null) {
   			String s_PeroidDay =DateUtil.date2String(now, "yyyy-MM-dd");
   			if (SummaryTypeEnum.BY_MONTH.getIndex().equals(summaryTypeEnum.getIndex())) {
   				// 默认当年01月01日
   				s_PeroidDay = DateUtil.date2String(now, "yyyy-01-01");
   			}
   			startDate = DateUtil.string2Date(s_PeroidDay + " 00:00:00",
   					"yyyy-MM-dd HH:mm:ss");
   		}
   		if (endDate == null || endDate.after(now)) {
   			endDate = now;
   		}
   		Integer projectId = dateVo.getProjectId();   
   		String[] codeNumbers = codeNumber.split("-");		

   		
   		HashMap<String, Object> paramspro = new HashMap<String, Object>();
   		String region=dateVo.getRegion();
   		String corpId=dateVo.getCorpId();
   		if (region != null && region.length() > 0) {
   			paramspro.put("regions", region.split(","));
   		}
   		paramspro.put("corp_id", corpId);
   		String strProject = analyticalService.getProjectIds(paramspro);
   		
   		
   		HashMap<String, Object> params = new HashMap<String, Object>();
   		params.put("startTime", startDate);
   		params.put("endTime", endDate);
   		params.put("projectId", projectId);
   		// 时间字段名称
   		params.put("peroid_time", "peroid_time");

   		// 整理调用参数
   		List<HashMap<String, String>> codeNumberlist = new ArrayList<HashMap<String, String>>();
   		for(int i=0;i<codeNumbers.length;i++)
   		{
   		HashMap<String, String> param = new HashMap<String, String>();
   		param.put("codeNumber", codeNumbers[i]);
   		param.put("codeNumberName", "a"+codeNumbers[i]);   		
   		codeNumberlist.add(param);
   		}		
   		
   		// 根据查询条件获取数据
   		List<Map<String, Object>> mudAnalyseDate = analyticalService.getAnalogDataListforCorp(params,	summaryTypeEnum, codeNumberlist,strProject);
   	
   		
   		/*
   		 * 整理输出参数
   		 */   	
   		Map<String, Object> returnMap = new HashMap<String, Object>();
   		returnMap.put("data", mudAnalyseDate);
   		
   		return returnMap;

   	}
   	
    @RequestMapping("/getCockpitSumDataforCrop")
    @ResponseBody
    public AjaxReturnVo getCockpitSumDataforCrop(String codeNumber,DateVo dateVo,String sumType,String newScale,String region,String corpId) {
    	/*
   		 * 整理输入参数
   		 */
		// 计算周期
		SummaryTypeEnum summaryTypeEnum = SummaryTypeEnum.BY_ALL;
		if (sumType != null && sumType.length() > 1) {
			summaryTypeEnum = SummaryTypeEnum.getSummaryTypeEnum(sumType);
		}
		// 时间
		 Date startDate = dateVo.getStartDate();
         Date endDate = dateVo.getEndDate();
		Date now = new Date();
		
		if (startDate == null) {
			String s_PeroidDay = DateUtil.date2String(now, "yyyy-MM-dd");
			if (SummaryTypeEnum.BY_MONTH.getIndex().equals(summaryTypeEnum.getIndex())) {				
				s_PeroidDay = DateUtil.date2String(now, "yyyy-01-01");
			}
			startDate = DateUtil.string2Date(s_PeroidDay + " 00:00:00",
					"yyyy-MM-dd HH:mm:ss"); 
		}
		if (endDate == null || endDate.after(now)) {
			endDate = now;
		}
		Integer projectId = dateVo.getProjectId();
		String[] codeNumbers = codeNumber.split("-");
		// 精度
		String[] newScales = null;
		if (newScale != null)
			newScales = newScale.split("-");

		HashMap<String, Object> paramspro = new HashMap<String, Object>();
		if(region!=null &&region.length()>0)
		{
		  paramspro.put("regions", region.split(","));
		}
		paramspro.put("corp_id", corpId);
		String strProject= analyticalService.getProjectIds(paramspro);
				
		
			
		/*
   		 * 调用计算，整理输出参数
   		 */
		Map<String, Object> dataMap = new HashMap<>();
		for (int i = 0; i < codeNumbers.length; i++) {
			HashMap<String, Object> params = new HashMap<String, Object>();
			params.put("startTime", startDate);
			params.put("endTime", endDate);
			params.put("projectId", projectId);
			params.put("codeNumber", codeNumbers[i]);
			// 保留位数
			String inewScale = null;
			if (newScales != null && newScales.length > i && newScales[i].length() > 0) {
				inewScale = newScales[i];
			}
			List<AnalyticalAnalogData> analogValuelist = analyticalService.getAnalogValueList(params, summaryTypeEnum,
					inewScale,strProject);
			// 调用计算
			Double analogValue1 = null;
			if (analogValuelist != null && analogValuelist.size() > 0 && analogValuelist.get(0) != null) {
				AnalyticalAnalogData analogValue = analogValuelist.get(0);
				analogValue1 = analogValue.getValue();
			}
			dataMap.put("a" + codeNumbers[i], analogValue1);
		}
		
	
		
		return  returnAjaxSuccess(dataMap);
	}
   
}
