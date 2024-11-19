package com.example.Kanban.user;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Entity(name = "roles")
@Table(name = "roles")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private Long id;
    private String name;


    public enum Values{
        ADMIN(1L),
        BASIC(2L);

        long roleId;

        Values(long roleId) {
            this.roleId = roleId;
        }
    }

}
