package PROSEFA.app.features.user.adapters.secondary.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity(name = "users")
@Getter
@Setter
@NoArgsConstructor
public class UserEntity {
    @Id
    @GeneratedValue
    private Long id;
    @Column(nullable = false, unique = true, updatable = false)
    private UUID ref;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String role;

    @PrePersist
    public void generateUUID() {
        if (this.ref == null) {
            this.ref = UUID.randomUUID();
        }
    }

    @Override
    public String toString() {
        return "UserEntity{id=" + id + ", email='" + email + "', role='" + role + "'}";
    }
}
