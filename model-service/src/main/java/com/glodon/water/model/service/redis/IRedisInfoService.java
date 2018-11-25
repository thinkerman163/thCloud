package com.glodon.water.model.service.redis;

import com.glodon.water.common.common.vo.RedisRegInfoVo;

/**
 * 缓存注册接口
 * Service接口
 * Created by zoul on 2017/1/31.
 */
public interface IRedisInfoService {

    Integer clearRedisInfo(String key);  
    boolean setKvValue(String value, int VALUE_TIMEOUT);
    String getKvValue(String key);
    void setRedisRegInfo(RedisRegInfoVo redisRegInfo,String value);
    String getRegKeybyTable(String tableName);
    String getRegInfo(String key);
	Integer clearRegInfo(String key, String tableName);
}
