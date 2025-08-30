package PROSEFA.app.features.fiscalStamp.adapters.primary.http.validation;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class   CreateFiscalStampRequest {
    @NotBlank(message = "O ref da empresa é obrigatório")
    String companyRef;
//    @NotBlank(message = "A data de experacao é obrigatório")
//    LocalDate expirationDate;
}
