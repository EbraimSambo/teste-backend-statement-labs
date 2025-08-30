package PROSEFA.app.features.auditLog.adapters.primary.memory;

import PROSEFA.app.features.auditLog.domain.entity.ActionAuditLog;
import PROSEFA.app.features.company.domain.entity.Company;

public class AuditLogCapture {

    private final Company company;
    private final ActionAuditLog auditLog;

    public AuditLogCapture (Company company, ActionAuditLog auditLog) {
        this.company = company;
        this.auditLog = auditLog; // faltava esta linha
    }

    public Company getCompany() {
        return company;
    }

    public ActionAuditLog getAction(){
        return auditLog;
    }
}
