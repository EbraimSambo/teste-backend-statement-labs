package PROSEFA.app.features.auditLog.domain.repository;

import PROSEFA.app.features.auditLog.domain.entity.ActionAuditLog;
import PROSEFA.app.features.auditLog.domain.entity.AuditLog;
import PROSEFA.app.features.company.domain.entity.Company;
import org.springframework.data.domain.Page;

public interface AuditLogRepository {
    AuditLog register(Company company, ActionAuditLog auditLog);
    Page<AuditLog> findALl(int page, int size);
}
