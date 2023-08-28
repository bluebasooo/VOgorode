package ru.tinkoff.handyman.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.mongodb.repository.MongoRepository;
import ru.tinkoff.handyman.model.Account;

public interface AccountRepository extends JpaRepository<Account, Long> {
}
