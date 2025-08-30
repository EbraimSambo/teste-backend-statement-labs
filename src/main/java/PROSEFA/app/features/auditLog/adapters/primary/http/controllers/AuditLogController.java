package PROSEFA.app.features.auditLog.adapters.primary.http.controllers;

import PROSEFA.app.features.auditLog.adapters.secondary.DTO.AuditLogResponseDto;
import PROSEFA.app.features.auditLog.domain.entity.AuditLog;
import PROSEFA.app.features.auditLog.domain.services.AuditLogService;
import PROSEFA.app.shared.api.ApiResponse;
import PROSEFA.app.shared.interceptors.ResponseBuilder;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/audit-logs")
@Tag(name = "Audit Logs", description = "Endpoints para consulta dos registros de auditoria do sistema")
public class AuditLogController {
    private final AuditLogService auditLogService;

    public AuditLogController(AuditLogService auditLogService) {
        this.auditLogService = auditLogService;
    }

    @GetMapping
    @Operation(
            summary = "Listar logs de auditoria",
            description = "Retorna uma lista paginada de logs de auditoria registrados no sistema"
    )
    public ResponseEntity<ApiResponse<Page<AuditLogResponseDto>>> findAll(
            @Parameter(description = "Número da página (inicia em 0)", example = "0")
            @RequestParam(defaultValue = "0") int page,

            @Parameter(description = "Quantidade de itens por página", example = "10")
            @RequestParam(defaultValue = "10") int size
    ){
        Page<AuditLog> logs = this.auditLogService.findALl(page, size);
        return ResponseBuilder.ok(logs.map(AuditLogResponseDto::toDto), "Lista de logs acessada com sucesso");
    }
}
