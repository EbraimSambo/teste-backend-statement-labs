package PROSEFA.app.features.company.application.service;

import java.util.Optional;
import java.util.UUID;

import PROSEFA.app.features.auditLog.adapters.primary.memory.AuditLogCapture;
import PROSEFA.app.features.auditLog.domain.entity.ActionAuditLog;
import PROSEFA.app.features.company.domain.entity.CompanyStatus;
import PROSEFA.app.shared.exception.BadRequestException;
import PROSEFA.app.shared.exception.ConflictException;
import PROSEFA.app.shared.exception.NotFoundException;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import PROSEFA.app.features.company.domain.entity.Company;
import PROSEFA.app.features.company.domain.repository.CompanyRepository;
import PROSEFA.app.features.company.domain.services.CompanyService;
import PROSEFA.app.features.fiscalStamp.domain.entity.FiscalStampStatus;

@Service
public class CompanyServiceImpl implements CompanyService {
    private final CompanyRepository companyRepository;
    private final ApplicationEventPublisher eventPublisher;

    public CompanyServiceImpl(CompanyRepository companyRepository, ApplicationEventPublisher eventPublisher) {
        this.companyRepository = companyRepository;
        this.eventPublisher = eventPublisher;
    }

    @Override
    public Optional<Company> findByRef(UUID ref) {
        return this.companyRepository.findByRef(ref);
    }

    @Override
    public Optional<Company> findByNif(String nif) {
        return this.companyRepository.findByNif(nif);
    }

    @Override
    public boolean  existsActiveStamp(Long companyId, FiscalStampStatus status) {
        return this.companyRepository.findActiveStamp(companyId, status);
    }

    @Override
    public Company validate(UUID ref) {
        return this.companyRepository.findByRef(ref).orElseThrow(
                ()-> new NotFoundException("Empresa nao encontrada!")
        );
    }

    @Override
    public Company updateStatus(UUID ref, CompanyStatus status) {
        Company company = this.validate(ref);
        if(company.getStatus() == status){
            throw  new ConflictException("Não se pode fazer alteração para o mesmo estado já existente");
        }
       Company result = this.companyRepository.updateStatus(company, status);
        eventPublisher.publishEvent(new AuditLogCapture(company, this.generateActionLog(status)));
        return  result;
    }

    @Override
    public Page<Company> findAllCompanies(int page, int size) {
        if (page < 0 || size <= 0) {
            throw new BadRequestException("Parâmetros de paginação inválidos: page deve ser >= 0 e size deve ser > 0");
        }
        return this.companyRepository.findAllCompanies(page, size);
    }

    private ActionAuditLog generateActionLog(CompanyStatus status){
        if(status ==  CompanyStatus.SUSPENDED) return ActionAuditLog.COMPANY_SUSPENDED;
        if(status == CompanyStatus.BLOCKED) return ActionAuditLog.COMPANY_SUSPENDED;

        return ActionAuditLog.COMPANY_REACTIVATED;
    }
}
