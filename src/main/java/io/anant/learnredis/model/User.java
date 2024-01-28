package io.anant.learnredis.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Reference;
import org.springframework.data.annotation.Transient;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(onlyExplicitlyIncluded = true)
@JsonIgnoreProperties(value = { "password", "passwordConfirm" }, allowSetters = true)
@Data
@RedisHash
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable {

    @Id
    @ToString.Include
    private String id;

    @NotNull
    @Size(min = 2, max = 48)
    @ToString.Include
    private String name;

    @Email
    @EqualsAndHashCode.Include
    @ToString.Include
    @Indexed
    private String email;

    @NotNull
    @ToString.Include
    private String password;

    @Transient
    private String passwordConfirm;

    @Reference
    @ToString.Include
    private Set<Role> roles = new HashSet<Role>();

    public void addRole(Role role) {
        roles.add(role);
    }
}