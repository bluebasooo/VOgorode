package ru.tinkoff.rancher.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import ru.tinkoff.rancher.model.Rancher;

import java.util.UUID;

@Repository
public interface RancherRepository extends MongoRepository<Rancher, UUID> {

}
