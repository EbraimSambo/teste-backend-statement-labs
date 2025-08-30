package PROSEFA.app.company.application.usecase;

import PROSEFA.app.company.domain.entity.Company;
import PROSEFA.app.company.domain.repository.CompanyRepository;
import PROSEFA.app.company.domain.usecase.CreateCompanyUseCase;
import org.springframework.stereotype.Service;

@Service
public class CreateCompanyUseCaseImpl implements CreateCompanyUseCase {
    private final CompanyRepository companyRepository;

    public CreateCompanyUseCaseImpl(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    @Override
    public Company save(Company company) {
        return this.companyRepository.save(company);
    }
}
