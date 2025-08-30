package PROSEFA.app.features.company.adapters.primary.http.controllers;

import java.util.UUID;

import PROSEFA.app.features.company.domain.entity.CompanyStatus;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import PROSEFA.app.features.company.adapters.primary.http.dto.CompanyResponseDto;
import PROSEFA.app.features.company.adapters.primary.http.validation.CreateCompanyRequest;
import PROSEFA.app.features.company.domain.entity.Company;
import PROSEFA.app.features.company.domain.services.CompanyService;
import PROSEFA.app.features.company.domain.usecase.CreateCompanyUseCase;
import PROSEFA.app.shared.api.ApiResponse;
import PROSEFA.app.shared.exception.BadRequestException;
import PROSEFA.app.shared.interceptors.ResponseBuilder;
import PROSEFA.app.shared.utils.UtilUUID;
import jakarta.validation.Valid;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/companies")
@Tag(name = "Empresas", description = "Endpoints para gerenciamento de empresas")
public class CompanyController {

    private final CreateCompanyUseCase createCompanyUseCase;
    private final CompanyService companyService;

    public CompanyController(CreateCompanyUseCase createCompanyUseCase, CompanyService companyService) {
        this.createCompanyUseCase = createCompanyUseCase;
        this.companyService = companyService;
    }

    @PostMapping
    @Operation(
            summary = "Criar empresa",
            description = "Registra uma nova empresa no sistema"
    )
    public ResponseEntity<ApiResponse<CompanyResponseDto>> create(
            @Valid @RequestBody CreateCompanyRequest request
    ) {
        Company saved = this.createCompanyUseCase.execute(request.toDomain());
        return ResponseBuilder.created(CompanyResponseDto.toDto(saved), "Empresa criada com sucesso!");
    }

    @GetMapping("/{ref}")
    @Operation(
            summary = "Buscar empresa por referência",
            description = "Retorna os dados de uma empresa a partir do seu UUID de referência"
    )

    public ResponseEntity<ApiResponse<CompanyResponseDto>> getCompanyByRef(
            @Parameter(description = "UUID da empresa", example = "550e8400-e29b-41d4-a716-446655440000")
            @PathVariable("ref") String ref
    ) {
        UUID companyRef = UtilUUID.parseUuid(ref)
                .orElseThrow(() -> new BadRequestException("O parametro 'companyRef' deve ser um UUID válido."));
        Company company = this.companyService.validate(companyRef);
        return ResponseBuilder.ok(CompanyResponseDto.toDto(company), "Empresa encontrada");
    }

    @PutMapping("/{ref}/status")
    @Operation(
            summary = "Alterar status da empresa",
            description = "Atualiza o estado da empresa (ATIVA, INATIVA, etc.)"
    )
    public ResponseEntity<ApiResponse<CompanyResponseDto>> updateStatus(
            @Parameter(description = "UUID da empresa", example = "550e8400-e29b-41d4-a716-446655440000")
            @PathVariable String ref,

            @Parameter(description = "Novo status da empresa", example = "ACTIVE")
            @RequestParam CompanyStatus status
    ){
        UUID companyRef = UtilUUID.parseUuid(ref)
                .orElseThrow(() -> new BadRequestException("O parametro 'companyRef' deve ser um UUID válido."));

        return ResponseBuilder.created(
                CompanyResponseDto.toDto(this.companyService.updateStatus(companyRef, status)),
                "Estado alterado"
        );
    }

    @GetMapping
    @Operation(
            summary = "Listar empresas",
            description = "Retorna uma lista paginada de empresas cadastradas"
    )
    public ResponseEntity<ApiResponse<Page<CompanyResponseDto>>> getAllCompanies(
            @Parameter(description = "Número da página (inicia em 0)", example = "0")
            @RequestParam(defaultValue = "0") int page,

            @Parameter(description = "Quantidade de registros por página", example = "10")
            @RequestParam(defaultValue = "10") int size
    ) {
        Page<Company> companies = this.companyService.findAllCompanies(page, size);
        Page<CompanyResponseDto> response = companies.map(CompanyResponseDto::toDto);
        return ResponseBuilder.ok(response, "Lista de empresas recuperada com sucesso");
    }
}
