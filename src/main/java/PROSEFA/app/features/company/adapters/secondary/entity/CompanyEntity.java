package PROSEFA.app.features.company.adapters.secondary.entity;

import PROSEFA.app.features.auditLog.adapters.secondary.entity.AuditLogEntity;
import PROSEFA.app.features.company.domain.entity.CompanyStatus;
import PROSEFA.app.features.company.domain.entity.TypeCompany;
import PROSEFA.app.features.fiscalStamp.adapters.secondary.entity.FiscalStampEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
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
    @Column(nullable = false, unique = true)
    private String nif;
    @Column(nullable = false)
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
    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FiscalStampEntity> fiscalStamps = new ArrayList<>();
    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AuditLogEntity> auditLogs = new ArrayList<>();
    @PrePersist
    public void generateUUID() {
        if (this.ref == null) {
            this.ref = UUID.randomUUID();
        }
    }
}
