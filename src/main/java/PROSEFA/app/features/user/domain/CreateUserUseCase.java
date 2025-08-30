package PROSEFA.app.features.user.domain;

import PROSEFA.app.features.user.domain.entity.User;

public interface CreateUserUseCase {
    User save(User user);
}
