package PROSEFA.app.features.user.domain.repository;

import PROSEFA.app.features.user.domain.entity.User;

import java.util.Optional;

public interface UserRepository {
    Optional<User> finByEmail(String email);
    User save(User user);
}
