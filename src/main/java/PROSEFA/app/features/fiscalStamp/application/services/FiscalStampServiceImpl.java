package PROSEFA.app.features.fiscalStamp.application.services;

import PROSEFA.app.features.auditLog.adapters.primary.memory.AuditLogCapture;
import PROSEFA.app.features.auditLog.domain.entity.ActionAuditLog;
import PROSEFA.app.features.fiscalStamp.domain.entity.FiscalStamp;
import PROSEFA.app.features.fiscalStamp.domain.entity.FiscalStampStatus;
import PROSEFA.app.features.fiscalStamp.domain.repository.FiscalStampRepository;
import PROSEFA.app.features.fiscalStamp.domain.service.FiscalStampService;
import PROSEFA.app.shared.exception.BadRequestException;
import PROSEFA.app.shared.exception.ConflictException;
import PROSEFA.app.shared.exception.NotFoundException;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class FiscalStampServiceImpl implements FiscalStampService {
    private final FiscalStampRepository repository;
    private final ApplicationEventPublisher eventPublisher;

    public FiscalStampServiceImpl(FiscalStampRepository repository, ApplicationEventPublisher eventPublisher) {
        this.repository = repository;
        this.eventPublisher = eventPublisher;
    }



    @Override
    public FiscalStamp updateStatus(UUID ref, FiscalStampStatus newStatus) {
        FiscalStamp stamp = repository.findByRef(ref)
                .orElseThrow(()-> new NotFoundException("Selo fiscal não encontrado"));

        if (stamp.getStatus() == newStatus){
            eventPublisher.publishEvent(new AuditLogCapture(stamp.getCompany(), ActionAuditLog.INVALID_REQUEST));
           throw  new ConflictException("Não se pode fazer alteração para o mesmo estado já existente");
        }

        if(stamp.getValidateAt() != null){
            eventPublisher.publishEvent(new AuditLogCapture(stamp.getCompany(), ActionAuditLog.INVALID_REQUEST));
            throw  new ConflictException("Este selo ja foi validado");
        }

        if(newStatus == FiscalStampStatus.ISSUED){
            eventPublisher.publishEvent(new AuditLogCapture(stamp.getCompany(), ActionAuditLog.REQUEST_ISSUED));
        }

        return this.repository.updateStatus(ref, newStatus);
    }

    @Override
    public Optional<FiscalStamp> findByRef(UUID ref) {
        return this.repository.findByRef(ref);
    }

    @Override
    public FiscalStamp validate(UUID ref) {
        return this.findByRef(ref).orElseThrow(
                ()-> new NotFoundException("Selo nao encontrado")
        );
    }

    @Override
    public Page<FiscalStamp> findAll(int page, int size) {
        if (page < 0 || size <= 0) {
            throw new BadRequestException( "Parâmetros de paginação inválidos: page deve ser >= 0 e size deve ser > 0");
        }
        return this.repository.findAll(page,size);
    }
}
