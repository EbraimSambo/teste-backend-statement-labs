package PROSEFA.app.features.user.adapters.secondary.mappers;

import PROSEFA.app.features.user.adapters.secondary.entity.UserEntity;
import PROSEFA.app.features.user.domain.entity.User;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class UserMapper {
    private Long id;
    private UUID ref;
    private String email;
    private String password;
    private String role;

    public static User toDomain(UserEntity entity) {
        User user = new User();
        user.setId(entity.getId());
        user.setRef(entity.getRef() != null ? entity.getRef() : UUID.randomUUID());
        user.setEmail(entity.getEmail());
        user.setPassword(entity.getPassword());
        user.setRole(entity.getRole());
        return user;
    }

    public static UserEntity toEntity(User domain) {
        UserEntity entity = new UserEntity();
        entity.setId(domain.getId());
        entity.setRef(domain.getRef());
        entity.setEmail(domain.getEmail());
        entity.setPassword(domain.getPassword());
        entity.setRole(domain.getRole());
        return entity;
    }
}
