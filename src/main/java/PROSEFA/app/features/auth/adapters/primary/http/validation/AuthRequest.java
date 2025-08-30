package PROSEFA.app.features.auth.adapters.primary.http.validation;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AuthRequest {
    @Email(message = "Email invalido")
    @NotNull(message = "Email nao pode esta vazio e nulo")
    private  String email;
    @NotNull(message = "Palavara passe e obrigaotria")
    private  String password;
}
