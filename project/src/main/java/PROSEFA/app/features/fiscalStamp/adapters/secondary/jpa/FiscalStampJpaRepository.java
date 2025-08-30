package PROSEFA.app.features.stamp.adapters.secondary.jpa;

import PROSEFA.app.features.stamp.adapters.secondary.entity.FiscalStampEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FiscalStampJpaRepository  extends JpaRepository<FiscalStampEntity, Long> {
    Optional<FiscalStampEntity> findTopByYearOrderBySequenceDesc(int year);
}
