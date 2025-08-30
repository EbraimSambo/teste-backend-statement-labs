package PROSEFA.app.features.auditLog.adapters.secondary.repository;

import PROSEFA.app.features.auditLog.adapters.secondary.entity.AuditLogEntity;
import PROSEFA.app.features.auditLog.adapters.secondary.jpa.AuditLogJpaRepository;
import PROSEFA.app.features.auditLog.domain.entity.ActionAuditLog;
import PROSEFA.app.features.auditLog.domain.entity.AuditLog;
import PROSEFA.app.features.auditLog.domain.repository.AuditLogRepository;
import PROSEFA.app.features.company.adapters.secondary.AuditLogMapper;
import PROSEFA.app.features.company.adapters.secondary.mappers.CompanyMapper;
import PROSEFA.app.features.company.domain.entity.Company;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public class AuditLogRepositoryImpl implements AuditLogRepository {

    private final AuditLogJpaRepository auditLogJpaRepository;
    private final AuditLogMapper auditLogMapper;
    private  final CompanyMapper companyMapper;

    public AuditLogRepositoryImpl(AuditLogJpaRepository auditLogJpaRepository, AuditLogMapper auditLogMapper, CompanyMapper companyMapper) {
        this.auditLogJpaRepository = auditLogJpaRepository;
        this.auditLogMapper = auditLogMapper;
        this.companyMapper = companyMapper;
    }

    @Override
    public AuditLog register(Company company, ActionAuditLog auditLog) {
        AuditLogEntity auditLogEntity = new AuditLogEntity();
        auditLogEntity.setAction(auditLog);
        auditLogEntity.setDescription(this.generateDescription(auditLog));
        auditLogEntity.setCompany(companyMapper.toEntity(company));
        auditLogJpaRepository.save(auditLogEntity);
        return this.auditLogMapper.toDomain(auditLogEntity);
    }

    @Override
    public Page<AuditLog> findALl(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<AuditLogEntity> auditLogEntities = this.auditLogJpaRepository.findAll(pageable);
        return auditLogEntities.map(auditLogMapper::toDomain);
    }

    private String generateDescription(ActionAuditLog auditLog){
        if (auditLog == ActionAuditLog.REQUEST_IN_PROCESS) return "SOLICITAÇÃO EM PROCESSO";
        if(auditLog == ActionAuditLog.REQUEST_ISSUED) return "PEDIDO EMISSO";
        if(auditLog == ActionAuditLog.COMPANY_DEACTIVATED) return "EMPRESA DESATIVADA";
        if(auditLog == ActionAuditLog.CREATE_COMPANY) return "CRIAR EMPRESA)";
        if(auditLog == ActionAuditLog.VALIDATION_PERFORMED) return "VALIDAÇÃO EXECUTADA";
        if(auditLog == ActionAuditLog.REQUEST_BLOCKED) return "SOLICITAÇÃO BLOQUEADA";
        if(auditLog == ActionAuditLog.INVALID_REQUEST) return "PEDIDO INVÁLIDO";
        if(auditLog == ActionAuditLog.COMPANY_REACTIVATED) return "EMPRESA REATIVADA";
        if(auditLog == ActionAuditLog.COMPANY_SUSPENDED) return "EMPRESA SUSPENSA";
        return "SEM SESCRICAO";
    }

}
