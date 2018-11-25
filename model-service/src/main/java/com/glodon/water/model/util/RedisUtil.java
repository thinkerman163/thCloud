
package com.glodon.water.model.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.glodon.water.model.config.redis.RedisConstant;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.Pipeline;

public final class RedisUtil {

    // Redis服务器IP
    private static String ADDR;

    // Redis的端口号
    private static String PASSWORD=null;
    
    // Redis的密码
    private static int PORT;

    static {
        try {
            ADDR = RedisConstant.sADDR;
            PORT = Integer.parseInt(RedisConstant.sPORT);
            PASSWORD=RedisConstant.sPASSWORD;
            if(PASSWORD!=null&&PASSWORD.length()==0)
            {
            	PASSWORD=null;
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();            
        } catch (Exception e) {
            e.printStackTrace();
            ADDR="10.129.56.161";
            PORT=6379;
        }
    }

    // 访问密码
    // private static String AUTH = "admin";

    // 可用连接实例的最大数目，默认值为8；
    // 如果赋值为-1，则表示不限制；如果pool已经分配了maxActive个jedis实例，则此时pool的状态为exhausted(耗尽)。
    // private static int MAX_ACTIVE = 1024;

    // 控制一个pool最多有多少个状态为idle(空闲的)的jedis实例，默认值也是8。
    private static int MAX_IDLE = 200;

    // 等待可用连接的最大时间，单位毫秒，默认值为-1，表示永不超时。如果超过等待时间，则直接抛出JedisConnectionException；
    // private static int MAX_WAIT = 10000;

    private static int TIMEOUT = 1000;

    // 在borrow一个jedis实例时，是否提前进行validate操作；如果为true，则得到的jedis实例均是可用的；
    private static boolean TEST_ON_BORROW = true;

    private static JedisPool jedisPool = null;

    // 值过期时间（单位：秒），两分钟
    private static int VALUE_TIMEOUT = 60 * 2;
    // 值过期时间（单位：秒）,两天
    private static int MAX_TIMEOUT = 24  * 60 * 60 * 2;

   
    /**
     * 缓存key类型，水质监测自动模式
     */
    public static String KEY_TYPE_WATER_QUALITY_AUTO = "WQ_AUTO_T";
    /**
     * 缓存key类型，水质监测手动模式
     */
    public static String KEY_TYPE_WATER_QUALITY_HAND = "WQ_HAND";
    /**
     * 缓存key类型，水质监测自动模式预警
     */
    public static String KEY_TYPE_WARING_WATER_QUALITY_AUTO = "WAR_WQ_AUTO_T";
    /**
     * 缓存key类型，水质监测手动模式预警
     */
    public static String KEY_TYPE_WARING_WATER_QUALITY_HAND = "WAR_WQ_HAND";
    /**
     * 缓存key类型，3D模型文件的fileId，注意后面要加上项目ID(子模型还需要加上子模型构件ID)后缀来使用
     * null表示没有缓存值，空串表示数据库没有对应fileId
     */
    public static String KEY_TYPE_3D_MODEL_FILE_ID_ = "3D_MODEL_FILE_ID_";

    /**
     * 缓存key类型，根据fileId从BIMFACE获取到的viewToken，有效期五分钟，需要加上fileId后缀来使用
     * null表示没有缓存值
     */
    public static String KEY_TYPE_VIEW_TOKEN_ = "VIEW_TOKEN_";

    /**
     * 初始化Redis连接池
     */
    static {
        try {
            JedisPoolConfig config = new JedisPoolConfig();
            // config.setMaxActive(MAX_ACTIVE);
            config.setMaxIdle(MAX_IDLE);
            // config.setMaxWait(MAX_WAIT);
            config.setTestOnBorrow(TEST_ON_BORROW);
            jedisPool = new JedisPool(config, ADDR, PORT, TIMEOUT, PASSWORD);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取Jedis实例
     */
    public static synchronized Jedis getJedis() {
        try {
            if (jedisPool != null) {
                Jedis resource = jedisPool.getResource();
                return resource;
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 释放jedis资源
     */
    @SuppressWarnings("deprecation")
    public static void returnResource(final Jedis jedis) {
        if (jedis != null) {
            jedisPool.returnResource(jedis);
        }
    }

    public static void setValue(String key, String value) {
    	//if(timeOut<=1)timeOut=VALUE_TIMEOUT;
        setValue(key, VALUE_TIMEOUT, value);
    }

    /**
     * 
     * @Description 设置值
     * @author youps-a
     * @date 2016年9月21日 下午12:55:46
     * @param key key
     * @param timeOut 超时时间
     * @param value 值
     */
    public static void setValue(String key, int timeOut, String value) {
        Jedis jedis = getJedis();
        try {
        	//设置最大过期时间
        	if(timeOut>MAX_TIMEOUT)timeOut=MAX_TIMEOUT;
            jedis.setex(key, timeOut, value);
        } finally {
            jedis.close();
        }
    }
    
    
    
    /**
     * 
     * @Description 设置值
     * @author zouli
     * @date 2016年9月21日 下午12:55:46
     * @param key key
     * @param timeOut 超时时间
     * @param value 值
     */
    public static void setRealTimeValueforPipeline(int timeOut,List<Map<String, byte[]>> hashValues) {
        Jedis jedis = getJedis();
        Pipeline pipe=jedis.pipelined();
        try {
        	//设置最大过期时间
        	if(timeOut>MAX_TIMEOUT)timeOut=MAX_TIMEOUT;
        	for(Map<String, byte[]> hashValue: hashValues)
      	  {
        		byte[] lastkey=hashValue.get("lastKey");  
        		byte[] curKey=hashValue.get("curKey");  
        		byte[] curValue=hashValue.get("curValue");  
        		byte[] lastValue=hashValue.get("lastValue");
        		
        		if(lastkey!=null)
        		{	//if(lastValue==null&&curKey!=null)
                	//	lastValue=jedis.get(curKey);   
        			if(lastValue!=null)
        				pipe.setex(lastkey, timeOut, lastValue);        			
        		}
        		
        		if(curKey!=null && curValue!= null)
        			pipe.setex(curKey, timeOut, curValue);               
      	  }
        	pipe.sync();
        	
        	try {
				pipe.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	
        } finally {        	
            jedis.close();            
        }
    }
      
    
    /**
     * 
     * @Description 设置值
     * @author zouli
     * @date 2016年9月21日 下午12:55:46
     * @param key key
     * @param timeOut 超时时间
     * @param value 值
     */
    public static void setRealTimeValue(int timeOut,List<Map<String, byte[]>> hashValues) {
        Jedis jedis = getJedis();
        try {
        	//设置最大过期时间
        	if(timeOut>MAX_TIMEOUT)timeOut=MAX_TIMEOUT;
        	for(Map<String, byte[]> hashValue: hashValues)
      	  {
        		byte[] lastkey=hashValue.get("lastKey");  
        		byte[] curKey=hashValue.get("curKey");  
        		byte[] curValue=hashValue.get("curValue");  
        		byte[] lastValue=hashValue.get("lastValue");
        		
        		if(lastkey!=null)
        		{
        			if(lastValue==null&&curKey!=null)
                		lastValue=jedis.get(curKey);   
        			if(lastValue!=null)
        			jedis.setex(lastkey, timeOut, lastValue);
        		}
        		
        		if(curKey!=null && curValue!= null)
        			jedis.setex(curKey, timeOut, curValue);  		    		
              
      	  }
        } finally {
            jedis.close();
        }
    }
    
    
    
    /**
     * 
     * @Description 设置值
     * @author zouli
     * @date 2016年9月21日 下午12:55:46
     * @param key key
     * @param timeOut 超时时间
     * @param value 值
     */
    public static void setValue(int timeOut, List<byte[][]> hashValues) {
        Jedis jedis = getJedis();
        try {
        	//设置最大过期时间
        	if(timeOut>MAX_TIMEOUT)timeOut=MAX_TIMEOUT;
        	for(byte[][] hashValue : hashValues)
      	  {
            jedis.setex(hashValue[0], timeOut, hashValue[1]);
      	  }
        } finally {
            jedis.close();
        }
    }
    
  

    /**
     * 
     * @Description 取值
     * @author youps-a
     * @date 2016年9月21日 下午12:56:12
     * @param key key
     */
    public static String getValue(String key) {
        Jedis jedis = getJedis();
        try {
        	return jedis.get(key);
        } finally {
            if (null != jedis) {
                jedis.close();
            }
        }
    }
    
    
    /**
     * 
     * @Description 删除一个值
     * @author youps-a
     * @date 2016年9月21日 下午12:56:12
     * @param key key
     */
    public static long delValue(String key) {
        Jedis jedis = getJedis();
        try {
            return jedis.del(key);
        } finally {
            if (null != jedis) {
                jedis.close();
            }
        }
      //  return 0;
    }
    

    public static String getKey(String type, int id, long period) {
        return type + "_" + id + "_" + period;
    } 

    /**
     * 
     * @Description 设置哈希缓存
     * @author zoul-c
     * @date 2017年2月8日 下午5:56:12
     * @param hsetkey 哈希key
     * @param hashKey field名
     * @param value 值
     */
    public static boolean setHash(String hsetkey,String hashKey,String hashValue) {
    	
    	boolean result=true;
    	 Jedis jedis = getJedis();
         try {
             //jedis.setex(key, timeOut, value);
             jedis.hset(hsetkey, hashKey, hashValue);           
         } 
         catch (Exception e) {
             e.printStackTrace();
             result=false;
         }        
         finally {
             jedis.close();
         }
        return result;
    }
    
    
    /**
     * 
     * @Description 取哈希缓存
     * @author zoul-c
     * @date 2017年2月8日 下午5:56:12
     * @param hsetkey 哈希key
     * @param hashKey field名，数组
     */
     public static  String getHash(String hsetkey,String hashKey) {   	
    	 Jedis jedis = getJedis();    
    	 String hashValue = null;
         try {
              hashValue =jedis.hget(hsetkey, hashKey); 
         } 
         catch (Exception e) {
             e.printStackTrace();            
         }        
         finally {
             jedis.close();
         }
        return hashValue;
    }
     
    /**
     * 
     * @Description 取哈希缓存
     * @author zoul-c
     * @date 2017年2月8日 下午5:56:12
     * @param hsetkey 哈希key
     * @param hashKey field名，数组
     */
     public static  List<String> getHash(String hsetkey,String[] hashKey) {   	
    	 Jedis jedis = getJedis();
    	 List<String> listValue=new ArrayList<String>();
         try {
             if(hashKey.length==1)
        	 {
               String hashValue =jedis.hget(hsetkey, hashKey[0]); 
               listValue.add(hashValue);
        	 }
        	 else
        		 listValue=jedis.hmget(hsetkey, hashKey);  
         } 
         catch (Exception e) {
             e.printStackTrace();            
         }        
         finally {
             jedis.close();
         }
        return listValue;
    }

     
     /**
      * 
      * @Description 存储REDIS队列 顺序存储
      * @author zoul-c
      * @date 2017年2月8日 下午5:56:12
      * @param topic 哈希key
      * @param hashValues 数组数据
      */
      public static  int lpush(byte[] topic,List<byte[]> hashValues) {   	
     	 Jedis jedis = getJedis();
     	int result=0;
          try {
        	  for(byte[] hashValue : hashValues)
        	  {
        	  jedis.lpush(topic, hashValue);
        	  result ++;
        	  }
          } 
          catch (Exception e) {
              e.printStackTrace();            
          }        
          finally {
              jedis.close();
          }
         return result;
     }
    
      
      /**
       * 
       * @Description 存储REDIS队列 顺序存储
       * @author zoul-c
       * @date 2017年2月8日 下午5:56:12
       * @param topic 哈希key
       * @param hashValues 数组数据
       */
       public static  long lsize(byte[] topic) {   	
      	 Jedis jedis = getJedis();
      	long result=0;
           try {
         	 
        	   result=jedis.llen(topic);
         	       	 
           } 
           catch (Exception e) {
               e.printStackTrace();            
           }        
           finally {
               jedis.close();
           }
          return result;
      }
       
      
      
      /**
       * 
       * @Description 存储REDIS队列 顺序存储
       * @author zoul-c
       * @date 2017年2月8日 下午5:56:12
       * @param topic 哈希key
       * @param count 数量
       */
       public static  List<byte[]> rpop(byte[] topic,int count) {     	   
    	  
      	 Jedis jedis = getJedis();
      	 List<byte[]> listValue=new ArrayList<byte[]>();
           try {
         	  for(int i=0;i<count;i++)
         	  {
         		 byte[] q = jedis.rpop(topic);
         		 if(q!=null)
         		 listValue.add(q);
         		 else
         		 {break; }
         			 
         	  }
           } 
           catch (Exception e) {
               e.printStackTrace();            
           }        
           finally {
               jedis.close();
           }
          return listValue;
      }

}
