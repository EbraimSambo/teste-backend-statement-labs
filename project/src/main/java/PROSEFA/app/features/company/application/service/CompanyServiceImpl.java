package PROSEFA.app.company.application.service;

import PROSEFA.app.company.domain.entity.Company;
import PROSEFA.app.company.domain.repository.CompanyRepository;
import PROSEFA.app.company.domain.services.CompanyService;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class CompanyServiceImpl implements CompanyService {
    private final CompanyRepository companyRepository;

    public CompanyServiceImpl(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    @Override
    public Optional<Company> findByRef(UUID ref) {
        return Optional.empty();
    }
}
