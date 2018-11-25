package com.glodon.water.user.controller;

import com.glodon.water.common.common.entity.RoleFunction;
import com.glodon.water.common.common.enumpo.AuthorizationTypeEnum;
import com.glodon.water.user.service.IRoleFunctionService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/function")
public class FunctionController extends BaseController {
    protected static Logger logger = LoggerFactory.getLogger(FunctionController.class);

    @Autowired
    private IRoleFunctionService roleFunctionService;

    @RequestMapping("/modules")
    @ResponseBody
    public Map<String, Object> getModulesApi(Integer user_id, Integer corp_id) {
    	/*
   		 * 整理输入参数
   		 */
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("status", "00");
        List< Map<String, Object>> dataList = new ArrayList<>();
        resultMap.put("module", dataList);
        Map<String, Object> functionMap = new HashMap<>();
        List< Map<String, Object>> functionList = new ArrayList<>();
        resultMap.put("function", functionList);

        // 查询角色
        Map<String, Object> paramMap = new HashMap<String, Object>();
        if(user_id==null)user_id=this.getTokenVO().getUserId();
        if(user_id==null)
        {
        	resultMap.put("status", "03");
        	resultMap.put("err_code", "authentication.failed");
        	resultMap.put("err_msg", "Token was not recognized");
        	return resultMap;
        }
        paramMap.put("userId", user_id);
        paramMap.put("corpId", corp_id);
      
        // 调用服务
        List<RoleFunction> functions = roleFunctionService.selectRoleFunctionsByUser(paramMap);
        
        /*
   		 * 整理输出数据
   		 */
        if (null == functions || functions.size() == 0) {
            resultMap.put("status", "01");
            resultMap.put("err_code", "User has no role in project.");
            return resultMap;
        }

        List<Integer> moduleIdList = new ArrayList<>();
        for (RoleFunction roleFunction : functions) {
            if ("title".equals(roleFunction.getFunctionType())) {
                if (moduleIdList.contains(roleFunction.getFunctionId())) {
                    continue;
                }

                Map<String, Object> moduleMap = new HashMap<>();
                moduleMap.put("name", roleFunction.getFunctionName());
                moduleMap.put("code", roleFunction.getFunctionCode());
                moduleMap.put("path", roleFunction.getFunctionPath());
                moduleMap.put("img_url", roleFunction.getFunctionImgUrl());
                dataList.add(moduleMap);
                moduleIdList.add(roleFunction.getFunctionId());
            } else if ("menu".equals(roleFunction.getFunctionType())) {
                String authorizationStr = roleFunction.getAuthorization();
                if(authorizationStr==null||authorizationStr.length()<1)authorizationStr="|1|";
                if (functionMap.containsKey(roleFunction.getFunctionCode()))
                {
                	String authorization_old=functionMap.get(roleFunction.getFunctionCode()).toString();
                    if(authorization_old.indexOf(authorizationStr)>=0) continue;
                    else
                    {
                    	String[] authorization=authorizationStr.split("\\|");
                    	for(int i=0;i<authorization.length;i++)
                    	{
                    		if(authorization[i].length()==1)
                    		{
                    			if(authorization_old.indexOf("|"+authorization[i]+"|")<0)
                    			{
                    				authorization_old=authorization_old+authorization[i]+"|";
                    			}
                    		}
                    	}
                    	
                    	authorizationStr=authorization_old;
                    }
                }
                functionMap.put(roleFunction.getFunctionCode(), authorizationStr);
                Map<String, Object> function = new HashMap<>();
                function.put("name", roleFunction.getFunctionName());
                function.put("code", roleFunction.getFunctionCode());
                function.put("img_url", roleFunction.getFunctionImgUrl());
                function.put("module_id", roleFunction.getModuleId());
                function.put("path", roleFunction.getFunctionPath());
                function.put("authorization", authorizationStr);
                functionList.add(function);
            }
        }


        return resultMap;
    }
  
    
    @RequestMapping("/functionAuthorization")
    @ResponseBody
    public Map<String, Object> getFunctionAuthorization(Integer user_id, Integer corp_id,String functionCode,Byte optype) {
        
    	/*
   		 * 整理输入参数
   		 */
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("status", "00");
        Map<String, Object> functionMap = new HashMap<>();
        resultMap.put("function", functionMap);

        // 查询角色
        Map<String, Object> paramMap = new HashMap<String, Object>();
        if(user_id==null)user_id=this.getTokenVO().getUserId();
        if(user_id==null)
        {
        	resultMap.put("status", "03");
        	resultMap.put("err_code", "authentication.failed");
        	resultMap.put("err_msg", "Token was not recognized");
        	return resultMap;
        }
        
        paramMap.put("userId", user_id);
        paramMap.put("corpId", corp_id);     
        paramMap.put("functionCode", functionCode);

        // 调用服务
        List<RoleFunction> functions = roleFunctionService.selectRoleFunctionsByUser(paramMap);
        
        
        /*
   		 * 整理输出数据
   		 */
        if(optype!=null)
        {        	
           functionMap.put( AuthorizationTypeEnum.getName(optype.byteValue()), 0);
        }
        if (null == functions || functions.size() == 0) {           
             resultMap.put("err_code", "User has no role_function for "+functionCode);        	
            return resultMap;
        }
        for (RoleFunction roleFunction : functions) { 
        	 String authorizationStr = roleFunction.getAuthorization();
            	if (authorizationStr!=null&&authorizationStr.length()>0) {
            		String[] chartDivs = authorizationStr.trim().split("\\|");
            		for(int i=1;i<chartDivs.length;i++)
            		{
            			try
            			{
            			 byte type=Byte.parseByte(chartDivs[i]);
            			 if(optype==null||optype==type)
            			 functionMap.put( AuthorizationTypeEnum.getName(type), 1);
            			}
            			catch (Exception e) {        					
        					// 记录失败日志
        					e.printStackTrace();
        				}
            		}
               
            }
        }

        return resultMap;
    }
    
    
    @RequestMapping("/functionbypath")
    @ResponseBody
    public Map<String, Object> selectFunctionsByPath(Integer user_id, Integer corp_id,String path) {
    	  
    	/*
   		 * 整理输入参数
   		 */
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("status", "00");
        Map<String, Object> functionMap = new HashMap<>();
        resultMap.put("function", functionMap);

        // 查询角色
        Map<String, Object> paramMap = new HashMap<String, Object>();
              
        if(user_id==null)user_id=this.getTokenVO().getUserId();
        if(user_id==null)
        {
        	resultMap.put("status", "03");
        	resultMap.put("err_code", "authentication.failed");
        	resultMap.put("err_msg", "Token was not recognized");
        	return resultMap;
        }
        paramMap.put("userId", user_id);  
        paramMap.put("corpId", corp_id);     
        paramMap.put("path", path);

        // 调用服务
        List<RoleFunction> functions = roleFunctionService.selectRoleFunctionsByPath(paramMap);
                
        
    	/*
   		 * 整理输出参数
   		 */
        functionMap.put("functions", functions);    

        return resultMap;
    }
    

}
