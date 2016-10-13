package com.fushionbaby.cache.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

import com.fushionbaby.cache.analyze.RedisConstant;

/**
 * @author 张明亮
 */
public class RedisUtil {
	private static ShardedJedisPool shardedJedisPool;// 切片连接池

	public RedisUtil() {
		initialShardedPool();
	}

	/**
	 * 初始化切片池
	 */
	private void initialShardedPool() {
		// 池基本配置
		JedisPoolConfig config = new JedisPoolConfig();
		config.setMaxActive(RedisConstant.REDIS_MAXACTIVE);
		config.setMaxIdle(RedisConstant.REDIS_MAXIDLE);
		config.setMaxWait(RedisConstant.REDIS_MAXWAIT);
		config.setTestOnBorrow(RedisConstant.REDIS_TESTONBORROW);
		// slave链接
		List<JedisShardInfo> shards = new ArrayList<JedisShardInfo>();
		shards.add(new JedisShardInfo(RedisConstant.REDIS_SERVER, RedisConstant.REDIS_PORT,
				RedisConstant.REDIS_MASTER));

		// 构造池
		shardedJedisPool = new ShardedJedisPool(config, shards);
	}

	/**
	 * 判断key是否在Redis存在,存在返回ture,否则返回false
	 * 
	 * @param key
	 * @return
	 */
	public boolean isExists(String key) {
		ShardedJedis shardedJedis = null;
		boolean flag = false;
		try {
			shardedJedis = shardedJedisPool.getResource();
			flag = shardedJedis.exists(key);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}finally{
			if(shardedJedis!=null){
				try {
					shardedJedisPool.returnResource(shardedJedis);
				} catch (Exception e2) {
					throw new RuntimeException(e2);
				}
			}
		}
		return flag;
	}

	/**
	 * 向list左边追加加元素
	 * 
	 * @param visitKey
	 * @param item
	 */
	public void lpush(String visitKey, String item) {
		ShardedJedis shardedJedis = null;
		try {
			shardedJedis = shardedJedisPool.getResource();
			shardedJedis.lpush(visitKey, item);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}finally{
			if(shardedJedis!=null){
				try {
					shardedJedisPool.returnResource(shardedJedis);
				} catch (Exception e2) {
					throw new RuntimeException(e2);
				}
			}
		}
	}

	/**
	 * 查询返回list中指定索引位置的值
	 * 
	 * @param key
	 * @param index
	 * @return
	 */
	public String lindex(String key, long index) {
		ShardedJedis shardedJedis = null;
		String result = null;
		try {
			shardedJedis = shardedJedisPool.getResource();
			result = shardedJedis.lindex(key, index);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}finally{
			if(shardedJedis!=null){
				try {
					shardedJedisPool.returnResource(shardedJedis);
				} catch (Exception e2) {
					throw new RuntimeException(e2);
				}
			}
		}
		return result;
	}

	/**
	 * 获取指定范围记录,可以做分页使用 end是-1的时候,从start位置到列表结束
	 * 
	 * @param key
	 * @param start
	 * @param end
	 * @return
	 */
	public List<String> lrange(String key, long start, long end) {
		ShardedJedis shardedJedis = null;
		List<String> list = new ArrayList<String>();
		try {
			shardedJedis = shardedJedisPool.getResource();
			list = shardedJedis.lrange(key, start, end);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}finally{
			if(shardedJedis!=null){
				try {
					shardedJedisPool.returnResource(shardedJedis);
				} catch (Exception e2) {
					throw new RuntimeException(e2);
				}
			}
		}
		return list;
	}

	/**
	 * 删除一个key
	 * 
	 * @param visitKey
	 * @return
	 */
	public long del(String visitKey) {
		ShardedJedis shardedJedis = null;
		long delNum = 0;
		try {
			shardedJedis = shardedJedisPool.getResource();
			delNum = shardedJedis.del(visitKey);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}finally{
			if(shardedJedis!=null){
				try {
					shardedJedisPool.returnResource(shardedJedis);
				} catch (Exception e2) {
					throw new RuntimeException(e2);
				}
			}
		}
		return delNum;
	}

	/**
	 * 删除指定index索引位置的list元素
	 * 
	 * @param visitKey
	 * @param index
	 * @return
	 */
	public long delIndex(String visitKey, long index) {
		ShardedJedis shardedJedis = null;
		long delNum = 0;
		try {
			shardedJedis = shardedJedisPool.getResource();
			shardedJedis.lset(visitKey, index, RedisConstant.REDIS_DELKEY);// 将原有索引位置内容替换设置成需要删除的标记
			delNum = shardedJedis.lrem(visitKey, 0, RedisConstant.REDIS_DELKEY);// 删除
		} catch (Exception e) {
			throw new RuntimeException(e);
		}finally{
			if(shardedJedis!=null){
				try {
					shardedJedisPool.returnResource(shardedJedis);
				} catch (Exception e2) {
					throw new RuntimeException(e2);
				}
			}
		}
		return delNum;
	}

	/**
	 * 删除模糊匹配的key
	 * 
	 * @param likeKey
	 *            模糊匹配的key
	 * @return 删除成功的条数
	 */
	public long delKeysLike(final String likeKey) {
		ShardedJedis shardedJedis = null;
		long count = 0;
		try {
			shardedJedis = shardedJedisPool.getResource();
			Collection<Jedis> jedisC = shardedJedis.getAllShards();
			Iterator<Jedis> iter = jedisC.iterator();
			while (iter.hasNext()) {
				Jedis _jedis = iter.next();
				Set<String> keys = _jedis.keys(likeKey + "*");
				count += _jedis.del(keys.toArray(new String[keys.size()]));
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}finally{
			if(shardedJedis!=null){
				try {
					shardedJedisPool.returnResource(shardedJedis);
				} catch (Exception e2) {
					throw new RuntimeException(e2);
				}
			}
		}
		return count;
	}

	/**
	 * 修改替换指定key指定index索引位置的值
	 * 
	 * @param visitKey
	 * @param index
	 * @param value
	 */
	public String lset(String visitKey, long index, String value) {
		ShardedJedis shardedJedis = null;
		String result = null;
		try {
			shardedJedis = shardedJedisPool.getResource();
			result = shardedJedis.lset(visitKey, index, value);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}finally{
			if(shardedJedis!=null){
				try {
					shardedJedisPool.returnResource(shardedJedis);
				} catch (Exception e2) {
					throw new RuntimeException(e2);
				}
			}
		}
		return result;
	}

	/**
	 * 获取集合总长度
	 * 
	 * @param visitKey
	 * @return
	 */
	public Long llen(String visitKey) {
		ShardedJedis shardedJedis = null;
		long len = 0;
		try {
			shardedJedis = shardedJedisPool.getResource();
			len = shardedJedis.llen(visitKey);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}finally{
			if(shardedJedis!=null){
				try {
					shardedJedisPool.returnResource(shardedJedis);
				} catch (Exception e2) {
					throw new RuntimeException(e2);
				}
			}
		}
		return len;
	}

	/**
	 * 向Redis保存一个字符串类型的 key-value键值对,如果原先有则把原先的替换掉
	 * 
	 * @param key
	 * @param value
	 */
	public void set(String key, String value) {
		ShardedJedis shardedJedis = null;
		try {
			shardedJedis = shardedJedisPool.getResource();
			shardedJedis.set(key, value);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}finally{
			if(shardedJedis!=null){
				try {
					shardedJedisPool.returnResource(shardedJedis);
				} catch (Exception e2) {
					throw new RuntimeException(e2);
				}
			}
		}
	}

	/**
	 * 根据key得到在Redis中的一个字符串
	 * 
	 * @param key
	 */
	public String get(String key) {
		ShardedJedis shardedJedis = null;
		String result = null;
		try {
			shardedJedis = shardedJedisPool.getResource();
			result = shardedJedis.get(key);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}finally{
			if(shardedJedis!=null){
				try {
					shardedJedisPool.returnResource(shardedJedis);
				} catch (Exception e2) {
					throw new RuntimeException(e2);
				}
			}
		}
		return result;
	}

	/**
	 * 向Redis保存 一个键值对的集合
	 * 
	 * @param key
	 *            redis储存名称
	 * @param field
	 *            键
	 * @param value
	 *            值
	 */
	public void hset(String key, String field, String value) {
		ShardedJedis shardedJedis = null;
		try {
			shardedJedis = shardedJedisPool.getResource();
			shardedJedis.hset(key, field, value);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}finally{
			if(shardedJedis!=null){
				try {
					shardedJedisPool.returnResource(shardedJedis);
				} catch (Exception e2) {
					throw new RuntimeException(e2);
				}
			}
		}
	}

	/**
	 * 通过redis储存名和键名查询值
	 * 
	 * @param key
	 *            redis储存名称
	 * @param field
	 *            键
	 */
	public boolean hexists(String key, String field) {
		ShardedJedis shardedJedis = null;
		boolean flag = false;
		try {
			shardedJedis = shardedJedisPool.getResource();
			flag = shardedJedis.hexists(key, field);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}finally{
			if(shardedJedis!=null){
				try {
					shardedJedisPool.returnResource(shardedJedis);
				} catch (Exception e2) {
					throw new RuntimeException(e2);
				}
			}
		}
		return flag;
	}

	/**
	 * 通过redis储存名和键名查询值获得 值
	 * 
	 * @param key
	 *            redis储存名称
	 * @param field
	 *            键
	 * @return
	 */
	public String hget(String key, String field) {
		ShardedJedis shardedJedis = null;
		String result = null;
		try {
			shardedJedis = shardedJedisPool.getResource();
			result = shardedJedis.hget(key, field);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}finally{
			if(shardedJedis!=null){
				try {
					shardedJedisPool.returnResource(shardedJedis);
				} catch (Exception e2) {
					throw new RuntimeException(e2);
				}
			}
		}
		return result;
	}
	
    public Long hdel(String key, String field) {
    	ShardedJedis shardedJedis = null;
		long delNum = 0;
		try {
			shardedJedis = shardedJedisPool.getResource();
			delNum = shardedJedis.hdel(key, field);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}finally{
			if(shardedJedis!=null){
				try {
					shardedJedisPool.returnResource(shardedJedis);
				} catch (Exception e2) {
					throw new RuntimeException(e2);
				}
			}
		}
		return delNum;
    }
}
