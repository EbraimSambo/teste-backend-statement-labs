package PROSEFA.app.features.fiscalStamp.adapters.primary.http.controller;

import java.util.UUID;

import PROSEFA.app.features.fiscalStamp.adapters.primary.http.dto.FiscalStampResponseDto;
import PROSEFA.app.features.fiscalStamp.domain.entity.FiscalStampStatus;
import PROSEFA.app.features.fiscalStamp.domain.service.FiscalStampService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import PROSEFA.app.features.fiscalStamp.adapters.primary.http.validation.CreateFiscalStampRequest;
import PROSEFA.app.features.fiscalStamp.domain.entity.FiscalStamp;
import PROSEFA.app.features.fiscalStamp.domain.usecase.CreateFiscalStampUseCase;
import PROSEFA.app.shared.api.ApiResponse;
import PROSEFA.app.shared.exception.BadRequestException;
import PROSEFA.app.shared.interceptors.ResponseBuilder;
import PROSEFA.app.shared.utils.UtilUUID;
import jakarta.validation.Valid;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/fiscal-stamps")
@Tag(name = "Selos Fiscais", description = "Endpoints para gerenciamento de selos fiscais")
public class FiscalStampController {

    private final CreateFiscalStampUseCase stampUseCase;
    private final FiscalStampService fiscalStampService;

    public FiscalStampController(CreateFiscalStampUseCase stampUseCase, FiscalStampService fiscalStampService) {
        this.stampUseCase = stampUseCase;
        this.fiscalStampService = fiscalStampService;
    }

    @PostMapping
    @Operation(
            summary = "Criar selo fiscal",
            description = "Gera um novo selo fiscal vinculado a uma empresa"
    )
    public ResponseEntity<ApiResponse<FiscalStampResponseDto>> create(
            @Valid @RequestBody CreateFiscalStampRequest request
    ) {
        UUID companyRef = UtilUUID.parseUuid(request.getCompanyRef())
                .orElseThrow(() -> new BadRequestException("O campo 'companyRef' deve ser um UUID válido."));
        return ResponseBuilder.created(
                FiscalStampResponseDto.toDto(this.stampUseCase.execute(companyRef)),
                "Código gerado com sucesso"
        );
    }

    @GetMapping
    @Operation(
            summary = "Listar selos fiscais",
            description = "Retorna uma lista paginada de selos fiscais existentes"
    )
    public ResponseEntity<ApiResponse<Page<FiscalStampResponseDto>>> findAll(
            @Parameter(description = "Número da página (inicia em 0)", example = "0")
            @RequestParam(defaultValue = "0") int page,

            @Parameter(description = "Quantidade de registros por página", example = "10")
            @RequestParam(defaultValue = "10") int size
    ){
        Page<FiscalStamp> all = this.fiscalStampService.findAll(page, size);
        return ResponseBuilder.ok(all.map(FiscalStampResponseDto::toDto), "Lista de selos acessada com sucesso");
    }

    @GetMapping("/{ref}")
    private ResponseEntity<ApiResponse<FiscalStampResponseDto>> findOne(
            @PathVariable String ref
    ){
        UUID fiscalStampRef = UtilUUID.parseUuid(ref)
                .orElseThrow(() -> new BadRequestException("O campo 'companyRef' deve ser um UUID válido."));
        return ResponseBuilder.ok(FiscalStampResponseDto.toDto(this.fiscalStampService.validate(fiscalStampRef)),"Selo encontrado");
    }
    @PutMapping("/{ref}/status")
    @Operation(
            summary = "Alterar status do selo fiscal",
            description = "Atualiza o status de um selo fiscal (VALIDO, INVALIDO, EXPIRADO, etc.)"
    )
    public ResponseEntity<ApiResponse<FiscalStampResponseDto>> changeStatus(
            @Parameter(description = "UUID do selo fiscal", example = "550e8400-e29b-41d4-a716-446655440000")
            @PathVariable String ref,

            @Parameter(description = "Novo status do selo", example = "VALIDO")
            @RequestParam FiscalStampStatus status
    ){
        UUID fiscalStampRef = UtilUUID.parseUuid(ref)
                .orElseThrow(() -> new BadRequestException("O campo 'companyRef' deve ser um UUID válido."));
        return ResponseBuilder.created(
                FiscalStampResponseDto.toDto(this.fiscalStampService.updateStatus(fiscalStampRef, status)),
                "Selo validado com sucesso"
        );
    }
}
