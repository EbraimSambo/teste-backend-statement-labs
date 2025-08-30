package PROSEFA.app.features.fiscalStamp.domain.service;

import PROSEFA.app.features.fiscalStamp.domain.entity.FiscalStamp;
import PROSEFA.app.features.fiscalStamp.domain.entity.FiscalStampStatus;
import org.springframework.data.domain.Page;

import java.util.Optional;
import java.util.UUID;

public interface FiscalStampService {
    FiscalStamp updateStatus(UUID ref, FiscalStampStatus newStatus);
    Optional<FiscalStamp> findByRef(UUID ref);
    FiscalStamp validate(UUID ref);
    Page<FiscalStamp> findAll(int page, int size);
}
