package heedoitdox.deliverysystem.application;

import heedoitdox.deliverysystem.domain.User;
import heedoitdox.deliverysystem.domain.UserRepository;
import java.util.Optional;
import javax.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public Optional<User> getByIdentifier(String identifier) {
        return userRepository.findByIdentifier(identifier);
    }
}
