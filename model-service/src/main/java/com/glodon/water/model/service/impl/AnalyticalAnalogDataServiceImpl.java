
package com.glodon.water.model.service.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.glodon.water.common.common.entity.AnalyticalAnalogData;
import com.glodon.water.common.common.enumpo.SummaryTypeEnum;
import com.glodon.water.common.util.DataUtil;
import com.glodon.water.common.util.DateUtil;
import com.glodon.water.model.dao.AnalyticalAnalogDataMapper;
import com.glodon.water.model.service.BaseService;
import com.glodon.water.model.service.IAnalyticalAnalogDataService;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
@Service
@Transactional
public class AnalyticalAnalogDataServiceImpl extends BaseService implements IAnalyticalAnalogDataService {

	private static final Logger logger = LogManager.getLogger(AnalyticalAnalogDataServiceImpl.class);

	
	@Autowired
	private AnalyticalAnalogDataMapper analogValueMapper;
	
	@Override
	public String getProjectIds(HashMap<String, Object> params) {
		String projects=null;
		// 读取项目列表
		List<Map<String, Object>> dlist = analogValueMapper.getProjectIds(params);
		if(dlist!=null)
		{
			for(int i=0;i<dlist.size();i++)
			{
				if(projects==null)projects=dlist.get(i).get("id").toString();
				else projects=projects+","+dlist.get(i).get("id").toString();
			}
		}
		return projects;
	}
	
	
	/**
	 * 集团版,获取指定时间节点之间的采集数据，指定编码的指定项目的数据，包括人工数据采集 map参数：projectId 项目ID,
	 * startTime、endTime 起始时间.codeNumber存放指标项 SummaryTypeEnum:汇总类型,null为不汇总
	 */
	@Override
	public List<AnalyticalAnalogData> getAnalogValueList(HashMap<String, Object> params, SummaryTypeEnum sumType,
			String newScales,String projects) {
		try {
			// 定义拼接sql语句
			String selsql = " ";
			String fromsql = "";
			String wheresql = "";
			String groupsql = "";
			String conditionsql = "";
			String table0 = null;
			String join ="  LEFT JOIN ";
			
			//处理项目id
			if(projects!=null&&projects.length()>0)
			{
				conditionsql=" v0.project_id in ("+projects+") ";
			}
			else
			{
				conditionsql=" 1=1 ";
			}
			
			// 处理时间参数
			Date now = new Date();
			String s_PeroidDay = DateUtil.date2String(now, "yyyy-MM-dd");
			Date startDate = DateUtil.string2Date(s_PeroidDay + " 00:00:00",
					"yyyy-MM-dd HH:mm:ss");
			Date endDate = now;
			if (params.get("startTime") != null) {
				startDate = (Date) params.get("startTime");
			}
			if (params.get("endTime") != null) {
				endDate = (Date) params.get("endTime");
			}
			if (endDate.after(now)) {
				endDate = now;
			}

			String s_startTime = DateUtil.date2String((Date) params.get("startTime"),
					"yyyy-MM-dd");
			String s_endTime = DateUtil.date2String((Date) params.get("endTime"),
					"yyyy-MM-dd");

			if (SummaryTypeEnum.BY_HOUR.getIndex().equals(sumType.getIndex())) {
				s_startTime = DateUtil.date2String((Date) params.get("startTime"),
						"yyyy-MM-dd HH:00:00");
				s_endTime = DateUtil.date2String((Date) params.get("endTime"),
						"yyyy-MM-dd HH:00:00");
				
				conditionsql+=" and v0.peroid_time>='"+s_startTime+"'";
				conditionsql+=" and v0.peroid_time<='"+s_endTime+"'";
			}
			else
			{
				conditionsql+=" and str_to_date(v0.peroid_time,'%Y-%m-%d')>='"+s_startTime+"'";
				conditionsql+=" and str_to_date(v0.peroid_time,'%Y-%m-%d')<='"+s_endTime+"'";
			}

			// 是否需要groupby
			boolean isgroupby = false;

			// 参数列表，value 为替换表名.字段名
			List<Map<String, String>> paramMap = new ArrayList<Map<String, String>>();
			// 读取公式配置
			List<Map<String, Object>> dlist = analogValueMapper.selectCodeformula(params);
			// 处理参数列表
			if (dlist != null && dlist.size() > 0 && dlist.get(0) != null) {
				Map<String, Object> dmap = dlist.get(0);
				// 处理精度
				if (newScales == null) {
					if (dmap.get("newScale") != null && dmap.get("newScale").toString().length() > 0) {
						newScales = dmap.get("newScale").toString();
					} else {
						newScales = "1";
					}
				}
				// 处理拼接语句
				if (dmap.get("formula_agg") == null || dmap.get("formula_agg").toString().length() <= 1) {
					selsql += " round(" + dmap.get("formula_sum").toString() + "," + newScales + " ) as value ";
				} else {// 需要聚合
					selsql = " round(" + dmap.get("formula_agg").toString() + "," + newScales
							+ " ) as value from (  SELECT ";
					selsql += " " + dmap.get("formula_sum").toString() + " as value ";
					if (SummaryTypeEnum.BY_HOUR.getIndex().equals(sumType.getIndex())) 
					{
						wheresql += "  and v0.peroid_time>='" + s_startTime	+ "' and v0.peroid_time<='" + s_endTime + "' ";
					}
					isgroupby = true;
				}

				// 处理timedif参数
				if (selsql.indexOf("[timedif]") >= 0) {
					long diff = (endDate.getTime() - startDate.getTime()) / 1000;
					selsql = selsql.replace("[timedif]", diff + "");
				}

				if (dmap.get("datanull_op") != null ) {
					if((int) dmap.get("datanull_op") == 1)table0 = " ";
					if((int) dmap.get("datanull_op") == 2)join ="  INNER JOIN ";;
				}

				// 处理参数列表
				String sParamlist = dmap.get("paramlist").toString();
				String[] list = sParamlist.split(",");
				for (int i = 0; i < list.length; i++) {
					Map<String, String> codeMap = new HashMap<String, String>();
					String[] maps = list[i].trim().split("_");
					if (maps.length > 1) {
						codeMap.put("key", list[i].trim());
						codeMap.put("data_source", maps[0].trim());
						codeMap.put("code_number", maps[1].trim());
						if (table0 == null)
							codeMap.put("table_name", "v" + i);
						else
							codeMap.put("table_name", "v" + (paramMap.size() + 1));
					}
					if (maps.length > 2) {
						if (maps[2].length() >= 4)
							codeMap.put("thing_key", maps[2].trim());
					}
					if (maps.length > 3) {
						codeMap.put("value_col", maps[3].trim());
					}
					if (codeMap.size() > 0)
						paramMap.add(codeMap);
				}

			} else {
				return null;
			}
			// 处理拼接sql语句
			for (int i = 0; i < paramMap.size(); i++) {
				Map<String, String> pmap = paramMap.get(i);
				String tableName = pmap.get("table_name");
				String oldChar = "[" + pmap.get("key") + "]";
				String newChar = "(" + tableName + ".value)";
				// val列名，默认value
				if (pmap.get("value_col") != null && pmap.get("value_col").length() > 0) {
					newChar = "(" + tableName + "." + pmap.get("value_col") + ")";
				}
				String condiction = " and " + tableName + ".code_number='" + pmap.get("code_number") + "' ";

				if (pmap.get("thing_key") != null) {
					condiction += " and " + tableName + ".thing_key='" + pmap.get("thing_key") + "' ";
				}

				// 读取数据源配置
				HashMap<String, Object> map = new HashMap<String, Object>();
				map.put("data_source", pmap.get("data_source"));
				List<Map<String, Object>> slist = analogValueMapper.selectDataSource(map);
				
				
				// 处理数据源信息
				if (slist != null && slist.size() > 0 && slist.get(0) != null) {
					Map<String, Object> smap = slist.get(0);

					// 缓存
					if ((int) smap.get("type") == 6) {

						HashMap<String, Object> param = new HashMap<String, Object>();
						param.put("projectId", params.get("projectId"));
						param.put("code_number", pmap.get("code_number"));
						param.put("thing_key", pmap.get("thing_key"));
						param.put("table_name", smap.get("day_table_name").toString());

						// 读取缓存配置
						List<Map<String, Object>> rlist = analogValueMapper.getRedisKey(param);
						// 调用计算
						Double analogValue = null;
						String s_analogValue = "null";
						if (rlist != null) {
							for (int j = 0; j < rlist.size(); j++) {
								Map<String, Object> dmap = rlist.get(j);
								if (dmap.get("scada_point_id") != null) {
									int pointid = Integer.parseInt(dmap.get("scada_point_id").toString());
								//	RedisDataUtil redisDataUtil = new RedisDataUtil();
									//Double analogValue1 = redisDataUtil.getRealtimeValue(pointid);
									Double analogValue1=Double.valueOf(pointid);
									if (analogValue1 != null) {
										if (analogValue == null)
											analogValue = 0.0;
										analogValue = analogValue + analogValue1;
									}
								}
							}

						}
						// 只有缓存
						if (paramMap.size() == 1) {
							List<AnalyticalAnalogData> a = new ArrayList<AnalyticalAnalogData>();
							AnalyticalAnalogData avd = new AnalyticalAnalogData();
							
							if(analogValue!=null)
							analogValue=DataUtil.getRoundingData(analogValue, Integer.parseInt(newScales));
							 
							avd.setValue(analogValue);
							if (params.get("projectId") != null)
								avd.setProjectId(Integer.parseInt(params.get("projectId").toString()));
							avd.setCodeNumber(pmap.get("code_number"));
							avd.setPeroidTime(s_startTime);
							a.add(avd);
							return a;
						} else {
							if (analogValue != null)
								s_analogValue = analogValue + "";
							selsql = selsql.replace(oldChar, s_analogValue);
							continue;
						}
					}
					// 常量
					if ((int) smap.get("type") == 5||(int) smap.get("type") == 0) {
						
						List<AnalyticalAnalogData> analogValuelist=null;
						if ((int) smap.get("type") == 5)
						{
						 HashMap<String, Object> param = new HashMap<String, Object>();						
						 param.put("endTime",endDate);
						 param.put("projectId",params.get("projectId"));
						 param.put("code_number", pmap.get("code_number")); 
						analogValuelist=analogValueMapper.getNormConstant(param);	
						}
						else
						{
							HashMap<String, Object> param = new HashMap<String, Object>();						
							 param.put("endTime",endDate);
							 param.put("projects",projects);
							 param.put("codeNumber", pmap.get("code_number")); 
							 analogValuelist=analogValueMapper.getNormConstantforCorp(param);		
						}
			
						//只有常量
						if(paramMap.size()==1)
						{							
						   return analogValuelist;
						}								 				
						//改成嵌套查询
						 Double analogValue1=null;
					        String s_analogValue="null";
						 if (analogValuelist != null && analogValuelist.size() > 0 && analogValuelist.get(0) != null) {
					    		AnalyticalAnalogData analogValue = analogValuelist.get(0);
					    		analogValue1=analogValue.getValue();   
					    		s_analogValue=analogValue1+"";
					       	}  
					   
						selsql = selsql.replace(oldChar, s_analogValue);
						continue;
						}

					//公式嵌套
					if ((int) smap.get("type") == 4) {
						
						 HashMap<String, Object> param = new HashMap<String, Object>();
						 param.put("startTime",startDate);
						 param.put("endTime",endDate);
						 param.put("projectId",params.get("projectId"));
						 param.put("codeNumber", pmap.get("code_number")); 
				         
						 List<AnalyticalAnalogData> analogValuelist=getAnalogValueList(param, sumType,null,projects);
					    	//调用计算
					        Double analogValue1=null;
					        String s_analogValue="null";
					    	if (analogValuelist != null && analogValuelist.size() > 0 && analogValuelist.get(0) != null) {
					    		AnalyticalAnalogData analogValue = analogValuelist.get(0);
					    		analogValue1=analogValue.getValue();   
					    		s_analogValue=analogValue1+"";
					       	}  
					   
						selsql = selsql.replace(oldChar, s_analogValue);
						continue;
						}
					
					
					if (smap.get("day_table_name") != null) {
						selsql = selsql.replace(oldChar, newChar);
						String day_table_name = smap.get("day_table_name").toString();
                        //去除模式不同
						if (table0 == null) {
							if (i == 0) {
								fromsql = " " + day_table_name + " " + tableName + " ";
								wheresql += condiction;
								if (smap.get("filter") != null && smap.get("filter").toString().length() > 0) {
									wheresql += " and "
											+ smap.get("filter").toString().replace(day_table_name, tableName) + " ";
								}
							} else {
								fromsql += join + day_table_name + " " + tableName + " ON v0.project_id="
										+ tableName + ".project_id and v0.peroid_time=" + tableName + ".peroid_time ";
								fromsql += condiction;
								if (smap.get("filter") != null && smap.get("filter").toString().length() > 0) {
									fromsql += " and "
											+ smap.get("filter").toString().replace(day_table_name, tableName) + " ";
								}
							}
						} else {
							if (i > 0) {
								table0 = table0 + " union ";
							}
							table0 = table0 + " SELECT " + tableName + ".peroid_time," + tableName + ".project_id from "
									+ day_table_name + " " + tableName + " where  " + tableName + ".project_id='"
									+ params.get("projectId") + "' and " + tableName + ".peroid_time>='" + s_startTime
									+ "' and " + tableName + ".peroid_time<='" + s_endTime + "' " + condiction;
							fromsql += "  LEFT JOIN " + day_table_name + " " + tableName + "  ON v0.project_id="
									+ tableName + ".project_id and v0.peroid_time=" + tableName + ".peroid_time ";
							fromsql += condiction;
							if (smap.get("filter") != null && smap.get("filter").toString().length() > 0) {
								fromsql += " and " + smap.get("filter").toString().replace(day_table_name, tableName)
										+ " ";
							}

						}
					}
				}
			}

			if (isgroupby) {
				if (sumType.getIndex() == SummaryTypeEnum.BY_HOUR.getIndex()) {
					groupsql = " GROUP BY DATE_FORMAT(v0.peroid_time,'%Y-%m-%d %H:00:00') ";
				} else if (sumType.getIndex() == SummaryTypeEnum.BY_DAY.getIndex()) {
					groupsql = " GROUP BY str_to_date(v0.peroid_time,'%Y-%m-%d') ";
				} else if (sumType.getIndex() == SummaryTypeEnum.BY_WEEK.getIndex()) {
					groupsql = " GROUP BY CONCAT(DATE_FORMAT(v0.peroid_time, '%x-%v'),'周') ";
				} else if (sumType.getIndex() == SummaryTypeEnum.BY_MONTH.getIndex()) {
					groupsql = " GROUP BY  CONCAT(DATE_FORMAT(v0.peroid_time, '%Y-%m'),'月') ";
				} else if (sumType.getIndex() == SummaryTypeEnum.BY_SEASON.getIndex()) {
					groupsql = " GROUP BY  CONCAT(YEAR(v0.peroid_time),'-',QUARTER(v0.peroid_time),'季度') ";
				} else if (sumType.getIndex() == SummaryTypeEnum.BY_YEAR.getIndex()) {
					groupsql = " GROUP BY  CONCAT(DATE_FORMAT(v0.peroid_time, '%Y'),'年') ";
				}
				groupsql = groupsql + ") gr";
			}

			if (table0 != null && table0.length() > 1) {
				fromsql = "(" + table0 + ") v0" + fromsql;
			}
			// 设置查询参数
			params.put("selsql", selsql);
			params.put("fromsql", fromsql);
			params.put("wheresql", wheresql);
			params.put("groupsql", groupsql);
			params.put("conditionsql", conditionsql);
			
			List<AnalyticalAnalogData> a = analogValueMapper.getAnalogDataforCorp(params);
			return a;

		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
			return null;
		}
	}
	

	
	// 读取公式配置
	private List<Map<String, String>> getCodeformula(List<Map<String, String>> paramMap, HashMap<String, Object> params,
			String newScales, boolean isMultiCode) {
		try {
			String codeNumberName = params.get("codeNumberName").toString();

			String selsql = " ,";

			if (params.get("selsql") != null) {
				selsql = params.get("selsql").toString() + selsql;
			}

			String isgroupby = null;

			// 读取公式配置
			List<Map<String, Object>> dlist = analogValueMapper.selectCodeformula(params);
			// 处理参数列表
			if (dlist != null && dlist.size() > 0 && dlist.get(0) != null) {
				Map<String, Object> dmap = dlist.get(0);
				// 处理精度
				if (newScales == null) {
					if (dmap.get("newScale") != null && dmap.get("newScale").toString().length() > 0) {
						newScales = dmap.get("newScale").toString();
					} else {
						newScales = "1";
					}
				}
				// 处理拼接语句
				if (isMultiCode) // 批量数据
				{
					selsql += " round(" + dmap.get("formula").toString() + "," + newScales + " ) as "
							+ codeNumberName + " ";
				} else {
					// 不需要聚合
					if (dmap.get("formula_agg") == null || dmap.get("formula_agg").toString().length() <= 1) {
						selsql += " round(" + dmap.get("formula").toString() + "," + newScales + " ) as value ";
					} else {// 需要聚合
						selsql = " round(" + dmap.get("formula_agg").toString() + "," + newScales
								+ " ) as value from (  SELECT ";
						selsql += " " + dmap.get("formula_sum").toString() + " as value ";
						isgroupby = "true";
					}
				}

				// 处理timedif参数
				if (selsql.indexOf("[timedif]") >= 0) {				
					selsql = selsql.replace("[timedif]", "v0.timedif");
				}

				// 处理参数列表
				String sParamlist = dmap.get("paramlist").toString();
				String[] list = sParamlist.split(",");
				for (int i = 0; i < list.length; i++) {
					Map<String, String> codeMap = new HashMap<String, String>();
					String[] maps = list[i].trim().split("_");
					if (maps.length > 1) {
						codeMap.put("key", list[i].trim());
						codeMap.put("data_source", maps[0].trim());
						codeMap.put("code_number", maps[1].trim());
						if (!isMultiCode) {
							codeMap.put("table_name", "v" + paramMap.size());
						} else
							codeMap.put("table_name", "v" + (paramMap.size() + 1));
					}
					if (maps.length > 2) {
						if (maps[2].length() >= 4)
							codeMap.put("thing_key", maps[2].trim());
					}
					if (maps.length > 3) {
						codeMap.put("value_col", maps[3].trim());
					}
					if (codeMap.size() > 0)
						paramMap.add(codeMap);
				}

			} else {
				return paramMap;
			}
			params.put("selsql", selsql);
			params.put("isgroupby", isgroupby);
			return paramMap;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
			return paramMap;
		}
	}

	// 处理拼接sql语句，集团版
	private HashMap<String, Object> dealCodeformulaSqlforCorp(HashMap<String, Object> params, SummaryTypeEnum sumType,
			List<Map<String, String>> paramMap, boolean isgroupby, boolean isMultiCode,String projects) {

		// 定义拼接sql语句
		String selsql = " ";
		String conditionsql = "";
		String fromsql = " ";
		String wheresql = " ";
		String groupsql = " ";
		String table0 = "";
		if (params.get("selsql") != null) {
			selsql = params.get("selsql").toString();
		}
		String peroid_time = "peroid_time";
		if (params.get("peroid_time") != null) {
			peroid_time = params.get("peroid_time").toString();
		}

		//处理项目id
		if(projects!=null&&projects.length()>0)
		{
			conditionsql=" v0.project_id in ("+projects+") ";
		}
		else
		{
			conditionsql=" 1=1 ";
		}
		
		// 时间参数
		String s_startTime = DateUtil.date2String((Date) params.get("startTime"),
				"yyyy-MM-dd");
		String s_endTime = DateUtil.date2String((Date) params.get("endTime"),
				"yyyy-MM-dd");
		if (sumType.getIndex() == SummaryTypeEnum.BY_HOUR.getIndex()) {
			s_startTime = DateUtil.date2String((Date) params.get("startTime"),
					"yyyy-MM-dd HH:00:00");
			s_endTime = DateUtil.date2String((Date) params.get("endTime"),
					"yyyy-MM-dd HH:00:00");
			
			conditionsql+=" and v0.peroid_time>='"+s_startTime+"'";
			conditionsql+=" and v0.peroid_time<='"+s_endTime+"'";
			table0 = "  SELECT * from v_dimen_hour where   project_id in ("+ projects +") and peroid_time>='"+s_startTime +	"' and peroid_time<='"+s_endTime+"' ";
			
			}		
		else
		{
			conditionsql+=" and str_to_date(v0.peroid_time,'%Y-%m-%d')>='"+s_startTime+"'";
			conditionsql+=" and str_to_date(v0.peroid_time,'%Y-%m-%d')<='"+s_endTime+"'";
			table0 = " SELECT *	FROM v_dimen_date WHERE project_id in ("+ projects +") and peroid_time>='"+s_startTime + "' and peroid_time<='"+s_endTime+"' ";
		}
		

		// 处理拼接sql语句,目前不支持只有常量的情况
		for (int i = 0; i < paramMap.size(); i++) {
			Map<String, String> pmap = paramMap.get(i);
			String tableName = pmap.get("table_name");
			String oldChar = "[" + pmap.get("key") + "]";
			String newChar = "(" + tableName + ".value)";
			// val列名，默认value
			if (pmap.get("value_col") != null && pmap.get("value_col").length() > 0) {
				newChar = "(" + tableName + "." + pmap.get("value_col") + ")";
			}
			String condiction = " and " + tableName + ".code_number='" + pmap.get("code_number") + "' ";

			if (pmap.get("thing_key") != null) {
				condiction += " and " + tableName + ".thing_key='" + pmap.get("thing_key") + "' ";
			}

			// 读取数据源配置
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("data_source", pmap.get("data_source"));
			List<Map<String, Object>> slist = analogValueMapper.selectDataSource(map);
			// 处理数据源信息
			if (slist != null && slist.size() > 0 && slist.get(0) != null) {
				Map<String, Object> smap = slist.get(0);
				// 处理常量
				if ((int) smap.get("type") == 5) {					
					 HashMap<String, Object> param = new HashMap<String, Object>();						
					 param.put("endTime",params.get("endTime"));
					 param.put("projects",projects);
					 param.put("code_number", pmap.get("code_number")); 
					List<AnalyticalAnalogData> analogValuelist=analogValueMapper.getNormConstant(param);						
					//改成嵌套查询
					 Double analogValue1=null;
				        String s_analogValue="null";
					 if (analogValuelist != null && analogValuelist.size() > 0 && analogValuelist.get(0) != null) {
				    		AnalyticalAnalogData analogValue = analogValuelist.get(0);
				    		analogValue1=analogValue.getValue();   
				    		s_analogValue=analogValue1+"";
				       	}  
				   
					selsql = selsql.replace(oldChar, s_analogValue);
					continue;
				}// 处理项目常量
				else if ((int) smap.get("type") == 0)
				{
					String scode_number="v0.a"+pmap.get("code_number");
					selsql = selsql.replace(oldChar, scode_number);
					continue;
				}

				if (smap.get("day_table_name") != null) {
					selsql = selsql.replace(oldChar, newChar);
					String day_table_name = smap.get("day_table_name").toString();

					if (!isMultiCode) {
						if (i == 0) {
							fromsql = " " + day_table_name + " " + tableName + " ";
							wheresql = condiction;
							if (smap.get("filter") != null && smap.get("filter").toString().length() > 0) {
								wheresql += " and " + smap.get("filter").toString().replace(day_table_name, tableName)
										+ " ";
							}
						} else {
							fromsql += "  LEFT JOIN " + day_table_name + " ON v0.project_id=" + tableName
									+ ".project_id and v0.peroid_time=" + tableName + ".peroid_time ";
							fromsql += condiction;
							if (smap.get("filter") != null && smap.get("filter").toString().length() > 0) {
								fromsql += " and " + smap.get("filter").toString().replace(day_table_name, tableName)
										+ " ";
							}
						}
					} else {											
						fromsql += "  LEFT JOIN " + day_table_name + " " + tableName + "  ON v0.project_id=" + tableName
								+ ".project_id and v0.peroid_time=" + tableName + ".peroid_time ";
						fromsql += condiction;
						if (smap.get("filter") != null && smap.get("filter").toString().length() > 0) {
							fromsql += " and " + smap.get("filter").toString().replace(day_table_name, tableName) + " ";
						}
					}

				}
			}
		}

		if (isgroupby) {
			String sel = "";
			String group = "";
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("sumType", sumType.getIndex());
			List<Map<String, Object>> dimlist = analogValueMapper.selectDimenSource(map);
			
			if (dimlist != null && dimlist.size() > 0) {
				
				group=dimlist.get(0).get("groupbysql").toString();
				
				if(dimlist.get(0).get("colsel")!=null)
				{
					sel=dimlist.get(0).get("colsel").toString();
				}
				else
				{
					sel=group;
				}
				
				if(dimlist.get(0).get("fromtable")!=null)
				{
					String fromtable=dimlist.get(0).get("fromtable").toString();
					fromsql=fromsql+" "+fromtable+" ";
				}				
				
				groupsql = " GROUP BY " + group + " order BY " + group;
				if (!isMultiCode)
					groupsql = groupsql + ") gr";
				else {
					if (table0 != null && table0.length() > 1) {
						fromsql = "(" + table0 + ") v0" + fromsql;
					}
					selsql = sel + " as " + peroid_time + selsql;
				}
			}
		}

		// 设置查询参数
		params.put("selsql", selsql);
		params.put("fromsql", fromsql);
		params.put("wheresql", wheresql);
		params.put("groupsql", groupsql);
		params.put("conditionsql", conditionsql);
		return params;
	}
	
	@Override
	/**
	 * 集团版，获取指定时间节点之间的采集数据，指定编码的指定项目的数据，包括人工数据采集 map参数：projectId 项目ID,
	 * startTime、endTime 起始时间.codeNumber存放指标项 SummaryTypeEnum:汇总类型,null为不汇总
	 * codeNumber 为指标编码列表
	 */
	public List<Map<String, Object>> getAnalogDataListforCorp(HashMap<String, Object> params,
			SummaryTypeEnum sumType, List<HashMap<String, String>> codeNumber,String projects) {
		try {
			// 处理时间参数
			Date now = new Date();
			String s_PeroidDay = DateUtil.date2String(now, "yyyy-MM-dd");
			Date startDate = DateUtil.string2Date(s_PeroidDay + " 00:00:00",
					"yyyy-MM-dd HH:mm:ss");
			Date endDate = now;
			if (params.get("startTime") != null) {
				startDate = (Date) params.get("startTime");
			}
			if (params.get("endTime") != null) {
				endDate = (Date) params.get("endTime");
			}
			if (endDate.after(now)) {
				endDate = now;
			}
			// 生成参数
			HashMap<String, Object> param = new HashMap<String, Object>();
			param.put("endTime", endDate);
			param.put("startTime", startDate);
			param.put("projectId", params.get("projectId"));
			param.put("peroid_time", params.get("peroid_time"));

			// 参数列表，value 为替换表名.字段名
			List<Map<String, String>> paramMap = new ArrayList<Map<String, String>>();

			for (int i = 0; i < codeNumber.size(); i++) {
				param.put("codeNumber", codeNumber.get(i).get("codeNumber"));
				param.put("codeNumberName", codeNumber.get(i).get("codeNumberName"));
				paramMap = getCodeformula(paramMap, param, codeNumber.get(i).get("newScales"), true);
			}

			// 是否需要groupby
			boolean isgroupby = true;

			if (paramMap == null || paramMap.size() <= 0)
				return null;

			param = dealCodeformulaSqlforCorp(param, sumType, paramMap, isgroupby, true,projects);
			List<Map<String, Object>> a = analogValueMapper.getAnalogDataListforCorp(param);
			return a;

		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
			return null;
		}
	}

}
