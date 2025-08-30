package PROSEFA.app.features.user.adapters.secondary.jpa;

import PROSEFA.app.features.user.adapters.secondary.entity.UserEntity;
import PROSEFA.app.features.user.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserJpaRepository  extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByEmail(String email);
}
