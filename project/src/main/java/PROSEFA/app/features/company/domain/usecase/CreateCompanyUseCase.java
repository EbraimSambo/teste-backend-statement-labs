package PROSEFA.app.company.domain.usecase;

import PROSEFA.app.company.domain.entity.Company;

public interface CreateCompanyUseCase {
    Company save(Company company);
}
