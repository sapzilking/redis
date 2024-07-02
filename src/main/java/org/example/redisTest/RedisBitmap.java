package org.example.redisTest;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Pipeline;

import java.util.stream.IntStream;

public class RedisBitmap {
    public static void test() {
        try (var jedisPool = new JedisPool("127.0.0.1", 6379)) {
            try (Jedis jedis = jedisPool.getResource()) {
                jedis.setbit("request-somepage-20240630", 100, true);
                jedis.setbit("request-somepage-20240630", 200, true);
                jedis.setbit("request-somepage-20240630", 300, true);

                System.out.println(jedis.getbit("request-somepage-20240630", 100));
                System.out.println(jedis.getbit("request-somepage-20240630", 10));

                System.out.println(jedis.bitcount("request-somepage-20240630"));

                // bitmap vs set
                Pipeline pipelined = jedis.pipelined();
                IntStream.rangeClosed(0, 100000).forEach(i -> {
                    pipelined.sadd("request-somepage-set-20240630", String.valueOf(i), "1");
                    pipelined.setbit("request-somepage-bit-20240630-bitmap", i, true);

                    if (i == 1000) {
                        pipelined.sync();
                    }
                });
                pipelined.sync();
            }
        }
    }
}
