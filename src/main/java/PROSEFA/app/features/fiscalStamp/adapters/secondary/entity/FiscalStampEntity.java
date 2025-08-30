package PROSEFA.app.features.fiscalStamp.adapters.secondary.entity;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;

import PROSEFA.app.features.company.adapters.secondary.entity.CompanyEntity;
import PROSEFA.app.features.fiscalStamp.domain.entity.FiscalStampStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

    @ManyToOne(fetch = FetchType.LAZY) 
    @JoinColumn(name = "company_id", nullable = false)
    private CompanyEntity company;

    @Enumerated(EnumType.STRING)
    private FiscalStampStatus status;

    @Column(nullable = false)
    private int year;

    @Column(nullable = false)
    private int sequence; 

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(nullable = true)
    private LocalDateTime validateAt;

    @Column(nullable = false)
    private LocalDateTime expirationDate;

    @PrePersist
    public void generateUUID() {
        if (this.ref == null) {
            this.ref = UUID.randomUUID();
        }
    }
}
