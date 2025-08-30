package PROSEFA.app.company.domain.entity;

import java.time.LocalDateTime;
import java.util.UUID;

public class Company {
    private Long id;
    private UUID ref;
    private String nif;
    private String name;
    private  TypeCompany type;
    private  CompanyStatus status;
    private LocalDateTime createdAt;


    public static Company createCompany() {
        return new Company();
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UUID getRef() {
        return ref;
    }

    public void setRef(UUID ref) {
        this.ref = ref;
    }

    public String getNif() {
        return nif;
    }

    public void setNif(String nif) {
        this.nif = nif;
    }

    public CompanyStatus getStatus() {
        return status;
    }

    public void setStatus(CompanyStatus status) {
        this.status = status;
    }

    public TypeCompany getType() {
        return type;
    }

    public void setType(TypeCompany type) {
        this.type = type;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
