package io.anant.learnredis.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.anant.learnredis.pubsub.MessagePublisher;
import io.anant.learnredis.pubsub.RedisMessagePublisher;
import io.anant.learnredis.pubsub.RedisMessageSubscriber;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class ApplicationConfiguration {

    @Bean
    public GenericJackson2JsonRedisSerializer jackson2JsonRedisSerializer() {
        return new GenericJackson2JsonRedisSerializer();
    }

    @Bean
    @Primary
    public RedisTemplate<?, ?> redisTemplate(RedisConnectionFactory connectionFactory, GenericJackson2JsonRedisSerializer jackson2JsonRedisSerializer ) {
        RedisTemplate<?, ?> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(jackson2JsonRedisSerializer);
        template.setHashKeySerializer(new StringRedisSerializer());
        template.setHashValueSerializer(jackson2JsonRedisSerializer);
        return template;
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    MessageListenerAdapter messageListener(GenericJackson2JsonRedisSerializer serializer) {
        return new MessageListenerAdapter(new RedisMessageSubscriber(serializer));
    }

    @Bean
    RedisMessageListenerContainer redisContainer(RedisConnectionFactory connectionFactory, GenericJackson2JsonRedisSerializer serializer) {
        RedisMessageListenerContainer container
                = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.addMessageListener(messageListener(serializer), topic());
        return container;
    }

    @Bean
    MessagePublisher redisPublisher(RedisTemplate redisTemplate) {
        return new RedisMessagePublisher(redisTemplate, topic());
    }

    @Bean
    ChannelTopic topic() {
        return new ChannelTopic("config-server-update");
    }
}
