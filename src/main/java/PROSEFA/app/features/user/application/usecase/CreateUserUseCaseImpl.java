package PROSEFA.app.features.user.application.usecase;

import PROSEFA.app.features.user.domain.CreateUserUseCase;
import PROSEFA.app.features.user.domain.entity.User;
import PROSEFA.app.features.user.domain.repository.UserRepository;
import PROSEFA.app.shared.exception.ConflictException;
import org.springframework.stereotype.Service;

@Service
public class CreateUserUseCaseImpl implements CreateUserUseCase {
    private final UserRepository userRepository;

    public CreateUserUseCaseImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User save(User user) {
    if(this.userRepository.finByEmail(user.getEmail()).isPresent()) throw  new ConflictException("Email ja usado");
    return this.userRepository.save(user);
    }
}
