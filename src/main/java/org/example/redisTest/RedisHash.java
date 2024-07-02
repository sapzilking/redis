package org.example.redisTest;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.HashMap;
import java.util.Map;

public class RedisHash {

    public static void hashTest() {
        try (var jedisPool = new JedisPool("127.0.0.1", 6379)) {
            try (Jedis jedis = jedisPool.getResource()) {
                // hset
                jedis.hset("users:1:info", "name", "minsu");

                var userInfo = new HashMap<String, String>();
                userInfo.put("email", "minsu@abcd.co.kr");
                userInfo.put("phone", "010-1234-5678");

                jedis.hset("users:2:info", userInfo);

                // hdel
                jedis.hdel("users:2:info", "phone");

                // get, getAll
                System.out.println(jedis.hget("users:2:info", "email"));
                Map<String, String> user2Info = jedis.hgetAll("users:2:info");
                user2Info.forEach((k, v) -> System.out.println(k + " = " + v));

                // increment
                jedis.hincrBy("users:2:info", "visits", 1);
            }
        }
    }

}
