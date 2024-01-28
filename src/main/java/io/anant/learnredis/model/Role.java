package io.anant.learnredis.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import java.io.Serializable;

@Data
@Builder
@RedisHash(value = "ROLE")
@AllArgsConstructor
@NoArgsConstructor
public class Role implements Serializable {
    @Id
    private String id;

    @Indexed
    private String name;
}
