package com.huizhi.management.util;

import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.DefaultStringRedisConnection;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import java.io.Serializable;
import java.util.*;
import java.util.Map.Entry;

/**
 * Created by Janita on 2017/1/3.
 */
public class RedisUtilsTemplate extends RedisTemplate<String, Serializable> {

    private RedisSerializer<String> stringSerializer = null;

    private RedisSerializer<Object> jdkSerializationSerializer = null;

    /**
     * Constructs a new <code>StringRedisTemplate</code> instance.
     * {@link #setConnectionFactory(RedisConnectionFactory)}
     * and {@link #afterPropertiesSet()} still need to be called.
     */
    public RedisUtilsTemplate() {
        RedisSerializer<String> stringSerializer = new StringRedisSerializer();
        RedisSerializer<Object> jdkSerializationSerializer = new JdkSerializationRedisSerializer();
        setKeySerializer(stringSerializer);
        setValueSerializer(jdkSerializationSerializer);
        setHashKeySerializer(stringSerializer);
        setHashValueSerializer(jdkSerializationSerializer);
        this.stringSerializer = stringSerializer;
        this.jdkSerializationSerializer = jdkSerializationSerializer;
    }

    /**
     * Constructs a new <code>StringRedisTemplate</code> instance ready to be
     * used.
     *
     * @param connectionFactory
     *            connection factory for creating new connections
     */
    public RedisUtilsTemplate(RedisConnectionFactory connectionFactory) {
        this();
        setConnectionFactory(connectionFactory);
        afterPropertiesSet();
    }

    protected RedisConnection preProcessConnection(RedisConnection connection, boolean existingConnection) {
        return new DefaultStringRedisConnection(connection);
    }

    public void set(final String key, final byte[] value) {
        this.execute(new RedisCallback<Object>() {

            public Object doInRedis(RedisConnection connection) throws DataAccessException {
                connection.set(stringSerializer.serialize(key), value);
                return null;
            }
        });
    }

    /**
     * 此方法存入的value为jdk序列化
     * 
     * @param key
     * @param value
     * @param <T>
     */
    public <T> void set(final String key, final T value) {
        this.execute(new RedisCallback<Object>() {

            public Object doInRedis(RedisConnection connection) throws DataAccessException {
                connection.set(stringSerializer.serialize(key), jdkSerializationSerializer.serialize(value));
                return null;
            }
        });
    }

    /**
     * 此方法存入的value为string序列化
     * 
     * @param key
     * @param value
     * @param <T>
     */
    public void set(final String key, final String value) {
        this.execute(new RedisCallback<Object>() {

            @Override
            public Object doInRedis(RedisConnection connection) throws DataAccessException {
                connection.set(stringSerializer.serialize(key), stringSerializer.serialize(value));
                return null;
            }
        });
    }

    /**
     * SETEX key seconds value
     * <p/>
     * 将值 value 关联到 key ，并将 key 的生存时间设为 seconds (以秒为单位)。
     * <p/>
     * 如果 key 已经存在， SETEX 命令将覆写旧值。
     * <p/>
     * 这个命令类似于以下两个命令：
     * SET key value
     * EXPIRE key seconds # 设置生存时间
     * <p/>
     * 不同之处是， SETEX 是一个原子性(atomic)操作，关联值和设置生存时间两个动作会在同一时间内完成，该命令在 Redis
     * 用作缓存时，非常实用。
     *
     * @param key
     * @param value
     * @param seconds
     */
    public void setex(final String key, final byte[] value, final long seconds) {
        this.execute(new RedisCallback<Object>() {

            public Object doInRedis(RedisConnection connection) throws DataAccessException {
                connection.setEx(stringSerializer.serialize(key), seconds, value);
                return null;
            }
        });
    }

    /**
     * SETEX key seconds value
     * <p/>
     * 将值 value 关联到 key ，并将 key 的生存时间设为 seconds (以秒为单位)。
     * <p/>
     * 如果 key 已经存在， SETEX 命令将覆写旧值。
     * <p/>
     * 这个命令类似于以下两个命令：
     * SET key value
     * EXPIRE key seconds # 设置生存时间
     * <p/>
     * 不同之处是， SETEX 是一个原子性(atomic)操作，关联值和设置生存时间两个动作会在同一时间内完成，该命令在 Redis
     * 用作缓存时，非常实用。
     *
     * @param key
     * @param value
     * @param seconds
     */
    public <T> void setex(final String key, final T value, final long seconds) {
        this.execute(new RedisCallback<Object>() {

            public Object doInRedis(RedisConnection connection) throws DataAccessException {
                connection.setEx(stringSerializer.serialize(key), seconds, jdkSerializationSerializer.serialize(value));
                return null;
            }
        });
    }

    /**
     * SETNX key value
     * <p/>
     * 将 key 的值设为 value ，当且仅当 key 不存在。
     * <p/>
     * 若给定的 key 已经存在，则 SETNX 不做任何动作。
     * <p/>
     * SETNX 是『SET if Not eXists』(如果不存在，则 SET)的简写。
     *
     * @param key
     * @param value
     * @return
     */
    public boolean setnx(final String key, final byte[] value) {
        return this.execute(new RedisCallback<Boolean>() {

            public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
                return connection.setNX(stringSerializer.serialize(key), value);
            }
        });
    }

    /**
     * 判断此value是否已经在此set中
     * 
     * @param key
     * @param value
     * @param <T>
     * @return
     */
    public <T> Boolean sIsExistInSet(final String key, final T value) {
        return this.execute(new RedisCallback<Boolean>() {

            @Override
            public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
                return connection.sIsMember(stringSerializer.serialize(key),
                        jdkSerializationSerializer.serialize(value));
            }
        });
    }
    
    
    public Long hlen(final String key){
        return this.execute(new RedisCallback<Long>() {
            
            @Override
            public Long doInRedis(RedisConnection connection) throws DataAccessException {
                byte[] keyByte = stringSerializer.serialize(key);
                Long value =  connection.hLen(keyByte);
                return value;
            }
        });
    }

    /**
     * 把一个值设置到set
     * 
     * @param key
     * @param value
     * @param <T>
     */
    public <T> Long sAdd(final String key, final T value) {
        return this.execute(new RedisCallback<Long>() {

            @Override
            public Long doInRedis(RedisConnection connection) throws DataAccessException {
                return connection.sAdd(stringSerializer.serialize(key), jdkSerializationSerializer.serialize(value));
            }
        });
    }

    /**
     * SETNX key value
     * <p/>
     * 将 key 的值设为 value ，当且仅当 key 不存在。
     * <p/>
     * 若给定的 key 已经存在，则 SETNX 不做任何动作。
     * <p/>
     * SETNX 是『SET if Not eXists』(如果不存在，则 SET)的简写。
     *
     * @param key
     * @param value
     * @param <T>
     * @return
     */
    public <T> boolean setnx(final String key, final T value) {
        return this.execute(new RedisCallback<Boolean>() {

            public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
                return connection.setNX(stringSerializer.serialize(key), jdkSerializationSerializer.serialize(value));
            }
        });
    }

    /**
     * 通过key获取到value原文字符串（如果原值是对象序列化结果，则获取到就是序列化结果字符串）
     *
     * @param key
     * @return
     */
    public String get(final String key) {
        return this.execute(new RedisCallback<String>() {

            public String doInRedis(RedisConnection connection) throws DataAccessException {
                return stringSerializer.deserialize(connection.get(stringSerializer.serialize(key)));
            }
        });
    }

    public <T> T getd(final String key) {
        return this.execute(new RedisCallback<T>() {

            @SuppressWarnings("unchecked")
            public T doInRedis(RedisConnection connection) throws DataAccessException {
                // return
                // stringSerializer.deserialize(connection.get(stringSerializer.serialize(key)));
                byte[] serializeBytes = connection.get(stringSerializer.serialize(key));
                return (T) jdkSerializationSerializer.deserialize(serializeBytes);
            }
        });
    }

    /**
     * 通过key获取到value对象（如果原值是对象序列化结果，则反序列化为对象；否则抛出异常）
     *
     * @param key
     * @return
     */
    public <T> T getObj(final String key) {
        return this.execute(new RedisCallback<T>() {

            @SuppressWarnings("unchecked")
            public T doInRedis(RedisConnection connection) throws DataAccessException {
                byte[] serializeBytes = connection.get(stringSerializer.serialize(key));
                return (T) jdkSerializationSerializer.deserialize(serializeBytes);
            }
        });
    }

    /**
     * jedis.hset(stringSerializer.serialize(key),stringSerializer.serialize(11+""),jdkSerializationSerializer.serialize(welfare));
     * 把key-value对存入key中
     * 
     * @param key
     *            键
     * @param field
     *            hash表的key
     * @param value
     *            hash表的value
     * @param <T>
     */
    public <T> Boolean hashSet(final String key, final String field, final T value) {

        return this.execute(new RedisCallback<Boolean>() {

            @Override
            public Boolean doInRedis(RedisConnection connection) throws DataAccessException {

                byte[] keyByte = stringSerializer.serialize(key);
                byte[] fieldByte = stringSerializer.serialize(field);
                byte[] valueByte = jdkSerializationSerializer.serialize(value);

                return connection.hSet(keyByte, fieldByte, valueByte);
            }
        });
    }

    public <T> Boolean hashListSet(final String key, final List<String> fields, final List<T> values) {
        return this.execute(new RedisCallback<Boolean>() {

            @Override
            public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
                byte[] keyByte = stringSerializer.serialize(key);
                for (int i = 0; i < fields.size(); i++) {
                    byte[] fieldByte = stringSerializer.serialize(fields.get(i));
                    byte[] valueByte = jdkSerializationSerializer.serialize(values.get(i));
                    connection.hSet(keyByte, fieldByte, valueByte);
                }
                return true;
            }
        });
    }

    /**
     * byte[] val =
     * jedis.hget(stringSerializer.serialize(key),stringSerializer.serialize(11+""));
     * Object o = jdkSerializationSerializer.deserialize(val);
     * ReWelfare res = (ReWelfare) o;
     * 
     * @param key
     *            键
     * @param field
     *            hash表中存入的某一个key
     * @param <T>
     * @return
     */
    public <T> T hashGet(final String key, final String field) {

        return this.execute(new RedisCallback<T>() {

            @SuppressWarnings("unchecked")
            @Override
            public T doInRedis(RedisConnection connection) throws DataAccessException {
                byte[] keyByte = stringSerializer.serialize(key);
                byte[] fieldByte = stringSerializer.serialize(field);

                byte[] value = connection.hGet(keyByte, fieldByte);
                return (T) jdkSerializationSerializer.deserialize(value);
            }
        });
    }

    /**
     * 判断 hash里的key，field是否存在
     * 
     * @param key
     * @param field
     * @return
     */
    public boolean hashGetExist(final String key, final String field) {

        return this.execute(new RedisCallback<Boolean>() {

            @Override
            public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
                byte[] keyByte = stringSerializer.serialize(key);
                byte[] fieldByte = stringSerializer.serialize(field);
                return connection.hExists(keyByte, fieldByte);
            }
        });
    }

    /**
     * 从hash表中移除该key-value对
     * 
     * @param key
     * @param field
     */
    public Long hashDelete(final String key, final String field) {
        return this.execute(new RedisCallback<Long>() {

            @Override
            public Long doInRedis(RedisConnection connection) throws DataAccessException {
                byte[] keyByte = stringSerializer.serialize(key);
                byte[] fieldByte = stringSerializer.serialize(field);
                return connection.hDel(keyByte, fieldByte);
            }
        });
    }
    
    public Long hashDeleteList( List<String> key, final String field) {
        return this.execute(new RedisCallback<Long>() {

            @Override
            public Long doInRedis(RedisConnection connection) throws DataAccessException {
                for (int i = key.size()-1; i >= 0; i--) {
                    byte[] keyByte = stringSerializer.serialize(key.get(i));
                    byte[] fieldByte = stringSerializer.serialize(field);
                    connection.hDel(keyByte, fieldByte);
                }
                return Long.valueOf(key.size());
            }
        });
    }

    /**
     * 从sortedSet中取start-end元素的member
     * 
     * @param key
     * @return
     */
    public List<String> zRevRangeByScore(final String key, final double min, final double max) {
        return this.execute(new RedisCallback<List<String>>() {

            @Override
            public List<String> doInRedis(RedisConnection connection) throws DataAccessException {
                byte[] keyByte = stringSerializer.serialize(key);
                Set<byte[]> bytes = connection.zRevRangeByScore(keyByte, min, max);
                List<String> members = null;
                if (bytes != null && bytes.size() != 0) {
                    members = new ArrayList<String>();
                    Iterator<byte[]> iterator = bytes.iterator();
                    while (iterator.hasNext()) {
                        byte[] memberByte = iterator.next();
                        String member = stringSerializer.deserialize(memberByte);
                        members.add(member);
                    }
                }
                return members;
            }
        });
    }

    /**
     * 批量获取key的value（字符串形式返回）
     * 如果对应的key没有找到记录，也会在map中返回(map中value为null)
     *
     * @return
     */
    public Map<String, String> mget(Collection<String> keys) {
        if (keys == null || keys.isEmpty()) {
            return new HashMap<String, String>(0);
        }
        final Set<String> keySet = new HashSet<String>(keys);
        return this.execute(new RedisCallback<Map<String, String>>() {

            public Map<String, String> doInRedis(RedisConnection connection) throws DataAccessException {
                int keySize = keySet.size();
                byte[][] keyBytes = new byte[keySize][];
                {
                    List<byte[]> keyList = new ArrayList<byte[]>(keySize);
                    for (String key : keySet) {
                        keyList.add(stringSerializer.serialize(key));
                    }
                    keyList.toArray(keyBytes);
                }
                List<byte[]> originalValues = connection.mGet(keyBytes);
                int valueSize = originalValues != null ? originalValues.size() : 0;

                Map<String, String> map = new HashMap<String, String>(keySize);
                int index = 0;
                for (String key : keySet) {
                    String value = null;
                    if (index < valueSize) {
                        byte[] valueByte = originalValues.get(index++);
                        value = stringSerializer.deserialize(valueByte);
                    }
                    map.put(key, value);
                }
                return map;
            }
        });
    }

    /**
     * 批量获取key的value（字符串形式返回）
     * 如果对应的key没有找到记录，也会在map中返回(map中value为null)
     *
     * @return
     */
    public Map<String, String> mgeth(Collection<String> keys) {
        if (keys == null || keys.isEmpty()) {
            return new HashMap<String, String>(0);
        }
        final Set<String> keySet = new HashSet<String>(keys);
        return this.execute(new RedisCallback<Map<String, String>>() {

            public Map<String, String> doInRedis(RedisConnection connection) throws DataAccessException {
                int keySize = keySet.size();
                byte[][] keyBytes = new byte[keySize][];
                {
                    List<byte[]> keyList = new ArrayList<byte[]>(keySize);
                    for (String key : keySet) {
                        keyList.add(stringSerializer.serialize(key));
                    }
                    keyList.toArray(keyBytes);
                }
                List<byte[]> originalValues = connection.mGet(keyBytes);
                int valueSize = originalValues != null ? originalValues.size() : 0;

                Map<String, String> map = new HashMap<String, String>(keySize);
                int index = 0;
                for (String key : keySet) {
                    String value = null;
                    if (index < valueSize) {
                        byte[] valueByte = originalValues.get(index++);
                        value = stringSerializer.deserialize(valueByte);
                    }
                    map.put(key, value);
                }
                return map;
            }
        });
    }

    /**
     * 批量获取key的value（对象形式返回）
     * 如果对应的key没有找到记录，也会在map中返回(map中value为null)
     *
     * @return
     */
    public <T> Map<String, T> mgetObj(Collection<String> keys) {
        if (keys == null || keys.isEmpty()) {
            return new HashMap<String, T>(0);
        }
        final Set<String> keySet = new HashSet<String>(keys);
        return this.execute(new RedisCallback<Map<String, T>>() {

            @SuppressWarnings("unchecked")
            public Map<String, T> doInRedis(RedisConnection connection) throws DataAccessException {
                int keySize = keySet.size();
                byte[][] keyBytes = new byte[keySize][];
                {
                    List<byte[]> keyList = new ArrayList<byte[]>(keySize);
                    for (String key : keySet) {
                        keyList.add(stringSerializer.serialize(key));
                    }
                    keyList.toArray(keyBytes);
                }
                List<byte[]> originalValues = connection.mGet(keyBytes);
                int valueSize = originalValues != null ? originalValues.size() : 0;

                Map<String, T> map = new HashMap<String, T>(keySize);
                int index = 0;
                for (String key : keySet) {
                    T value = null;
                    if (index < valueSize) {
                        byte[] valueByte = originalValues.get(index++);
                        value = (T) jdkSerializationSerializer.deserialize(valueByte);
                    }
                    map.put(key, value);
                }
                return map;
            }
        });
    }

    /**
     * 以秒为单位,返回给定key的有效时间长，如果是-1则表示永远有效,当 key 不存在时，返回 -2 。
     *
     * @param key
     * @return
     */
    public long ttl(final String key) {
        return this.execute(new RedisCallback<Long>() {

            public Long doInRedis(RedisConnection connection) throws DataAccessException {
                return connection.ttl(stringSerializer.serialize(key));
            }
        });
    }

    /**
     * INCR key
     * <p/>
     * 将 key 中储存的数字值增一。
     * <p/>
     * 如果 key 不存在，那么 key 的值会先被初始化为 0 ，然后再执行 INCR 操作。
     * <p/>
     * 如果值包含错误的类型，或字符串类型的值不能表示为数字，那么返回一个错误。
     * <p/>
     * 本操作的值限制在 64 位(bit)有符号数字表示之内。
     * <p/>
     * 这是一个针对字符串的操作，因为 Redis 没有专用的整数类型，所以 key 内储存的字符串被解释为十进制 64 位有符号整数来执行 INCR
     * 操作。
     *
     * @param key
     * @return
     */
    public long incr(final String key) {
        return this.execute(new RedisCallback<Long>() {

            public Long doInRedis(RedisConnection connection) throws DataAccessException {
                return connection.incr(stringSerializer.serialize(key));
            }
        });
    }

    /**
     * INCR key
     * <p/>
     * 将 key 中储存的数字值增一。
     * <p/>
     * 如果 key 不存在，那么 key 的值会先被初始化为 0 ，然后再执行 INCR 操作。
     * <p/>
     * 如果值包含错误的类型，或字符串类型的值不能表示为数字，那么返回一个错误。
     * <p/>
     * 本操作的值限制在 64 位(bit)有符号数字表示之内。
     * <p/>
     * 这是一个针对字符串的操作，因为 Redis 没有专用的整数类型，所以 key 内储存的字符串被解释为十进制 64 位有符号整数来执行 INCR
     * 操作。
     *
     * @param key
     * @param increment
     *            增量
     * @return
     */
    public long incrBy(final String key, final long increment) {
        return this.execute(new RedisCallback<Long>() {

            public Long doInRedis(RedisConnection connection) throws DataAccessException {
                return connection.incrBy(stringSerializer.serialize(key), increment);
            }
        });
    }

    /**
     * DECR key
     * <p/>
     * 将 key 中储存的数字值减一。
     * <p/>
     * 如果 key 不存在，那么 key 的值会先被初始化为 0 ，然后再执行 DECR 操作。
     * <p/>
     * 如果值包含错误的类型，或字符串类型的值不能表示为数字，那么返回一个错误。
     * <p/>
     * 本操作的值限制在 64 位(bit)有符号数字表示之内。
     * <p/>
     * 关于递增(increment) / 递减(decrement)操作的更多信息，请参见 INCR 命令。
     *
     * @param key
     * @return
     */
    public long decr(final String key) {
        return this.execute(new RedisCallback<Long>() {

            public Long doInRedis(RedisConnection connection) throws DataAccessException {
                return connection.decr(stringSerializer.serialize(key));
            }
        });
    }

    /**
     * DECR key
     * <p/>
     * 将 key 中储存的数字值减一。
     * <p/>
     * 如果 key 不存在，那么 key 的值会先被初始化为 0 ，然后再执行 DECR 操作。
     * <p/>
     * 如果值包含错误的类型，或字符串类型的值不能表示为数字，那么返回一个错误。
     * <p/>
     * 本操作的值限制在 64 位(bit)有符号数字表示之内。
     * <p/>
     * 关于递增(increment) / 递减(decrement)操作的更多信息，请参见 INCR 命令。
     *
     * @param key
     * @param decrement
     *            减量
     * @return
     */
    public long decrBy(final String key, final long decrement) {
        return this.execute(new RedisCallback<Long>() {

            public Long doInRedis(RedisConnection connection) throws DataAccessException {
                return connection.decrBy(stringSerializer.serialize(key), decrement);
            }
        });
    }

    /**
     * 返回列表范围：从0开始，到最后一个(-1) [包含]
     *
     * @param key
     * @param start
     *            从0开始
     * @param end
     *            -1：到最后一个
     * @return
     */
    public List<String> lrange(final String key, final long start, final long end) {
        return this.execute(new RedisCallback<List<String>>() {

            public List<String> doInRedis(RedisConnection connection) throws DataAccessException {
                List<byte[]> byteList = connection.lRange(stringSerializer.serialize(key), start, end);
                List<String> stringList = null;
                if (byteList == null || byteList.size() == 0) {
                    stringList = new ArrayList<String>(0);
                } else {
                    stringList = new ArrayList<String>(byteList.size());
                    for (byte[] bytes : byteList) {
                        stringList.add(stringSerializer.deserialize(bytes));
                    }
                }
                return stringList;
            }
        });
    }

    /**
     * 返回List长度, key不存在时返回0，key类型不是list时抛出异常.
     */
    public long llen(final String key) {
        return this.execute(new RedisCallback<Long>() {

            public Long doInRedis(RedisConnection connection) throws DataAccessException {
                return connection.lLen(stringSerializer.serialize(key));
            }
        });
    }

    /**
     * LREM key count value
     * <p/>
     * 根据参数 count 的值，移除列表中与参数 value 相等的元素。
     * <p/>
     * count 的值可以是以下几种：
     * <p/>
     * count > 0 : 从表头开始向表尾搜索，移除与 value 相等的元素，数量为 count 。
     * count < 0 : 从表尾开始向表头搜索，移除与 value 相等的元素，数量为 count 的绝对值。
     * count = 0 : 移除表中所有与 value 相等的值。
     * <p/>
     * 返回值：
     * 被移除元素的数量。
     * 因为不存在的 key 被视作空表(empty list)，所以当 key 不存在时， LREM 命令总是返回 0 。
     */
    public boolean lremOne(final String key, final String value) {
        return this.execute(new RedisCallback<Boolean>() {

            public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
                return connection.lRem(stringSerializer.serialize(key), 1, stringSerializer.serialize(value)) > 0;
            }
        });
    }

    /**
     * LREM key count value
     * <p/>
     * 根据参数 count 的值，移除列表中与参数 value 相等的元素。
     * <p/>
     * count 的值可以是以下几种：
     * <p/>
     * count > 0 : 从表头开始向表尾搜索，移除与 value 相等的元素，数量为 count 。
     * count < 0 : 从表尾开始向表头搜索，移除与 value 相等的元素，数量为 count 的绝对值。
     * count = 0 : 移除表中所有与 value 相等的值。
     * <p/>
     * 返回值：
     * 被移除元素的数量。
     * 因为不存在的 key 被视作空表(empty list)，所以当 key 不存在时， LREM 命令总是返回 0 。
     */
    public long lremAll(final String key, final String value) {
        return this.execute(new RedisCallback<Long>() {

            public Long doInRedis(RedisConnection connection) throws DataAccessException {
                return connection.lRem(stringSerializer.serialize(key), 1, stringSerializer.serialize(value));
            }
        });
    }

    /**
     * 移除并返回列表 key 的尾元素。
     * <p/>
     * 返回值：
     * 列表的头元素。
     * 当 key 不存在时，返回 nil 。
     */
    public String rpop(final String key) {
        return this.execute(new RedisCallback<String>() {

            public String doInRedis(RedisConnection connection) throws DataAccessException {
                return stringSerializer.deserialize(connection.rPop(stringSerializer.serialize(key)));
            }
        });
    }

    /**
     * 移除并返回列表 key 的头元素。
     * <p/>
     * 返回值：
     * 列表的头元素。
     * 当 key 不存在时，返回 nil 。
     */
    public String lpop(final String key) {
        return this.execute(new RedisCallback<String>() {

            public String doInRedis(RedisConnection connection) throws DataAccessException {
                return stringSerializer.deserialize(connection.lPop(stringSerializer.serialize(key)));
            }
        });
    }

    /**
     * LPUSH key value [value ...]
     * <p/>
     * 将一个或多个值 value 插入到列表 key 的表头
     * <p/>
     * 如果有多个 value 值，那么各个 value 值按从左到右的顺序依次插入到表头： 比如说，对空列表 mylist 执行命令 LPUSH
     * mylist a b c ，列表的值将是 c b a ，这等同于原子性地执行 LPUSH mylist a 、 LPUSH mylist b 和
     * LPUSH mylist c 三个命令。
     * <p/>
     * 如果 key 不存在，一个空列表会被创建并执行 LPUSH 操作。
     * <p/>
     * 当 key 存在但不是列表类型时，返回一个错误。
     * <p/>
     * 在Redis 2.4版本以前的 LPUSH 命令，都只接受单个 value 值。
     */
    public void lpush(final String key, final String... values) {
        if (values == null || values.length <= 0) {
            return;
        }
        this.execute(new RedisCallback<Object>() {

            public Object doInRedis(RedisConnection connection) throws DataAccessException {
                byte[][] valuesByte = new byte[values.length][];
                int i = 0;
                for (String value : values) {
                    valuesByte[i++] = stringSerializer.serialize(value);
                }
                connection.lPush(stringSerializer.serialize(key), valuesByte);
                return null;
            }
        });
    }

    /**
     * RPUSH key value [value ...]
     * <p/>
     * 将一个或多个值 value 插入到列表 key 的表尾(最右边)。
     * <p/>
     * 如果有多个 value 值，那么各个 value 值按从左到右的顺序依次插入到表尾：比如对一个空列表 mylist 执行 RPUSH mylist
     * a b c ，得出的结果列表为 a b c ，等同于执行命令 RPUSH mylist a 、 RPUSH mylist b 、 RPUSH
     * mylist c 。
     * <p/>
     * 如果 key 不存在，一个空列表会被创建并执行 RPUSH 操作。
     * <p/>
     * 当 key 存在但不是列表类型时，返回一个错误。
     * <p/>
     * 在 Redis 2.4 版本以前的 RPUSH 命令，都只接受单个 value 值。
     */
    public void rpush(final String key, final String... values) {
        if (values == null || values.length <= 0) {
            return;
        }
        this.execute(new RedisCallback<Object>() {

            public Object doInRedis(RedisConnection connection) throws DataAccessException {
                byte[][] valuesByte = new byte[values.length][];
                int i = 0;
                for (String value : values) {
                    valuesByte[i++] = stringSerializer.serialize(value);
                }
                connection.rPush(stringSerializer.serialize(key), valuesByte);
                return null;
            }
        });
    }

    /**
     * 加入Sorted set, 如果member在Set里已存在，只更新score并返回false,否则返回true.
     * <p/>
     * ZADD key score member [[score member] [score member] ...]
     * <p/>
     * 将一个或多个 member 元素及其 score 值加入到有序集 key 当中。
     * <p/>
     * 如果某个 member 已经是有序集的成员，那么更新这个 member 的 score 值，并通过重新插入这个 member 元素，来保证该
     * member 在正确的位置上。
     * <p/>
     * score 值可以是整数值或双精度浮点数。
     * <p/>
     * 如果 key 不存在，则创建一个空的有序集并执行 ZADD 操作。
     * <p/>
     * 当 key 存在但不是有序集类型时，返回一个错误。
     * <p/>
     * 对有序集的更多介绍请参见 sorted set 。
     * <p/>
     * 返回值:
     * 被成功添加的新成员的数量，不包括那些被更新的、已经存在的成员。
     */
    public boolean zadd(final String key, final String member, final double score) {
        return this.execute(new RedisCallback<Boolean>() {

            public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
                return connection.zAdd(stringSerializer.serialize(key), score, stringSerializer.serialize(member));
            }
        });
    }

    /**
     * 把某个值的分数增加increment
     * 
     * @param key
     * @param value
     * @param increment
     * @param <T>
     * @return
     */
    public <T> Double zIncrBy(final String key, final T value, final double increment) {
        return this.execute(new RedisCallback<Double>() {

            @Override
            public Double doInRedis(RedisConnection connection) throws DataAccessException {
                return connection.zIncrBy(stringSerializer.serialize(key), increment,
                        jdkSerializationSerializer.serialize(value));
            }
        });
    }

    /**
     * 获取sortedSet中的某个member的score
     * 
     * @param key
     * @param member
     * @return
     */
    public Double zGetScoreByMember(final String key, final String member) {

        return this.execute(new RedisCallback<Double>() {

            @Override
            public Double doInRedis(RedisConnection connection) throws DataAccessException {
                return connection.zScore(stringSerializer.serialize(key), stringSerializer.serialize(member));
            }
        });
    }

    /**
     * 删除sorted set中的元素
     * <p/>
     * ZREM key member [member ...]
     * <p/>
     * 移除有序集 key 中的一个或多个成员，不存在的成员将被忽略。
     * <p/>
     * 当 key 存在但不是有序集类型时，返回一个错误。
     * <p/>
     * 在 Redis 2.4 版本以前， ZREM 每次只能删除一个元素。
     * <p/>
     * 返回值:
     * 被成功移除的成员的数量，不包括被忽略的成员。
     */
    public long zrem(final String key, final String member) {
        return this.execute(new RedisCallback<Long>() {

            public Long doInRedis(RedisConnection connection) throws DataAccessException {
                return connection.zRem(stringSerializer.serialize(key), stringSerializer.serialize(member));
            }
        });
    }

    /**
     * 返回List长度, key不存在时返回0，key类型不是sorted set时抛出异常.
     * <p/>
     * ZCARD key
     * <p/>
     * 返回有序集 key 的基数。
     * <p/>
     * 返回值:
     * 当 key 存在且是有序集类型时，返回有序集的基数。
     * 当 key 不存在时，返回 0 。
     */
    public long zcard(final String key) {
        return this.execute(new RedisCallback<Long>() {

            public Long doInRedis(RedisConnection connection) throws DataAccessException {
                return connection.zCard(stringSerializer.serialize(key));
            }
        });
    }

    /**
     * 获取此刻有效的key的数量
     *
     * @return
     */
    public long dbSize() {
        return this.execute(new RedisCallback<Long>() {

            public Long doInRedis(RedisConnection connection) throws DataAccessException {
                return connection.dbSize();
            }
        });
    }

    /**
     * 从set中获取某个key的所有的值得列表<>set</>
     * 
     * @param key
     * @return
     *         Set<byte[]> sMembers(byte[] key);
     */
    public <T> List<T> sMembers(final String key) {
        return this.execute(new RedisCallback<List<T>>() {

            @Override
            public List<T> doInRedis(RedisConnection connection) throws DataAccessException {
                byte[] keyBytes = stringSerializer.serialize(key);
                Set<byte[]> set = connection.sMembers(keyBytes);
                List<T> list = new ArrayList<>(set.size());
                for (byte[] bytes : set) {
                    @SuppressWarnings("unchecked")
                    T t = (T) jdkSerializationSerializer.deserialize(bytes);
                    list.add(t);
                }
                return list;
            }
        });
    }

    /**
     * 返回key下的所有hash表中所有对象
     * 
     * @param key
     * @return
     */
    public <T> Map<String, T> hashGetAll(final String key) {

        return this.execute(new RedisCallback<Map<String, T>>() {

            @SuppressWarnings("unchecked")
            @Override
            public Map<String, T> doInRedis(RedisConnection connection) throws DataAccessException {
                Map<String, T> map = new HashMap<String, T>();
                byte[] keyByte = stringSerializer.serialize(key);
                T t = null;
                Map<byte[], byte[]> m = connection.hGetAll(keyByte);
                Set<Entry<byte[], byte[]>> s = m.entrySet();

                for (Entry<byte[], byte[]> e : s) {
                    byte[] fieldByte = e.getKey();
                    byte[] value = connection.hGet(keyByte, fieldByte);

                    t = (T) jdkSerializationSerializer.deserialize(value);
                    map.put(new String(e.getKey()), t);
                }
                return map;
            }
        });
    }

    /**
     * 获取所有 
     * @param key
     * @return
     */
    public <T> List<T> hashGetAl(final String key) {

        return this.execute(new RedisCallback<List<T>>() {

            @SuppressWarnings("unchecked")
            @Override
            public List<T> doInRedis(RedisConnection connection) throws DataAccessException {
               // Map<String, T> map = new HashMap<String, T>();
                byte[] keyByte = stringSerializer.serialize(key);
                T t = null;
                Map<byte[], byte[]> m = connection.hGetAll(keyByte);
                Set<Entry<byte[], byte[]>> s = m.entrySet();
                List<T> list = new ArrayList<>();
                for (Entry<byte[], byte[]> e : s) {
                    byte[] fieldByte = e.getKey();
                    byte[] value = connection.hGet(keyByte, fieldByte);

                    t = (T) jdkSerializationSerializer.deserialize(value);
                    //map.put(new String(e.getKey()), t);
                    list.add(t);
                }
                return list;
            }
        });
    }
    public <T> Map<T, T> hashGetAllTest(final String key) {
        return this.execute(new RedisCallback<Map<T, T>>() {

            @SuppressWarnings("unchecked")
            @Override
            public Map<T, T> doInRedis(RedisConnection connection) throws DataAccessException {
                byte[] keyBytes = stringSerializer.serialize(key);
                Map<byte[], byte[]> maps = connection.hGetAll(keyBytes);
                System.err.println(maps);
                Map<T, T> map = new HashMap<>(maps.size());
                for (byte[] keys : maps.keySet()) {
                    T t = (T) jdkSerializationSerializer.deserialize(keys);
                    byte[] value = connection.hGet(keyBytes, keys);
                    T t1 = (T) jdkSerializationSerializer.deserialize(value);
                    map.put(t, t1);
                }
                return map;
            }
        });
    }

    /**
     * 
     * hash 通过key删除
     * 
     * @param key
     * @return
     */
    public int hashDeleteAll(final String key) {
        List<Long> list = new ArrayList<Long>();
        this.execute(new RedisCallback<Long>() {

            @Override
            public Long doInRedis(RedisConnection connection) throws DataAccessException {
                byte[] keyByte = stringSerializer.serialize(key);
                Map<byte[], byte[]> m = connection.hGetAll(keyByte);
                Set<Entry<byte[], byte[]>> s = m.entrySet();

                for (Entry<byte[], byte[]> e : s) {
                    byte[] fieldByte = e.getKey();
                    Long l = connection.hDel(keyByte, fieldByte);
                    list.add(l);
                }
                return null;
            }
        });
        for (Long g : list) {
            if (g != 1) {
                return 0;
            }
        }
        return 1;
    }
}
