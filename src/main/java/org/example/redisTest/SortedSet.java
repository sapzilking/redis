package org.example.redisTest;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.resps.Tuple;

import java.util.HashMap;
import java.util.List;

public class SortedSet {

    public static void test() {
        try (var jedisPool = new JedisPool("127.0.0.1", 6379)) {
            try (Jedis jedis = jedisPool.getResource()) {
                // sorted set
                var scores = new HashMap<String, Double>();
                scores.put("users1", 100.0);
                scores.put("users2", 30.0);
                scores.put("users3", 50.0);
                scores.put("users4", 70.0);
                scores.put("users5", 15.0);

                jedis.zadd("game:scores", scores);

                List<String> zrange = jedis.zrange("game:scores", 0, Long.MAX_VALUE);
                zrange.forEach(System.out::println);

                List<Tuple> tuples = jedis.zrangeWithScores("game:scores", 0, Long.MAX_VALUE);
                tuples.forEach(i -> System.out.println("%s %f".formatted(i.getElement(), i.getScore())));

                System.out.println(jedis.zcard("game:scores"));

                jedis.zincrby("game:scores", 100, "users5");

                List<Tuple> tuples2 = jedis.zrangeWithScores("game:scores", 0, Long.MAX_VALUE);
                tuples2.forEach(i -> System.out.println("%s %f".formatted(i.getElement(), i.getScore())));


            }
        }
    }

}
