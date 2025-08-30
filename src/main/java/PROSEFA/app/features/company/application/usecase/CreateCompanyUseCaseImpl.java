package PROSEFA.app.features.company.application.usecase;

import PROSEFA.app.features.auditLog.adapters.primary.memory.AuditLogCapture;
import PROSEFA.app.features.auditLog.domain.entity.ActionAuditLog;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import PROSEFA.app.features.company.domain.entity.Company;
import PROSEFA.app.features.company.domain.repository.CompanyRepository;
import PROSEFA.app.features.company.domain.services.CompanyService;
import PROSEFA.app.features.company.domain.usecase.CreateCompanyUseCase;
import PROSEFA.app.shared.exception.ConflictException;

@Service
public class CreateCompanyUseCaseImpl implements CreateCompanyUseCase {
    private final CompanyRepository companyRepository;
    private final CompanyService companyService;
    private final ApplicationEventPublisher eventPublisher;

    public CreateCompanyUseCaseImpl(CompanyRepository companyRepository, CompanyService companyService, ApplicationEventPublisher eventPublisher) {
        this.companyRepository = companyRepository;
        this.companyService = companyService;
        this.eventPublisher = eventPublisher;
    }

    @Override
    public Company execute(Company company) {
        companyService.findByNif(company.getNif())
                .ifPresent(existing -> {
                    throw new ConflictException("Já existe uma empresa com o NIF: " + company.getNif());
                });

      Company newCompany =  this.companyRepository.save(company);
      eventPublisher.publishEvent(new AuditLogCapture(newCompany, ActionAuditLog.CREATE_COMPANY));
      return newCompany;
    }
}
