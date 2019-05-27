package lv.vlab.stars.repositories;

import lv.vlab.stars.models.Star;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface StarRepository extends CrudRepository<Star, Long> {
    Optional<Star> findByName(String name);
}
