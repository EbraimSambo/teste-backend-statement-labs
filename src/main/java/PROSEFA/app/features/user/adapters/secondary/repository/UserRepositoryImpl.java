package PROSEFA.app.features.user.adapters.secondary.repository;

import PROSEFA.app.features.company.adapters.secondary.entity.CompanyEntity;
import PROSEFA.app.features.user.adapters.secondary.entity.UserEntity;
import PROSEFA.app.features.user.adapters.secondary.jpa.UserJpaRepository;
import PROSEFA.app.features.user.adapters.secondary.mappers.UserMapper;
import PROSEFA.app.features.user.domain.entity.User;
import PROSEFA.app.features.user.domain.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserRepositoryImpl implements UserRepository {
    private final UserJpaRepository userJpaRepository;

    public UserRepositoryImpl(UserJpaRepository userJpaRepository) {
        this.userJpaRepository = userJpaRepository;
    }

    @Override
    public Optional<User> finByEmail(String email) {
        return this.userJpaRepository.findByEmail(email).map(UserMapper::toDomain);
    }

    @Override
    public User save(User user) {
        return UserMapper.toDomain(this.userJpaRepository.save(UserMapper.toEntity(user)));
    }
}
