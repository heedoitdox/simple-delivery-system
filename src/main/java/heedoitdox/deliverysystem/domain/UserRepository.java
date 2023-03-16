package heedoitdox.deliverysystem.domain;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long>{

    Optional<User> findByIdentifier(String identifier);
}
