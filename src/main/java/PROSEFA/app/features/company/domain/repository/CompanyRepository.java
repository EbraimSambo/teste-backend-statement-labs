package PROSEFA.app.features.company.domain.repository;

import java.util.Optional;
import java.util.UUID;

import PROSEFA.app.features.company.domain.entity.Company;
import PROSEFA.app.features.company.domain.entity.CompanyStatus;
import PROSEFA.app.features.fiscalStamp.domain.entity.FiscalStampStatus;
import org.springframework.data.domain.Page;

public interface CompanyRepository {
    Company save(Company company);
    Optional<Company> findByRef(UUID ref);
    Optional<Company> findByNif(String nif);
    boolean findActiveStamp(Long companyId, FiscalStampStatus status);
    Company updateStatus(Company company, CompanyStatus status);
    Page<Company> findAllCompanies(int page, int size);
}
