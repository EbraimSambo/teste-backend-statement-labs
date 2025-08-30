package PROSEFA.app.features.company.adapters.primary.http.dto;

import PROSEFA.app.features.company.domain.entity.TypeCompany;

public enum TypeCompanyDto {
    MANUFACTURER,
    IMPORTER;

    public TypeCompany toDomain() {
        return TypeCompany.valueOf(this.name());
    }
}
