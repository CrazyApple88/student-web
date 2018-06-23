package com.xhgx.sdk.cache;

import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName EhCacheAdapter
 * @Description ehcache缓存适配器
 * @author zohan(inlw@sina.com)
 * @date 2017-03-22 17:44
 * @vresion 1.0
 */
public class EhCacheAdapter implements ICache {



    /**ehcache 实体*/
    private Cache cache;

    /**
     * <p>Title: </p>
     * <p>Description: </p>
     * @param cache
     */
    public EhCacheAdapter(Cache cache) {
        this.cache = cache;
    }




    public Object get(Object key) {
        Element element = cache.get(key);
        if(element!=null){
            return element.getObjectValue();
        }
        return null;
    }


    public void put(Object key, Object value) {

        Element element = new Element(key, value);

        cache.put(element);

    }


    public boolean remove(Object key) {
        return cache.remove(key);
    }




    public void put(Object key, Object value, int liveTime) {
        Element element = new Element(key, value);
        element.setTimeToLive(liveTime);
        cache.put(element);
    }




    public boolean removeRegex(String regex) {
        if(StringUtils.isEmpty(regex)){
            throw new NullPointerException("regex 参数不能为null");
        }
        List<String> list = cache.getKeysWithExpiryCheck();
        List<String> keys = new ArrayList<String>();
        for(String key :list){
            if(key.matches(regex)){
                keys.add(key);
            }
        }
        if(keys.size()>0){
            cache.removeAll(keys);
            return true;
        }
        return false;
    }





    public Map<String, Object> getByRegex(String regex) {
        List<String> keys = new ArrayList<String>();
        List<String> list = cache.getKeysWithExpiryCheck();
        for(String key :list){
            if(key.matches(regex)){
                keys.add(key);
            }
        }
        Map<String,Object> map = new HashMap<String,Object>();
        Map<Object, Element>  objects  = cache.getAll(keys);
        if(objects!=null && !objects.isEmpty()){
            for(String key:keys){
                Element element = objects.get(key);
                if(element!=null){
                    map.put(key,  element.getObjectValue());
                }
            }

        }

        return map;
    }

}
