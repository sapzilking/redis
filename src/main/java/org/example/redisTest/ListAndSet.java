package org.example.redisTest;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.List;
import java.util.Set;

public class ListAndSet {

    public static void ListAndSetTest() {
        try (var jedisPool = new JedisPool("127.0.0.1", 6379)) {
            try (Jedis jedis = jedisPool.getResource()) {
                // List

                // 1. stack
                jedis.rpush("stack1", "a");
                jedis.rpush("stack1", "b");
                jedis.rpush("stack1", "c");

                System.out.println(jedis.rpop("stack1"));
                System.out.println(jedis.rpop("stack1"));
                System.out.println(jedis.rpop("stack1"));

                List<String> stack1 = jedis.lrange("stack1", 0, -1);
                stack1.forEach(System.out::println);

                // 2. queue
                jedis.rpush("queue1", "d");
                jedis.rpush("queue1", "e");
                jedis.rpush("queue1", "f");

                System.out.println(jedis.lpop("queue1"));
                System.out.println(jedis.lpop("queue1"));
                System.out.println(jedis.lpop("queue1"));

//                List<String> queue1 = jedis.lrange("queue1", 0, -1);
//                queue1.forEach(System.out::println);

                // 3. block brpop
//                while (true) {
//                    List<String> blpop = jedis.blpop(10, "queue:blocking");
//                    if (blpop != null) {
//                        blpop.forEach(System.out::println);
//                    }
//                }

                // Set
                jedis.sadd("users:500:follow", "100", "200", "300");
                jedis.srem("users:500:follow", "100");

                Set<String> smembers = jedis.smembers("users:500:follow");
                smembers.forEach(System.out::println);

                System.out.println(jedis.sismember("users:500:follow", "200"));
                System.out.println(jedis.sismember("users:500:follow", "210"));

                System.out.println(jedis.scard("users:500:follow"));

                jedis.sadd("users:600:follow", "200", "300", "400");
                Set<String> sinter = jedis.sinter("users:500:follow", "users:600:follow");
                sinter.forEach(System.out::println);
            }
        }
    }
}
