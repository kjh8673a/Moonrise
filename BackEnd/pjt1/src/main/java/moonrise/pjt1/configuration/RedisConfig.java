package moonrise.pjt1.configuration;

import io.lettuce.core.ReadFrom;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStaticMasterReplicaConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;

@Configuration @RequiredArgsConstructor
public class RedisConfig extends CachingConfigurerSupport {
    private final RedisInfo info;

//
//    @Bean
//    public RedisConnectionFactory redisConnectionFactory() {
//        return new LettuceConnectionFactory(host, port);
//    }

    @Bean
    public LettuceConnectionFactory redisConnectionFactory(){
        LettuceClientConfiguration clientConfig = LettuceClientConfiguration.builder()
                .readFrom(ReadFrom.REPLICA_PREFERRED)	// replica에서 우선적으로 읽지만 replica에서 읽어오지 못할 경우 Master에서 읽어옴
                .build();
        // replica 설정
        RedisStaticMasterReplicaConfiguration slaveConfig = new RedisStaticMasterReplicaConfiguration(info.getMaster().getHost(), info.getMaster().getPort());
        // 설정에 slave 설정 값 추가
        info.getSlaves().forEach(slave -> slaveConfig.addNode(slave.getHost(), slave.getPort()));
        return new LettuceConnectionFactory(slaveConfig, clientConfig);
    }
    @Bean
    public RedisTemplate<?, ?> redisTemplate() {
        RedisTemplate<byte[], byte[]> template = new RedisTemplate<>();
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new StringRedisSerializer());
        template.setConnectionFactory(redisConnectionFactory());
        return template;
    }


}
