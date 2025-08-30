package PROSEFA.app.features.auth.adapters.primary.http.controllers;

import PROSEFA.app.features.auth.adapters.primary.http.validation.AuthRequest;
import PROSEFA.app.features.auth.adapters.primary.http.validation.AuthResponse;
import PROSEFA.app.features.auth.infrastructure.security.JwtProvider;
import PROSEFA.app.features.user.domain.CreateUserUseCase;
import PROSEFA.app.features.user.domain.entity.User;
import PROSEFA.app.shared.api.ApiResponse;
import PROSEFA.app.shared.interceptors.ResponseBuilder;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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

@RestController
@RequestMapping("/auth")
@Tag(name = "Autenticação", description = "Endpoints para autenticação e registro de usuários")
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
    @Operation(
            summary = "Realizar login",
            description = "Autentica o usuário com email e senha e retorna um token JWT para acesso aos endpoints protegidos"
    )
    public ResponseEntity<ApiResponse<AuthResponse>> login(@Valid @RequestBody AuthRequest request) {
        Authentication auth = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );
        String token = jwtProvider.generateToken(auth.getName());
        return ResponseBuilder.created(new AuthResponse(token), "Login realizado com sucesso");
    }

    @PostMapping("/register")
    @Operation(
            summary = "Registrar usuário",
            description = "Cria uma nova conta de usuário com email e senha. A senha é criptografada antes de salvar no banco"
    )
    public ResponseEntity<ApiResponse<String>> register(@Valid @RequestBody AuthRequest request) {
        User u = new User();
        u.setEmail(request.getEmail());
        u.setPassword(passwordEncoder.encode(request.getPassword()));
        u.setRole("ROLE_USER");
        createUserUseCase.save(u);
        return ResponseBuilder.created("Registrado com sucesso", "");
    }
}
