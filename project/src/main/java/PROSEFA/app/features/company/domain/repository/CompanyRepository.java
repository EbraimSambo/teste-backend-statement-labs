package PROSEFA.app.company.domain.repository;

import PROSEFA.app.company.domain.entity.Company;

import java.util.Optional;
import java.util.UUID;

public interface CompanyRepository {
    Company save(Company company);
    Optional<Company> findByRef(UUID ref);
}
