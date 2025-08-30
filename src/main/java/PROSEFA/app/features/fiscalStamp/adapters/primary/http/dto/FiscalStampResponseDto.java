package PROSEFA.app.features.fiscalStamp.adapters.primary.http.dto;

import PROSEFA.app.features.company.adapters.primary.http.dto.CompanyResponseDto;
import PROSEFA.app.features.fiscalStamp.domain.entity.FiscalStamp;
import PROSEFA.app.features.fiscalStamp.domain.entity.FiscalStampStatus;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
public class FiscalStampResponseDto {

    private UUID ref;
    private String code;
    private FiscalStampStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime expirationDate;
    private CompanyResponseDto company;


    public static FiscalStampResponseDto toDto(FiscalStamp stamp){

        return new FiscalStampResponseDto(
                stamp.getRef(),
                stamp.getCode(),
                stamp.getStatus(),
                stamp.getCreatedAt(),
                stamp.getExpirationDate(),
                CompanyResponseDto.toDto(stamp.getCompany())
        );
    }

}
