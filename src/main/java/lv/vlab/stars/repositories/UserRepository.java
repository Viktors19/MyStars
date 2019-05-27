package lv.vlab.stars.repositories;

import lv.vlab.stars.models.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {
    Optional<User> findByApiKey(String apiKey);
}
