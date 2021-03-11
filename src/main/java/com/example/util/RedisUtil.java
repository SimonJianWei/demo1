package com.example.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: chenjianwei
 * @Date: 2020/11/26/14:08
 * @Description:
 */
public class RedisUtil {
    @Autowired
    private RedisTemplate<String, String> redisTemplate;


    //-------------------redis中Hash结构的数据处理-----------------------------------
    /**
     * 删除整个HashKey
     * @param key
     * @return
     */
    public boolean deleteHashAll(String key) {
        boolean result = false;
        try {
            redisTemplate.delete(key);
            result = true;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return result;
    }
    /**
     * 删除指定HashKey
     * @param key
     * @param hashKeys
     * @return
     */
    public boolean deleteHashValue(String key ,Object hashKeys) {
        boolean result = false;
        try {
            redisTemplate.opsForHash().delete(key, hashKeys);
            result = true;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 确定HashKey是否存在
     */
    public boolean hasHashKey(String key,Object hashKey) {
        boolean result = false;
        try {
            result = redisTemplate.opsForHash().hasKey(key, hashKey);
        } catch (Exception e) {
            // TODO: handle exception
        }
        return result;
    }

    /**
     * 获取整个Hash结构的值
     * @param key
     * @return
     */
    public Map<Object,Object> getAllHash(String key){
        Map<Object, Object> map = new HashMap<Object,Object>();
        try {
            map = redisTemplate.opsForHash().entries(key);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return map;
    }

    /**
     * 获取Hash的值
     * @param key
     * @param hashKey
     * @return
     */
    public String getHashValue(String key,Object hashKey) {
        Object obj = "";
        try {
            obj = redisTemplate.opsForHash().get(key, hashKey);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return obj.toString();
    }

    /**
     * 获取hash结构的所有key
     * @param key
     * @return
     */
    public Set<Object> getAllHashKey(String key){
        Set<Object> set = new HashSet<Object>();
        try {
            set = redisTemplate.opsForHash().keys(key);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return set;
    }

    /**
     * 存储Hash结构的数据
     * @param key
     * @param hashKey
     * @param value
     * @return
     */
    public boolean putHashValue(String key ,Object hashKey,Object value) {
        boolean result = false;
        try {
            redisTemplate.opsForHash().put(key, hashKey, value);
            result = true;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 保存整个Hash结构数据
     * @param key
     * @param map
     * @return
     */
    public boolean putAllHashValue(String key,Map<String,Object> map) {
        boolean result = false;
        try {
            redisTemplate.opsForHash().putAll(key, map);
            result = true;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 模糊获取key
     * @param patternKey
     * @return
     */
    public Set<String> getKeys(String patternKey){
        Set<String> set = new HashSet<String>();
        try {
            set = redisTemplate.keys(patternKey);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return set;
    }

    public boolean deleteKeys(Set<String> set) {
        boolean result = false;
        try {
            redisTemplate.delete(set);
            result = true;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return result;
    }
}
