package PROSEFA.app.features.auditLog.adapters.primary.memory;

import PROSEFA.app.features.auditLog.domain.repository.AuditLogRepository;
import PROSEFA.app.features.company.domain.entity.Company;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class AuditEvent {

    private final AuditLogRepository repository;

    public AuditEvent(AuditLogRepository repository) {
        this.repository = repository;
    }

    @EventListener
    public void handleUserCreated(AuditLogCapture auditLogCapture) {
        System.out.println(
                "📢 Listener recebeu evento: ação = " + auditLogCapture.getAction()
                        + " | Empresa = " + auditLogCapture.getCompany().getRef()
        );

        this.repository.register(auditLogCapture.getCompany(), auditLogCapture.getAction());
    }
}
