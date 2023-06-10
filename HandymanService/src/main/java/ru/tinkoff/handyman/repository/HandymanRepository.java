package ru.tinkoff.handyman.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import ru.tinkoff.handyman.model.Handyman;

import java.util.UUID;

@Repository
public interface HandymanRepository extends MongoRepository<Handyman, UUID> {

}
