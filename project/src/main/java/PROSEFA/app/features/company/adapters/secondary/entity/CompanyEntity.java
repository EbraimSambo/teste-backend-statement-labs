package PROSEFA.app.company.adapters.secondary.entity;

import PROSEFA.app.company.domain.entity.CompanyStatus;
import PROSEFA.app.company.domain.entity.TypeCompany;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity(name = "companies")
@Getter
@Setter
@NoArgsConstructor
public class CompanyEntity {
    @Id
    @GeneratedValue
    private Long id;
    @Column(nullable = false, unique = true, updatable = false)
    private UUID ref;
    private String nif;
    private String name;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TypeCompany type = TypeCompany.MANUFACTURER;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CompanyStatus status = CompanyStatus.ACTIVE;
    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    public void generateUUID() {
        if (this.ref == null) {
            this.ref = UUID.randomUUID();
        }
    }
}
