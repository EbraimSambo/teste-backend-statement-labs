package PROSEFA.app.features.auditLog.domain.services;

import PROSEFA.app.features.auditLog.adapters.secondary.repository.AuditLogRepositoryImpl;
import PROSEFA.app.features.auditLog.domain.entity.AuditLog;
import org.springframework.data.domain.Page;

public interface AuditLogService {
    Page<AuditLog> findALl(int page, int size);
}
