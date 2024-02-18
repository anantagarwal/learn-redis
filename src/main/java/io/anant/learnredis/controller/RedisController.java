package io.anant.learnredis.controller;

import io.anant.learnredis.model.Role;
import io.anant.learnredis.model.User;
import io.anant.learnredis.pubsub.MessagePublisher;
import lombok.AllArgsConstructor;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.AbstractMap.SimpleEntry;
import java.util.Map;

@RestController
@RequestMapping("/api/redis")
@AllArgsConstructor
public class RedisController {

    private static final String STRING_KEY_PREFIX = "redi2read:strings:";
    private RedisTemplate<String, Object> template;
    private RedisOperations redis;
    MessagePublisher publisher;

    @PostMapping("/strings")
    @ResponseStatus(HttpStatus.CREATED)
    public Map.Entry<String, String> setString(@RequestBody Map.Entry<String, String> kvp) {
        User adminUser = new User();
        adminUser.setName("Adminus Admistradore");
        adminUser.setEmail("admin@example.com");
        adminUser.setPassword("Reindeer Flotilla");//
        adminUser.addRole(Role.builder().name("admin").id("1").build());
        template.opsForValue().set(STRING_KEY_PREFIX + kvp.getKey(), kvp.getValue());
        template.opsForValue().set(STRING_KEY_PREFIX + kvp.getKey() + ":1", adminUser);
        redis.opsForValue().set(STRING_KEY_PREFIX + "adminus", adminUser);

        User fromRedis = (User)redis.opsForValue().get(STRING_KEY_PREFIX + "adminus");
        System.out.println("fromRedis = " + fromRedis);
        publisher.publish(adminUser);

        return kvp;
    }

    @GetMapping("/strings/{key}")
    public Map.Entry<String, String> getString(@PathVariable("key") String key) {
        String value = (String)template.opsForValue().get(STRING_KEY_PREFIX + key);

        if (value == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "key not found");
        }

        return new SimpleEntry<String, String>(key, value);
    }

    @PostMapping("/hash/{key}")
    public void postMap(@PathVariable("key") String key) {

        User adminUser = new User();
        adminUser.setName("Adminus Admistradore");
        adminUser.setEmail("admin@example.com");
        adminUser.setPassword("Reindeer Flotilla");//
        adminUser.addRole(Role.builder().name("admin").id("1").build());


        Role admin = Role.builder().name("admin").id("1").build();
        
        Map data = Map.of("admin", adminUser, "role", admin);
        
        template.opsForHash().put(key, "admin", adminUser);
        template.opsForHash().put(key, "admin1", adminUser);
        template.opsForHash().put(key, "role", admin);

        Object admin1 = template.opsForHash().get(key, "admin");
        System.out.println("admin1 = " + admin1);

    }

}
