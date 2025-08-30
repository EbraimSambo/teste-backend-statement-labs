package PROSEFA.app.features.company.adapters.primary.http;

import PROSEFA.app.features.company.domain.entity.TypeCompany;
import jakarta.validation.constraints.NotBlank;
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
    @ValidEnum(enumClass = TypeCompany.class, message = "Tipo de empresa inválido. Use: MANUFACTURER ou IMPORTER")
    private String type;
}
