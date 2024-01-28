package io.anant.learnredis.pubsub;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.anant.learnredis.model.User;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class RedisMessageSubscriber implements MessageListener {

    public static List<String> messageList = new ArrayList<String>();
    GenericJackson2JsonRedisSerializer serializer;

    @SneakyThrows
    public void onMessage(Message message, byte[] pattern) {

        byte[] body = message.getBody();

        User deserialize = serializer.deserialize(body, User.class);

        System.out.println("user = " + deserialize);
    }
}
