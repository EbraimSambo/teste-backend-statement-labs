package PROSEFA.app.features.fiscalStamp.adapters.secondary.jpa;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import PROSEFA.app.features.fiscalStamp.adapters.secondary.entity.FiscalStampEntity;

public interface FiscalStampJpaRepository  extends JpaRepository<FiscalStampEntity, Long> {
    Optional<FiscalStampEntity> findTopByYearOrderBySequenceDesc(int year);
    Optional<FiscalStampEntity> findByRef(UUID ref);
}
