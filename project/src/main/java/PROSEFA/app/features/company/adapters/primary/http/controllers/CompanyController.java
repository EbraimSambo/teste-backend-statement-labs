package PROSEFA.app.features.company.adapters.primary.http;

import PROSEFA.app.features.company.adapters.primary.http.dto.CompanyResponseDto;
import PROSEFA.app.features.company.adapters.primary.http.validation.CreateCompanyRequest;
import PROSEFA.app.features.company.domain.entity.Company;
import PROSEFA.app.features.company.domain.entity.CompanyStatus;
import PROSEFA.app.features.company.domain.usecase.CreateCompanyUseCase;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/companies")
public class CompanyController {
    private final CreateCompanyUseCase createCompanyUseCase;
    public CompanyController(CreateCompanyUseCase createCompanyUseCase) {
        this.createCompanyUseCase = createCompanyUseCase;
    }

    @PostMapping
    public ResponseEntity<CompanyResponseDto> create(@Valid @RequestBody CreateCompanyRequest request) {
        Company saved = createCompanyUseCase.save(request.toDomain());

        return ResponseEntity.ok(
                new CompanyResponseDto(
                        saved.getRef(),
                        saved.getName(),
                        saved.getNif()
                )
        );
    }
}
