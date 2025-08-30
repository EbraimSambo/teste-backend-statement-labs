package PROSEFA.app.features.company.adapters.primary.http.validation;

import PROSEFA.app.features.company.adapters.primary.http.dto.TypeCompanyDto;
import PROSEFA.app.features.company.domain.entity.Company;
import PROSEFA.app.features.company.domain.entity.CompanyStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CreateCompanyRequest {

    @NotBlank(message = "O nome da empresa é obrigatório")
    @Size(min = 3, max = 100, message = "O nome deve ter entre 3 e 100 caracteres")
    private String name;

    @NotBlank(message = "O NIF é obrigatório")
    @Size(min = 9, max = 15, message = "O NIF deve ter entre 9 e 15 caracteres")
    private String nif;

    @NotNull(message = "O tipo da empresa é obrigatório")
    @ValidEnum(enumClass = TypeCompanyDto.class, message = "Tipo de empresa inválido. Use: MANUFACTURER ou IMPORTER")
    private TypeCompanyDto type;

    public Company toDomain() {
        Company company = new Company();
        company.setName(this.name);
        company.setNif(this.nif);
        company.setType(this.type.toDomain());
        company.setStatus(CompanyStatus.ACTIVE);
        return company;
    }
}
