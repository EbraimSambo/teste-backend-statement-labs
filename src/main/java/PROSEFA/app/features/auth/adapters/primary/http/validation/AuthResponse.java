package PROSEFA.app.features.auth.adapters.primary.http.validation;

import lombok.Data;

public class AuthResponse {
    private String token;
    public AuthResponse(String token){
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
