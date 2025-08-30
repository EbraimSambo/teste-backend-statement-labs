package PROSEFA.app.features.company.domain.usecase;

import PROSEFA.app.features.company.domain.entity.Company;

public interface CreateCompanyUseCase {
    Company execute(Company company);
}
