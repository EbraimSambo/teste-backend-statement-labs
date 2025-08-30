package PROSEFA.app.features.fiscalStamp.application.usecase;

import java.util.UUID;

import PROSEFA.app.features.auditLog.adapters.primary.memory.AuditEvent;
import PROSEFA.app.features.auditLog.adapters.primary.memory.AuditLogCapture;
import PROSEFA.app.features.auditLog.domain.entity.ActionAuditLog;
import PROSEFA.app.features.company.domain.entity.CompanyStatus;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import PROSEFA.app.features.company.domain.entity.Company;
import PROSEFA.app.features.company.domain.services.CompanyService;
import PROSEFA.app.features.fiscalStamp.domain.entity.FiscalStamp;
import PROSEFA.app.features.fiscalStamp.domain.entity.FiscalStampStatus;
import PROSEFA.app.features.fiscalStamp.domain.repository.FiscalStampRepository;
import PROSEFA.app.features.fiscalStamp.domain.usecase.CreateFiscalStampUseCase;
import PROSEFA.app.shared.exception.ConflictException;
import PROSEFA.app.shared.exception.NotFoundException;

@Service
public class CreateFiscalStampUseCaseImpl implements CreateFiscalStampUseCase {

    private final FiscalStampRepository repository;
    private final CompanyService companyService;
    private final ApplicationEventPublisher eventPublisher;

    public CreateFiscalStampUseCaseImpl(FiscalStampRepository repository, CompanyService companyService, ApplicationEventPublisher eventPublisher) {
        this.repository = repository;
        this.companyService = companyService;
        this.eventPublisher = eventPublisher;
    }

    @Override
    public FiscalStamp execute(UUID companyRef) {
        Company company = this.companyService.findByRef(companyRef).orElseThrow(
                () -> new NotFoundException("Empresa não encontrada!")
        );

        if(company.getStatus() == CompanyStatus.BLOCKED || company.getStatus() == CompanyStatus.SUSPENDED){
            eventPublisher.publishEvent(new AuditLogCapture(company, ActionAuditLog.INVALID_REQUEST));
            throw new SecurityException("De momento não está autorizado a esta ação");
        }

        boolean hasActive = this.companyService.existsActiveStamp(company.getId(), FiscalStampStatus.PENDING);
        if (hasActive) {
            eventPublisher.publishEvent(new AuditLogCapture(company, ActionAuditLog.INVALID_REQUEST));
            throw new ConflictException("A empresa já possui um selo em processso, espere terminar");
        }

        FiscalStamp stamp =  this.repository.generate(company);
        eventPublisher.publishEvent(new AuditLogCapture(company, ActionAuditLog.REQUEST_IN_PROCESS));
        return  stamp;
    }
}
