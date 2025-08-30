package PROSEFA.app.features.fiscalStamp.adapters.primary.memory;

import PROSEFA.app.features.company.domain.entity.Company;
import org.springframework.context.event.EventListener;

public class AuditEvent {

    @EventListener
    public void handleUserCreated(Company company) {
        System.out.println("📢 Listener recebeu evento: usuário criado com ID " + company.getRef());
    }
}
