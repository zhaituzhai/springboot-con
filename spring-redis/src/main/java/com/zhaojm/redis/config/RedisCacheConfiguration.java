package com.zhaojm.redis.config;

/****
 * 配置Redis作为Mybatis的缓存
 *
 * 实现Mybatis的一个接口org.apache.ibatis.cache.Cache,这个接口设计了写缓存，读缓存，销毁缓存的方式，和访问控制读写锁
 * 这个类并不是由Spring虚拟机管理的类，但是，其中有一个静态属性jedisConnectionFactory需要注入一个Spring bean，
 * 也就是在RedisConfig中生成的bean。
 *
 * 在一个普通类中使用Spring虚拟机管理的Bean，一般使用Springboot自省的SpringContextAware。
 */

/*
@Configuration
@EnableCaching
public class RedisCacheConfiguration extends CachingConfigurerSupport {
    Logger logger = LoggerFactory.getLogger(RedisCacheConfiguration.class);

    @Value("${spring.redis.host}")
    private String host;

    @Value("${spring.redis.port}")
    private int port;

    @Value("${spring.redis.timeout}")
    private int timeout;

    @Value("${spring.redis.pool.max-idle}")
    private int maxIdle;

    @Value("${spring.redis.pool.min-idle}")
    private int minIdle;

    @Value("${spring.redis.pool.max-wait}")
    private long maxWaitMillis;

    @Value("${spring.redis.password}")
    private String password;

    @Value("${spring.redis.database}")
    private int database;

    @Bean(name = "jedisPool")
    public JedisPool redisPoolFactory() {
        logger.info("JedisPool注入成功！！");
        logger.info("redis地址：" + host + ":" + port);
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxIdle(maxIdle);
        jedisPoolConfig.setMaxWaitMillis(maxWaitMillis);

        JedisPool jedisPool = new JedisPool(jedisPoolConfig, host, port, timeout, password);
        return jedisPool;
    }
}
*/
