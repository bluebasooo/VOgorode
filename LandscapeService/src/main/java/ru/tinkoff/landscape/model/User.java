package ru.tinkoff.landscape.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "User")
@Table(name = "users")
public class User {

    @Column(name = "id",
            updatable = false
    )
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(name = "login")
    private String login;

    @Column(name = "email")
    private String email;

    @Column(name = "phone")
    private String phone;

    @Column(name = "users_type_id")
    private Integer userTypeId;

    @Column(name = "creates")
    private LocalDate creates;

    @Column(name = "updates")
    private LocalDate updates;

    @OneToOne(mappedBy = "user", cascade = {CascadeType.MERGE, CascadeType.REMOVE})
    Location location;
}
