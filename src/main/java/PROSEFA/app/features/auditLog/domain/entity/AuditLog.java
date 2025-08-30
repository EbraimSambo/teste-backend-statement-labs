package PROSEFA.app.features.auditLog.domain.entity;

import PROSEFA.app.features.company.domain.entity.Company;

import java.time.LocalDateTime;
import java.util.UUID;

public class AuditLog {
    private Long id;
    private UUID ref;
    private LocalDateTime createdAt = LocalDateTime.now();
    private  String description;
    private ActionAuditLog action;
    private Company company;


    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public UUID getRef() {
        return ref;
    }

    public void setRef(UUID ref) {
        this.ref = ref;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ActionAuditLog getAction() {
        return action;
    }

    public void setAction(ActionAuditLog action) {
        this.action = action;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
