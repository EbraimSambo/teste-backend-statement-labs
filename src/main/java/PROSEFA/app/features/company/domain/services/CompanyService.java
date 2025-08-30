package PROSEFA.app.features.company.domain.services;

import java.util.Optional;
import java.util.UUID;

import PROSEFA.app.features.company.domain.entity.Company;
import PROSEFA.app.features.company.domain.entity.CompanyStatus;
import PROSEFA.app.features.fiscalStamp.domain.entity.FiscalStampStatus;
import org.springframework.data.domain.Page;

public interface CompanyService {
    Optional<Company> findByRef(UUID ref);
    Optional<Company> findByNif(String nif);
    boolean existsActiveStamp(Long companyId, FiscalStampStatus status);
    Company validate(UUID ref);
    Company updateStatus(UUID ref, CompanyStatus status);
    Page<Company> findAllCompanies(int page, int size);
}
