package io.anant.learnredis.command;

import io.anant.learnredis.model.Role;
import io.anant.learnredis.repository.RoleRepository;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(1)
@AllArgsConstructor
public class CreateRoles implements CommandLineRunner {

    private RoleRepository roleRepository;

    @Override
    public void run(String... args) throws Exception {
        System.out.println(">>> Hello from CreateRoles CommandLineRunner");
        if (roleRepository.count() == 0 && false) {
            Role adminRole = Role.builder().name("admin").build();
            Role customerRole = Role.builder().name("customer").build();
            roleRepository.save(adminRole);
            roleRepository.save(customerRole);
            System.out.println("Created admin and customer roles");
        }
    }
}
