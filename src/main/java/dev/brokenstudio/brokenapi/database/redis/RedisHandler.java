package dev.brokenstudio.brokenapi.database.redis;

import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class RedisHandler {

    private final JedisPool pool;

    public RedisHandler(){
        pool = new JedisPool(new JedisPoolConfig(), "locaLhost", 6379, 5000);
    }

    public JedisPool getPool() {
        return pool;
    }

}
