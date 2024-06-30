package org.example;

import redis.clients.jedis.GeoCoordinate;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.args.GeoUnit;
import redis.clients.jedis.params.GeoSearchParam;
import redis.clients.jedis.resps.GeoRadiusResponse;

import java.util.List;

public class RedisGeospatial {

    public static void test() {
        try (var jedisPool = new JedisPool("127.0.0.1", 6379)) {
            try (Jedis jedis = jedisPool.getResource()) {
                // geo add
                jedis.geoadd("stores1:geo", 127.02985530619755, 37.49911212874, "some1");
                jedis.geoadd("stores1:geo", 127.0333352287619, 37.491921163986234, "some2");

                // geo dist
                Double geodist = jedis.geodist("stores1:geo", "some1", "some2");
                System.out.println(geodist);

                // geo search
                List<GeoRadiusResponse> radiusResponseList = jedis.geosearch("stores1:geo",
                        new GeoCoordinate(127.033, 37.495),
                        500,
                        GeoUnit.M);

                radiusResponseList.forEach(response -> System.out.println(response.getMemberByString()));

                // 좌표 정보까지 함께 검색 및 출력
                List<GeoRadiusResponse> radiusResponseList1 = jedis.geosearch("stores1:geo",
                        new GeoSearchParam()
                                .fromLonLat(new GeoCoordinate(127.033, 37.495))
                                .byRadius(500, GeoUnit.M)
                                .withCoord());
                radiusResponseList1.forEach(response ->
                        System.out.println("%s %f %f".formatted(
                                response.getMemberByString(),
                                response.getCoordinate().getLatitude(),
                                response.getCoordinate().getLongitude())));

                // unlink
                jedis.unlink("stores1:geo");
            }
        }
    }
}
