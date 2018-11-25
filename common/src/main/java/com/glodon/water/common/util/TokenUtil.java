package com.glodon.water.common.util;

import java.text.ParseException;
import java.util.Base64;

import javax.servlet.http.HttpServletRequest;

import com.glodon.water.common.common.enumpo.TokenTypeEnum;
import com.glodon.water.common.model.request.TokenVO;

public class TokenUtil {
	
	private static long validTime=5*60000;
	private static long loginvalidTime=24*60*60000;
	
    public static TokenVO getInnerToken( String token) {    	
        	
    	TokenVO tvo=new TokenVO();        
    	try
    	{
        byte[] decoded = Base64.getDecoder().decode(token);
        String[] innerToken=new String(decoded).split("\\|");    
        
        TokenTypeEnum tokenTypeEnum=TokenTypeEnum.getSummaryTypeEnum(innerToken[0]);
        tvo.setTokenTypeEnum(tokenTypeEnum);
        if(tokenTypeEnum.getIndex()==TokenTypeEnum.REST.getIndex())
        {
        	tvo.setUserId(Integer.parseInt(innerToken[1]));
        	tvo.setSource(tokenTypeEnum.getName());
        }
        else
        {
        	tvo.setSource(innerToken[1]);
        }
       
        tvo.setLoginTimestamp(Long.parseLong(innerToken[2]));
        tvo.setTimestamp(Long.parseLong(innerToken[3]));
        tvo.setTokenTypeEnum(tokenTypeEnum);
    	}
    	catch (Exception e) {	
    		tvo=null;
			System.out.println(e.getMessage());			
		}
        return tvo;
   }
  
 public static boolean AuthInnerToken( String token,String iproute,String[] IPRouteList) {
	 
	    TokenVO tvo=TokenUtil.getInnerToken(token);  
	    //验证Token
	    if(tvo==null)return false;
	    //验证Token时间
	    if(!checkTimestamp(tvo))return false;
	  //验证iproute
	    TokenTypeEnum tokenTypeEnum=tvo.getTokenTypeEnum();
	    //非法请求
	    if(tokenTypeEnum.getIndex()==TokenTypeEnum.OTHER.getIndex())return false;
	    //rest请求
	    if(tokenTypeEnum.getIndex()==TokenTypeEnum.REST.getIndex())return true;
	    else //feign请求和内部请求验证ip
	    {
	    	return TokenUtil.checkIPRoute(iproute,IPRouteList);
	    }
      
   }
 
 public static boolean checkIPRoute(String iproute, String[] IPRouteList)
 {
	
	 if(IPRouteList!=null&&IPRouteList.length>0)
	 {
		 boolean sign=false;
		 for(int i=0;i<IPRouteList.length;i++)
		 {
			 if(IPRouteList[i].length()>5&&iproute.indexOf(IPRouteList[i])>=0) return true;
		 }
		 return sign; 
	 }
	 return true;
 }
 
 public static boolean checkTimestamp(TokenVO tvo)
 {
	 //检查时间搓
	 if(System.currentTimeMillis()-tvo.getTimestamp()>validTime)return false;
	 else  if(tvo.getTimestamp()-System.currentTimeMillis()>validTime)return false;
	 
	//检查登录时间
	 if(System.currentTimeMillis()-tvo.getLoginTimestamp()>loginvalidTime)return false;
	 else  if(tvo.getLoginTimestamp()-System.currentTimeMillis()>validTime)return false;
	 
	 return true;
 }
 
 public static String encoderInnerToken(String token)
 {
	 byte[] bytes = token.getBytes();         
     //Base64 Encoded       
     String innertoken=Base64.getEncoder().encodeToString(bytes);
	 
	 return innertoken;
 }
 
 public static String decoderInnerToken(String innertoken) {
	 byte[] decoded = Base64.getDecoder().decode(innertoken);
     String token=new String(decoded);    
	 return token;
 }
 
 
 public static String getIpAddr(HttpServletRequest request) {
	 String ip = request.getHeader("x-forwarded-for");
	 if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
	 ip = request.getHeader("http_client_ip");
	 }
	 if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
	 ip = request.getRemoteAddr();
	 }
	 if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
	 ip = request.getHeader("Proxy-Client-IP");
	 }
	 if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
	 ip = request.getHeader("WL-Proxy-Client-IP");
	 }
	 if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
	 ip = request.getHeader("HTTP_X_FORWARDED_FOR");
	 }
	 // 如果是多级代理，那么取第一个ip为客户ip
	/* if (ip != null && ip.indexOf(",") != -1) {
	 ip = ip.substring(ip.lastIndexOf(",") + 1, ip.length()).trim();
	 }*/
	 return ip;
	 } 
 
 
	
	public static void main(String[] args) throws ParseException {
		
		//String token="1234567";
		String token = TokenTypeEnum.REST.getName()+"|"+298+"|"+(System.currentTimeMillis()-validTime)+"|"+System.currentTimeMillis();
		String innertoken=TokenUtil.encoderInnerToken(token);		
		System.out.println(innertoken);	
		//token="MTIzNDU2";
		token=TokenUtil.decoderInnerToken(innertoken);	
		System.out.println(token);	
		
		
		  
	 /* 
		String innertoken=TokenTypeEnum.REST.getName()+"|1";		
		//Original byte[]
        byte[] bytes = innertoken.getBytes();         
        //Base64 Encoded       
        innertoken=Base64.getEncoder().encodeToString(bytes);
         String encoded = Base64.getEncoder().encodeToString(bytes);    
        //Base64 Decoded
        byte[] decoded = Base64.getDecoder().decode(encoded);
        innertoken=new String(decoded);     */ 
		
    }
	
}