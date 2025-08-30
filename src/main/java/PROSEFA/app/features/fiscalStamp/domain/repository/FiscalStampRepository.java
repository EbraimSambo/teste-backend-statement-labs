package PROSEFA.app.features.fiscalStamp.domain.repository;

import PROSEFA.app.features.company.domain.entity.Company;
import PROSEFA.app.features.fiscalStamp.domain.entity.FiscalStamp;
import PROSEFA.app.features.fiscalStamp.domain.entity.FiscalStampStatus;
import org.springframework.data.domain.Page;

import java.util.Optional;
import java.util.UUID;

public interface FiscalStampRepository {
    FiscalStamp generate(Company  company);
    FiscalStamp  updateStatus(UUID ref, FiscalStampStatus newStatus);
    Optional<FiscalStamp> findByRef(UUID ref);
    Page<FiscalStamp> findAll(int page, int size);
}
