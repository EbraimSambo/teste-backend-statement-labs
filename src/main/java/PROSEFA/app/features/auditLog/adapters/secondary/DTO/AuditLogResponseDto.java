package PROSEFA.app.features.auditLog.adapters.secondary.DTO;

import PROSEFA.app.features.auditLog.domain.entity.ActionAuditLog;
import PROSEFA.app.features.auditLog.domain.entity.AuditLog;
import PROSEFA.app.features.company.domain.entity.Company;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
public class AuditLogResponseDto {
    private UUID ref;
    private ActionAuditLog action;
    private  String description;
    private LocalDateTime createdAt = LocalDateTime.now();
    private Company company;


    public static AuditLogResponseDto toDto(AuditLog auditLog){
        return new AuditLogResponseDto(
                auditLog.getRef(),
                auditLog.getAction(),
                auditLog.getDescription(),
                auditLog.getCreatedAt(),
                auditLog.getCompany()
        );
    }
}
