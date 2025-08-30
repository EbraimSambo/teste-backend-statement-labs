package PROSEFA.app.features.user.domain.service;

import PROSEFA.app.features.user.adapters.secondary.entity.UserEntity;
import PROSEFA.app.features.user.domain.entity.User;

import java.util.Optional;

public interface UserService {
    Optional<User> findByEmail(String email);
}
