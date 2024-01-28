package io.anant.learnredis.documents;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.geo.Point;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import java.util.HashSet;
import java.util.Set;

@Data
@RequiredArgsConstructor(staticName = "of")
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@RedisHash
public class Company {
    @Id
    @NonNull
    private String id;

    @NonNull
    private String name;

    @Indexed
    private Set<String> tags = new HashSet<String>();

    @NonNull
    private String url;

    @NonNull
    @Indexed
    private Integer numberOfEmployees;

    @NonNull
    @Indexed
    private Integer yearFounded;

    private boolean publiclyListed;
}
