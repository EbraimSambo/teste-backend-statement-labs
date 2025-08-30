package PROSEFA.app.features.company.adapters.secondary;

import PROSEFA.app.features.auditLog.adapters.secondary.entity.AuditLogEntity;
import PROSEFA.app.features.auditLog.domain.entity.ActionAuditLog;
import PROSEFA.app.features.auditLog.domain.entity.AuditLog;
import PROSEFA.app.features.company.adapters.secondary.mappers.CompanyMapper;
import PROSEFA.app.features.company.domain.entity.Company;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;

@Component
public class AuditLogMapper {
    private final CompanyMapper companyMapper;
    private Long id;
    private UUID ref;
    private LocalDateTime createdAt = LocalDateTime.now();
    private Company company;
    private String description;
    private ActionAuditLog action;

    public AuditLogMapper(CompanyMapper companyMapper) {
        this.companyMapper = companyMapper;
    }

    public AuditLog toDomain(AuditLogEntity entity){
        AuditLog auditLog =  new AuditLog();
        auditLog.setId(entity.getId());
        auditLog.setRef(entity.getRef());
        auditLog.setCreatedAt(entity.getCreatedAt());
        auditLog.setAction(entity.getAction());
        auditLog.setDescription(entity.getDescription());
        auditLog.setCompany(this.companyMapper.toDomain(entity.getCompany()));
        return auditLog;
    }

    public AuditLogEntity toEntity(AuditLog auditLog){
        AuditLogEntity entity = new AuditLogEntity();
        entity.setId(auditLog.getId());
        entity.setRef(auditLog.getRef() != null ? auditLog.getRef() : UUID.randomUUID());
        entity.setCreatedAt(auditLog.getCreatedAt());
        entity.setAction(entity.getAction());
        entity.setDescription(auditLog.getDescription());
        entity.setCompany(this.companyMapper.toEntity(auditLog.getCompany()));
        return entity;
    }
}
