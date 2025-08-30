package PROSEFA.app.features.stamp.adapters.secondary.entity;

import PROSEFA.app.features.company.adapters.secondary.entity.CompanyEntity;
import PROSEFA.app.features.stamp.domain.entity.FiscalStampStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity(name = "fiscalStamps")
@Getter
@Setter
@NoArgsConstructor
public class FiscalStampEntity {
    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false, unique = true, updatable = false)
    private UUID ref;

    @Column(nullable = false, unique = true)
    private String code;

    @ManyToOne(fetch = FetchType.LAZY) // muitos selos -> 1 empresa
    @JoinColumn(name = "company_id", nullable = false)
    private CompanyEntity company;

    @Enumerated(EnumType.STRING)
    private FiscalStampStatus status;

    @Column(nullable = false)
    private int year; // ano de emissão

    @Column(nullable = false)
    private int sequence; // sequência do ano

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime expirationDate;

    @PrePersist
    public void generateUUID() {
        if (this.ref == null) {
            this.ref = UUID.randomUUID();
        }
    }
}
