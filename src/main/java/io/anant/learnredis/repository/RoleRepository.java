package io.anant.learnredis.repository;

import io.anant.learnredis.model.Role;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends CrudRepository<Role, String> {
    Role findFirstByname(String name);
}
