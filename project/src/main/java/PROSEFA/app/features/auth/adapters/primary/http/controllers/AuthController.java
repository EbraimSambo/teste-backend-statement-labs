package PROSEFA.app.features.auth.adapters.primary.http;

import PROSEFA.app.features.auth.adapters.primary.http.validation.AuthRequest;
import PROSEFA.app.features.auth.adapters.primary.http.validation.AuthResponse;
import PROSEFA.app.features.auth.infrastructure.security.JwtProvider;
import PROSEFA.app.features.user.domain.CreateUserUseCase;
import PROSEFA.app.features.user.domain.entity.User;
import PROSEFA.app.shared.api.ApiResponse;
import PROSEFA.app.shared.interceptors.ResponseBuilder;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthenticationManager authManager;
    private final JwtProvider jwtProvider;
    private final PasswordEncoder passwordEncoder;
    private final CreateUserUseCase createUserUseCase;

    public AuthController(AuthenticationManager authManager, JwtProvider jwtProvider, PasswordEncoder passwordEncoder, CreateUserUseCase createUserUseCase) {
        this.authManager = authManager;
        this.jwtProvider = jwtProvider;
        this.passwordEncoder = passwordEncoder;
        this.createUserUseCase = createUserUseCase;
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthResponse>> login(@Valid @RequestBody AuthRequest request) {
            Authentication auth = authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
            );
            String token = jwtProvider.generateToken(auth.getName());
            return ResponseBuilder.created(new AuthResponse(token), "Login com sucesso");
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<String>> register(@Valid @RequestBody AuthRequest request) {
        User u = new User();
        u.setEmail(request.getEmail());
        u.setPassword(passwordEncoder.encode(request.getPassword()));
        u.setRole("ROLE_USER");
        createUserUseCase.save(u);
        return ResponseBuilder.created("Registrado com sucesso", "");
    }
}