package com.czbank.coding.good.util;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.SerializationUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.SortingParams;
import redis.clients.jedis.util.SafeEncoder;

@Component
public class JedisUtil {
    /*缓存生存时间  */
    private int expire = 60000;

    /*对key的操作方法 */
    private static JedisPool jedisPool = null;

    static {
        // 初始化
        // 1、设置连接池的配置对象
        JedisPoolConfig config = new JedisPoolConfig();
        // 设置池中最大的连接数量（可选）
        config.setMaxTotal(50);
        // 设置空闲时池中保有的最大连接数（可选）
        config.setMaxIdle(10);

        // 2、设置连接池对象
        jedisPool = new JedisPool(config, "119.23.22.99", 6379);
    }


    public synchronized static Jedis getJedis() {
        if (jedisPool != null) {
            //获取资源
            Jedis resource = jedisPool.getResource();
            return resource;
        } else {
            return null;
        }
    }


    public JedisPool getPool() {
        return jedisPool;
    }

    /*从jedispool中获取jedis对象*//*
    public Jedis getJedis(){
        if(jedisPool == null){
            throw new NullPointerException();
        }
        return jedisPool.getResource();
    }*/

    public static JedisPool getJedisPool() {
        return jedisPool;
    }

    public static void setJedisPool(JedisPool jedisPool) {
        JedisUtil.jedisPool = jedisPool;
    }


    /*
     * 在finaally中回收jedis
     */
    public void returnJedis(Jedis jedis) {
        if (null != jedis && null != jedisPool) {
            jedis.close();
        }
    }

    /*
     * 销毁链接(放入catch
     */
    public static void returnBrokenResource(Jedis jedis) {
        if (null != jedis && null != jedisPool) {
            jedis.close();
        }
    }

    /*
     * 设置过期时间
     */
    public void expire(String key, int seconds) {
        if (seconds < 0) {
            return;
        }
        Jedis jedis = getJedis();
        jedis.expire(key, seconds);
        returnJedis(jedis);
    }

    /*
     * 设置默认过期时间
     */
    public void expire(String key) {
        expire(key, expire);
    }

    /*
     * 清空所有key
     */
    public String flushAll() {
        Jedis jedis = getJedis();
        String stata = jedis.flushAll();
        returnJedis(jedis);
        return stata;
    }


    public Object get(String key) {
        Jedis jedis = getJedis();
        Object object = SerializationUtils.deserialize(jedis.get(key.getBytes()));
        returnJedis(jedis);
        return object;
    }

    public Object set(String key, Serializable value) {
        Jedis jedis = getJedis();
        Object object = jedis.set(key.getBytes(), SerializationUtils.serialize(value));
        returnJedis(jedis);
        return object;
    }

    /*
     * 更改key 返回值是状态吗
     */

    public String rename(String oldkey, String newkey) {
        return rename(SafeEncoder.encode(oldkey), SafeEncoder.encode(newkey));
    }

    /*
     * 更改key，仅当新key不存在时才执行
     */
    public long renamenx(String oldkey, String newkey) {
        Jedis jedis = getJedis();
        long status = jedis.renamenx(oldkey, newkey);
        returnJedis(jedis);
        return status;

    }

    /*
     * 更改key
     */
    public String rename(byte[] oldkey, byte[] newkey) {
        Jedis jedis = getJedis();
        String status = jedis.rename(oldkey, newkey);
        returnJedis(jedis);
        return status;
    }

    /*
     * 设置key的过期时间，以秒为单位             返回值是影响的记录数
     */
    public long expired(String key, int seconds) {
        Jedis jedis = getJedis();
        long count = jedis.expire(key, seconds);
        returnJedis(jedis);
        return count;
    }

    /*
     * 设置key的过期时间，它是距历元（即格林威治标准时间 1970年1月1日的00:00:00,格里高利历)的偏移量
     */
    public long expireAt(String key, long timestamp) {
        Jedis jedis = getJedis();
        long count = jedis.expireAt(key, timestamp);
        returnJedis(jedis);
        return count;
    }

    /*
     * 查询key的过期时间       以秒为单位的时间表示返回的是指定key的剩余的生存时间
     */
    public long ttl(String key) {
        Jedis sjedis = getJedis();
        long len = sjedis.ttl(key);
        returnJedis(sjedis);
        return len;

    }

    /*
     * 取消对key过期时间的设置  将带生存时间的转换成一个永不过期的key
     *
     * 当移除成功时返回1，key不存在或者移除不成功时返回0
     */
    public long persist(String key) {
        Jedis jedis = getJedis();
        long count = jedis.persist(key);
        returnJedis(jedis);
        return count;
    }

    /*
     * 删除keys对应的记录，可以是多个key
     *
     * 返回值是被删除的数量
     */
    public long del(String... keys) {
        Jedis jedis = getJedis();
        long count = jedis.del(keys);
        returnJedis(jedis);
        return count;
    }

    /*
     * 删除keys对应的记录，可以是多个key
     */
    public long del(byte[]... keys) {
        Jedis jedis = getJedis();
        long count = jedis.del(keys);
        returnJedis(jedis);
        return count;
    }

    /*
     * 判断key是否存在
     */
    public boolean exists(String key) {
        Jedis jedis = getJedis();
        boolean exists = jedis.exists(key);
        returnJedis(jedis);
        return exists;
    }

    /*
     * 对List，set，SortSet 进行排序，如果集合数据较大应避免使用这个方法
     *
     * 返回排序后的结果，默认升序 sort key Desc为降序
     */
    public List<String> sort(String key) {
        Jedis jedis = getJedis();
        List<String> list = jedis.sort(key);

        returnJedis(jedis);
        return list;
    }

    /*
     * 对List，set，SortSet 进行排序，如果集合数据较大应避免使用这个方法
     *
     * 返回排序后的结果，默认升序 sort key Desc为降序
     */
    public List<String> sort(String key, SortingParams parame) {
        Jedis jedis = getJedis();
        List<String> list = jedis.sort(key, parame);
        returnJedis(jedis);
        return list;
    }
    /*
     * 返回指定key的存储类型
     */

    public String type(String key) {
        Jedis jedis = getJedis();
        String type = jedis.type(key);
        returnJedis(jedis);
        return type;
    }

    /*
     * 查找所有匹配模式的键
     *
     * key的查询表达式 *代表任意多个 ？代表一个
     */
    public Set<String> Keys(String pattern) {
        Jedis jedis = getJedis();
        Set<String> set = jedis.keys(pattern);

        returnJedis(jedis);
        return set;
    }

    /*************************set部分*******************************/
    /*
     * 向set添加一条记录，如果member已经存在则返回0，否则返回1
     */
    public long sadd(String key, String member) {
        Jedis jedis = getJedis();

        Long s = jedis.sadd(key, member);
        returnJedis(jedis);
        return s;
    }

    public long sadd(byte[] key, byte[] member) {
        Jedis jedis = getJedis();
        Long s = jedis.sadd(key, member);
        returnJedis(jedis);
        return s;
    }

    /*
     * 获取给定key中元素个数
     *
     * @return 元素个数
     */
    public long scard(String key) {
        Jedis jedis = getJedis();
        Long count = jedis.scard(key);
        returnJedis(jedis);
        return count;
    }

    /*
     * 返回从第一组和所有的给定集合之间的有差异的成员
     *
     * @return 有差异成员的集合
     */
    public Set<String> sdiff(String... keys) {
        Jedis jedis = getJedis();
        Set<String> set = jedis.sdiff(keys);
        returnJedis(jedis);
        return set;
    }

    /*
     * 这个命令的作用和 SDIFF 类似，但它将结果保存到 destination 集合，而不是简单地返回结果集,如果目标已存在，则覆盖
     *
     * @return  新集合的记录数
     */
    public long sdiffstore(String newkey, String... keys) {
        Jedis jedis = getJedis();
        Long count = jedis.sdiffstore(newkey, keys);
        returnJedis(jedis);
        return count;
    }

    /*
     * sinter 返回给定集合交集成员，如果其中一个集合为不存在或为空，则返回空set
     * @return 交集成员的集合
     */
    public Set<String> sinter(String... keys) {
        Jedis jedis = getJedis();
        Set<String> set = jedis.sinter(keys);
        returnJedis(jedis);
        return set;
    }

    /*
     * sinterstore 这个命令类似于 SINTER 命令，但它将结果保存到 destination 集合，而不是简单地返回结果集。
     * 如果 destination 集合已经存在，则将其覆盖。destination 可以是 key 本身
     *
     * @return 新集合的记录数
     */
    public long sinterstore(String dstkey, String... keys) {
        Jedis jedis = getJedis();
        long count = jedis.sinterstore(dstkey, keys);
        returnJedis(jedis);
        return count;
    }

    /*
     * sismember 确定一个给定的值是否存在
     * @param String member 要判断的值
     * @return 存在返回1，不存在返回0
     */
    public boolean sismember(String key, String member) {
        Jedis jedis = getJedis();
        Boolean s = jedis.sismember(key, member);
        returnJedis(jedis);
        return s;
    }
    /*
     * smembers 返回集合中的所有成员
     * @return 成员集合
     */

    public Set<String> smembers(String key) {
        Jedis jedis = getJedis();
        Set<String> set = jedis.smembers(key);
        returnJedis(jedis);
        return set;
    }

    public Set<byte[]> smembers(byte[] key) {
        Jedis jedis = getJedis();
        Set<byte[]> set = jedis.smembers(key);
        returnJedis(jedis);
        return set;
    }

    /*
     * smove 将成员从源集合移除放入目标集合 </br>
     * 如果源集合不存在或不包含指定成员，不进行任何操作，返回0</br>
     * 否则该成员从源集合上删除，并添加到目标集合，如果目标集合成员以存在，则只在源集合进行删除
     * @param srckey 源集合  dstkey目标集合   member源集合中的成员
     *
     * @return 状态码 1成功 0失败
     */
    public long smove(String srckey, String dstkey, String member) {
        Jedis jedis = getJedis();
        Long s = jedis.smove(srckey, dstkey, member);
        returnJedis(jedis);
        return s;
    }

    /*
     * spop 从集合中删除成员  移除并返回集合中的一个随机元素。
     *
     * @return 被删除的随机成员
     */
    public String spop(String key) {
        Jedis jedis = getJedis();
        String s = jedis.spop(key); //s 被移除的随机成员
        returnJedis(jedis);
        return s;
    }

    /*
     * 从集合中删除指定成员
     * 移除集合 key 中的一个或多个 member 元素，不存在的 member 元素会被忽略。当 key 不是集合类型，返回一个错误。
     *
     * @param key member要删除的成员
     * @return 状态码 成功返回1，成员不存在返回0
     */
    public long srem(String key, String member) {
        Jedis jedis = getJedis();
        Long s = jedis.srem(key, member);
        returnJedis(jedis);
        return s;
    }

    /*
     *这个命令类似于 SUNION 命令，但它将结果保存到 destination 集合，而不是简单地返回结果集。
     *如果 destination 已经存在，则将其覆盖。  destination 可以是 key 本身
     */
    public long sunionstore(String dstkey, String... keys) {
        Jedis jedis = getJedis();
        Long s = jedis.sunionstore(dstkey, keys);
        returnJedis(jedis);
        return s;
    }


    /******************************SortSet******************************/

    /*
     * zadd 向集合中增加一条记录，如果这个值已经存在，这个值对应的权重将被置为新的权重
     *
     * @param double score 权重 member要加入的值
     * @return 状态码 1成功 0已经存在member值
     */
    public long zadd(String key, double score, String member) {
        Jedis jedis = getJedis();
        long s = jedis.zadd(key, score, member);
        returnJedis(jedis);
        return s;
    }

    /*
     * 获取集合中元素的数量
     * @param String key
     * @return 当 key 存在且是有序集类型时，返回有序集的基数。 当 key 不存在时，返回 0 。
     */
    public long zcard(String key) {
        Jedis jedis = getJedis();
        long count = jedis.zcard(key);
        returnJedis(jedis);
        return count;
    }

    /*
     * zcount 获取指定权重区间内的集合数量
     *
     * @param double min最小排序位置   max最大排序位置
     */
    public long zcount(String key, double min, double max) {
        Jedis jedis = getJedis();
        long count = jedis.zcount(key, min, max);
        returnJedis(jedis);
        return count;
    }

    /*
     * zrange 返回有序集合key中，指定区间的成员0，-1指的是整个区间的成员
     */
    public Set<String> zrange(String key, int start, int end) {

        Jedis jedis = getJedis();
        Set<String> set = jedis.zrange(key, 0, -1);
        returnJedis(jedis);
        return set;
    }


    /*
     * zrevrange  返回有序集 key 中，指定区间内的成员。其中成员的位置按 score 值递减(从大到小)来排列
     */
    public Set<String> zrevrange(String key, int start, int end) {
        // ShardedJedis sjedis = getShardedJedis();
        Jedis sjedis = getJedis();
        Set<String> set = sjedis.zrevrange(key, start, end);
        returnJedis(sjedis);
        return set;
    }

    /*
     * zrangeByScore  根据上下权重查询集合
     */
    public Set<String> zrangeByScore(String key, double min, double max) {
        Jedis jedis = getJedis();
        Set<String> set = jedis.zrangeByScore(key, min, max);
        returnJedis(jedis);
        return set;
    }

    /*
     * 接上面方法，获取有序集合长度
     */
    public long zlength(String key) {
        long len = 0;
        Set<String> set = zrange(key, 0, -1);
        len = set.size();
        return len;
    }

    /*
     * zincrby  为有序集 key 的成员 member 的 score 值加上增量 increment
     *
     * @return member 成员的新 score 值，以字符串形式表示
     */

    public double zincrby(String key, double score, String member) {
        Jedis jedis = getJedis();
        double s = jedis.zincrby(key, score, member);
        returnJedis(jedis);
        return s;
    }
    /*
     * zrank 返回有序集 key 中成员 member 的排名。其中有序集成员按 score 值递增(从小到大)顺序排列
     */

    public long zrank(String key, String member) {
        Jedis jedis = getJedis();
        long index = jedis.zrank(key, member);
        returnJedis(jedis);
        return index;
    }

    /*
     *zrevrank   返回有序集 key 中成员 member 的排名。其中有序集成员按 score 值递减(从大到小)排序。
     */
    public long zrevrank(String key, String member) {
        Jedis jedis = getJedis();
        long index = jedis.zrevrank(key, member);
        returnJedis(jedis);
        return index;
    }

    /*
     * zrem 移除有序集 key 中的一个或多个成员，不存在的成员将被忽略。当 key 存在但不是有序集类型时，返回一个错误。在 Redis 2.4 版本以前， ZREM 每次只能删除一个元素。
     * @return 被成功移除的成员的数量，不包括被忽略的成员
     */
    public long zrem(String key, String member) {
        Jedis jedis = getJedis();
        long count = jedis.zrem(key, member);
        returnJedis(jedis);
        return count;

    }

    /*
     *zremrangebyrank 移除有序集 key 中，指定排名(rank)区间内的所有成员。
     *@return 被移除成员的数量
     */
    public long zremrangeByRank(String key, int start, int end) {
        Jedis jedis = getJedis();
        long count = jedis.zremrangeByRank(key, start, end);
        returnJedis(jedis);
        return count;

    }


    /*
     * zremrangeByScore  删除指定权重区间的元素
     */
    public long zremrangeByScore(String key, double min, double max) {
        Jedis jedis = getJedis();
        long count = jedis.zremrangeByScore(key, min, max);
        returnJedis(jedis);
        return count;
    }


    /*
     * 获取给定值在集合中的权重
     */
    public double zscore(String key, String member) {
        Jedis jedis = getJedis();
        Double score = jedis.zscore(key, member);
        returnJedis(jedis);
        if (score != null) {
            return score;
        }
        return 0;
    }
}
