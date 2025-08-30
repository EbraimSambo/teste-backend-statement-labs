package PROSEFA.app.features.auditLog.application.services;

import PROSEFA.app.features.auditLog.domain.entity.AuditLog;
import PROSEFA.app.features.auditLog.domain.repository.AuditLogRepository;
import PROSEFA.app.features.auditLog.domain.services.AuditLogService;
import PROSEFA.app.shared.exception.BadRequestException;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;


@Service
public class AuditLogServiceImpl implements AuditLogService {
    private final AuditLogRepository repository;

    public AuditLogServiceImpl(AuditLogRepository repository) {
        this.repository = repository;
    }

    @Override
    public Page<AuditLog> findALl(int page, int size) {
        if (page < 0 || size <= 0) {
            throw new BadRequestException( "Parâmetros de paginação inválidos: page deve ser >= 0 e size deve ser > 0");
        }
        return this.repository.findALl(page,size);
    }
}
