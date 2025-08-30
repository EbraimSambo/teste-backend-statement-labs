package PROSEFA.app.features.fiscalStamp.domain.entity;

import PROSEFA.app.features.company.domain.entity.Company;

import java.time.LocalDateTime;
import java.util.UUID;

public class FiscalStamp {
    private Long id;
    private UUID ref;
    private String code;
    private Company company;
    private FiscalStampStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime expirationDate;
    private int year;
    private int sequence;
    private LocalDateTime validateAt;
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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public FiscalStampStatus getStatus() {
        return status;
    }

    public void setStatus(FiscalStampStatus status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(LocalDateTime expirationDate) {
        this.expirationDate = expirationDate;
    }

    public int getSequence() {
        return sequence;
    }

    public void setSequence(int sequence) {
        this.sequence = sequence;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public LocalDateTime getValidateAt() {
        return validateAt;
    }

    public void setValidateAt(LocalDateTime validateAt) {
        this.validateAt = validateAt;
    }
}
