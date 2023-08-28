package ru.tinkoff.handyman.model;

import io.hypersistence.utils.hibernate.type.array.ListArrayType;
import lombok.*;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity(name="User")
@Table(name="users")
@TypeDef(name = "list-array", typeClass = ListArrayType.class)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String surname;

    @Type(type = "list-array")
    @Column(name="skills", columnDefinition = "text[]")
    private List<String> skills;

    private String email;
    private String phone;

    @Lob
    @Type(type = "org.hibernate.type.BinaryType")
    private byte[] photo;

    @OneToMany(
            mappedBy = "user",
            cascade = {CascadeType.PERSIST ,CascadeType.REMOVE},
            orphanRemoval = true)
    private List<Account> accounts;
}
