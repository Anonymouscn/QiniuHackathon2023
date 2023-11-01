package util;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Component;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * Redis 工具类
 *
 * @author anonymous
 * @version 1.0
 */
@RequiredArgsConstructor
public class RedisUtil {

    private final RedisTemplate<Object, Object> redisTemplate;

    /**
     * 缓存基本的对象，Integer、String、实体类等
     *
     * @param key   缓存的键值
     * @param value 缓存的值
     * @return 缓存的对象
     */
    public ValueOperations<Object, Object> setCacheObject(Object key, Object value) {
        ValueOperations<Object, Object> operation = redisTemplate.opsForValue();
        operation.set(key, value);
        return operation;
    }

    /**
     * 缓存基本的对象，Integer、String、实体类等
     *
     * @param key      缓存的键值
     * @param value    缓存的值
     * @param timeout  时间
     * @param timeUnit 时间颗粒度
     * @return 缓存的对象
     */
    public ValueOperations<Object, Object> setCacheObject(Object key, Object value, Integer timeout, TimeUnit timeUnit) {
        ValueOperations<Object, Object> operation = redisTemplate.opsForValue();
        operation.set(key, value, timeout, timeUnit);
        return operation;
    }

    /**
     * 获得缓存的基本对象。
     *
     * @param key 缓存键值
     * @return 缓存键值对应的数据
     */
    public Object getCacheObject(Object key) {
        ValueOperations<Object, Object> operation = redisTemplate.opsForValue();
        return operation.get(key);
    }

    /**
     * 删除单个对象
     *
     * @param key 对象键值
     */
    public void deleteObject(Object key) {
        redisTemplate.delete(key);
    }

    /**
     * 删除集合对象
     *
     * @param collection 对象集合
     */
    public void deleteObject(Collection<?> collection) {
        redisTemplate.delete(collection);
    }

    /**
     * 获取对象剩余过期时间
     *
     * @param key 对象键值
     * @return 剩余过期时间
     */
    public Long getExpire(String key) {
        return redisTemplate.getExpire(key);
    }

    /**
     * 设置对象过期时间
     *
     * @param key      对象键值
     * @param expire   过期时间
     * @param timeUnit 时间颗粒度
     */
    public void expire(String key, Long expire, TimeUnit timeUnit) {
        redisTemplate.expire(key, expire, timeUnit);
    }

    /**
     * 缓存List数据
     *
     * @param key      缓存的键值
     * @param dataList 待缓存的List数据
     * @return 缓存的对象
     */
    public ListOperations<Object, Object> setCacheList(Object key, List<Object> dataList) {
        ListOperations<Object, Object> listOperation = redisTemplate.opsForList();
        if (null != dataList) {
            int size = dataList.size();
            for (Object o : dataList) {
                listOperation.leftPush(key, o);
            }
        }
        return listOperation;
    }

    /**
     * 获得缓存的list对象
     *
     * @param key 缓存的键值
     * @return 缓存键值对应的数据
     */
    public List<Object> getCacheList(String key) {
        List<Object> dataList = new ArrayList<>();
        ListOperations<Object, Object> listOperation = redisTemplate.opsForList();
        Long size = listOperation.size(key);
        if (null != size) {
            for (int i = 0; i < size; i++) {
                dataList.add(listOperation.index(key, i));
            }
        }
        return dataList;
    }

    /**
     * 缓存Set
     *
     * @param key     缓存键值
     * @param dataSet 缓存的数据
     * @return 缓存数据的对象
     */
    public BoundSetOperations<Object, Object> setCacheSet(String key, Set<Object> dataSet) {
        BoundSetOperations<Object, Object> setOperation = redisTemplate.boundSetOps(key);
        for (Object o : dataSet) {
            setOperation.add(o);
        }
        return setOperation;
    }

    /**
     * 获得缓存的Set
     *
     * @param key Set键值
     * @return 缓存的Set
     */
    public Set<Object> getCacheSet(Object key) {
        BoundSetOperations<Object, Object> operation = redisTemplate.boundSetOps(key);
        return operation.members();
    }

    /**
     * 缓存Map
     *
     * @param key     缓存键值
     * @param dataMap 缓存的数据
     * @return 缓存数据的对象
     */
    @SuppressWarnings("unchecked")
    public <T, K, V> HashOperations<T, K, V> setCacheMap(T key, Map<K, V> dataMap) {
        HashOperations<Object, Object, Object> hashOperations = redisTemplate.opsForHash();
        if (null != dataMap) {
            for (Map.Entry<K, V> entry : dataMap.entrySet()) {
                hashOperations.put(key, entry.getKey(), entry.getValue());
            }
        }
        return (HashOperations<T, K, V>) hashOperations;
    }

    /**
     * 获得缓存的Map
     *
     * @param key Map键值
     * @return 缓存的Map
     */
    @SuppressWarnings("unchecked")
    public <K, V> Map<K, V> getCacheMap(String key) {
        return (Map<K, V>) redisTemplate.opsForHash().entries(key);
    }

    /**
     * 获得缓存的基本对象列表
     *
     * @param pattern 字符串前缀
     * @return 对象列表
     */
    public Collection<Object> keys(String pattern) {
        return redisTemplate.keys(pattern);
    }

}