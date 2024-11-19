package com.example.Kanban.user;

import com.example.Kanban.controller.dto.LoginRequest;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Entity(name = "users")
@Table(name = "users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class User{
    @Id @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "user_id")
    private UUID id;


    @Column(unique = true)
    private String username;


    private String password;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
            name = "tb_user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn (name = "role_id")
    )
    private Set<Role> roles;

    public boolean isLoginCorrect(LoginRequest credenciais, PasswordEncoder passwordEncoder) {
        return passwordEncoder.matches(credenciais.password(), this.password);
    }


}
