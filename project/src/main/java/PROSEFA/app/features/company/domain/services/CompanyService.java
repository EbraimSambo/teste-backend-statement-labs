package PROSEFA.app.company.domain.services;

import PROSEFA.app.company.domain.entity.Company;

import java.util.Optional;
import java.util.UUID;

public interface CompanyService {
    Optional<Company> findByRef(UUID ref);
}
