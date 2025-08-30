package PROSEFA.app.features.company.adapters.primary.http.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import PROSEFA.app.features.company.domain.entity.Company;
import PROSEFA.app.features.company.domain.entity.CompanyStatus;
import PROSEFA.app.features.company.domain.entity.TypeCompany;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CompanyResponseDto {

    private UUID ref;
    private String name;
    private String nif;
    private CompanyStatus status;
    private TypeCompany type;
    private LocalDateTime createdAt;

    public static CompanyResponseDto toDto(Company company) {
        return new CompanyResponseDto(
                company.getRef(),
                company.getName(),
                company.getNif(),
                company.getStatus(),
                company.getType(),
                company.getCreatedAt()
        );
    }
}
