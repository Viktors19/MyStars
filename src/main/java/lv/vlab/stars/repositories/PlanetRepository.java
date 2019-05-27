package lv.vlab.stars.repositories;

import lv.vlab.stars.models.Planet;
import org.springframework.data.repository.CrudRepository;

public interface PlanetRepository extends CrudRepository<Planet, Long> {
}
