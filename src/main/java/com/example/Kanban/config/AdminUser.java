package com.example.Kanban.config;

import com.example.Kanban.repository.RoleRepository;
import com.example.Kanban.repository.UserRepository;
import com.example.Kanban.user.Role;
import com.example.Kanban.user.User;
import jakarta.transaction.Transactional;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Set;

@Configuration

public class AdminUser implements CommandLineRunner {
    private RoleRepository roleRepository;
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;


    public AdminUser(RoleRepository roleRepository, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    @Transactional
    public void run(String... args) throws Exception {
        Role roleAdmin = roleRepository.findByName(Role.Values.ADMIN.name())
                .orElseGet(() -> {
                    Role newRole = new Role();
                    newRole.setName(Role.Values.ADMIN.name());
                    return roleRepository.save(newRole); // Cria o Role ADMIN caso não exista
                });
        if (roleAdmin == null) {
            System.err.println("Role ADMIN não foi encontrada. Verifique se ela está corretamente cadastrada.");
            return; // Interrompe a execução caso o papel não exista
        }

        var userAdmin = userRepository.findByUsername("admin");

        userAdmin.ifPresentOrElse(
                user->{
                    System.out.println("Admin já existe");
                },
                ()->{
                    var user =  new User();
                    user.setUsername("admin");
                    user.setPassword(passwordEncoder.encode("123"));
                    user.setRoles(Set.of(roleAdmin));
                    userRepository.save(user);
                }
        );
    }
}
