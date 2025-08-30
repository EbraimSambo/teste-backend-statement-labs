package PROSEFA.app.features.auditLog.adapters.secondary.jpa;

import PROSEFA.app.features.auditLog.adapters.secondary.entity.AuditLogEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface AuditLogJpaRepository extends JpaRepository<AuditLogEntity, Long> {
  Optional<AuditLogEntity> findByRef(UUID ref);
}
