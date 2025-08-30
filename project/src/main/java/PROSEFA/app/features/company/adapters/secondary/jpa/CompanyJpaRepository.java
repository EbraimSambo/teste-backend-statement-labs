package PROSEFA.app.features.company.adapters.secondary.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import PROSEFA.app.features.company.adapters.secondary.entity.CompanyEntity;
import PROSEFA.app.features.fiscalStamp.domain.entity.FiscalStampStatus;

@Repository
public interface CompanyJpaRepository extends JpaRepository<CompanyEntity, Long> {
    Optional<CompanyEntity> findByRef(UUID ref);
    Optional<CompanyEntity> findByNif(String nif);
    @Query("""
        SELECT CASE WHEN COUNT(f) > 0 THEN true ELSE false END
        FROM fiscalStamps f
        WHERE f.company.id = :companyId
          AND f.status = :status
          AND f.expirationDate > CURRENT_TIMESTAMP
    """)
    boolean existsActiveStamp(Long companyId, FiscalStampStatus status);
}