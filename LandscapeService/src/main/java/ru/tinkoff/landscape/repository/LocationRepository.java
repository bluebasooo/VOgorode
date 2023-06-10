package ru.tinkoff.landscape.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.tinkoff.landscape.model.Location;

import java.util.UUID;

@Repository
public interface LocationRepository extends CrudRepository<Location, UUID> {

}
