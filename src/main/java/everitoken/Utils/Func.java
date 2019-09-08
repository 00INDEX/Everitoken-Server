package everitoken.Utils;

import redis.clients.jedis.Jedis;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class Func {
    public static Jedis getRedis(int pool){
        Jedis jedis = new Jedis("49.234.155.178");
        jedis.auth("tKIYF4O^3x$!JWhE");
        jedis.connect();
        jedis.select(pool);
        return jedis;
    }

    public static void delRedis(Jedis jedis){
        jedis.disconnect();
    }
}
