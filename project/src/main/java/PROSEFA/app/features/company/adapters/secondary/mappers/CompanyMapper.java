package PROSEFA.app.company.adapters.secondary.mappers;

import PROSEFA.app.company.adapters.secondary.entity.CompanyEntity;
import PROSEFA.app.company.domain.entity.Company;

import java.util.UUID;

public class CompanyMapper {
    public CompanyEntity toEntity(Company company) {
        CompanyEntity entity = new CompanyEntity();
        entity.setId(company.getId());
        entity.setRef(company.getRef() != null ? company.getRef() : UUID.randomUUID());
        entity.setNif(company.getNif());
        entity.setName(company.getName());
        entity.setType(company.getType());
        entity.setStatus(company.getStatus());
        entity.setCreatedAt(company.getCreatedAt());
        return entity;
    }

    public Company toDomain(CompanyEntity entity) {
        Company company = new Company();
        company.setId(entity.getId());
        company.setRef(entity.getRef());
        company.setNif(entity.getNif());
        company.setName(entity.getName());
        company.setType(entity.getType());
        company.setStatus(entity.getStatus());
        company.setCreatedAt(entity.getCreatedAt());
        return company;
    }
}