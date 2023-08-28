package ru.tinkoff.handyman.model;


import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity(name= "Account")
@Table(name="account")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name="user_id",
            referencedColumnName = "id",
            nullable = false)
    private User user;

    private String cardNumbers;
    private PaySystem paySystem;
}
