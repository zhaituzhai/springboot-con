package com.zhaojm.redis.config;

/***
 * RedisCacheTransfer是一个springboot bean，在容器创建之初进行初始化的时候，会注入jedisConnectionFactory bean给setJedisConnectionFactory方法的传参。
 *
 * 而setJedisConnectionFactory通过调用静态方法设置了类MybatisRedisCache的静态属性jedisConnectionFactory。
 *
 * 这样就把spring容器管理的jedisConnectionFactory注入到了静态域。
 *
 */

/*@Component
public class RedisCacheTransfer {

    @Autowired
    public void setJedisConnectionFactory(JedisConnectionFactory jedisConnectionFactory) {
        MybatisRedisCache.setJedisConnectionFactory(jedisConnectionFactory);
    }

}*/
