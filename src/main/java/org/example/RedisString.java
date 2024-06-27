package org.example;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Pipeline;

import java.util.List;

public class RedisString {

    public static void StringTest() {
        try (var jedisPool = new JedisPool("127.0.0.1", 6379)) {
            try (Jedis jedis = jedisPool.getResource()) {
                jedis.set("users:300:email", "kim@fast.co.kr");
                jedis.set("users:300:name", "minsu");
                jedis.set("users:300:age", "30");

                var userEmail = jedis.get("users:300:email");
                System.out.println("userEmail = " + userEmail);

                List<String> userInfo = jedis.mget("users:300:email", "users:300:name", "users:300:age");
                userInfo.forEach(System.out::println);

                long counter = jedis.incr("counter");
                System.out.println("counter = " + counter);

                counter = jedis.incrBy("counter", 10L);
                System.out.println("counter = " + counter);

                counter = jedis.decr("counter");
                System.out.println("counter = " + counter);

                counter = jedis.decrBy("counter", 20L);
                System.out.println("counter = " + counter);

                Pipeline pipelined = jedis.pipelined();
                pipelined.set("users:400:email", "greg@fast.co.kr");
                pipelined.set("users:400:name", "greg");
                pipelined.set("users:400:age", "40");
                List<Object> results = pipelined.syncAndReturnAll();
                results.forEach(i -> System.out.println(i.toString()));
            }
        }
    }

}
