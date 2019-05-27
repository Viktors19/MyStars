package lv.vlab.stars.repositories;

import lv.vlab.stars.models.Constellation;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ConstellationRepository extends CrudRepository<Constellation, Long> {
    Optional<Constellation> findByName(String name);
}
