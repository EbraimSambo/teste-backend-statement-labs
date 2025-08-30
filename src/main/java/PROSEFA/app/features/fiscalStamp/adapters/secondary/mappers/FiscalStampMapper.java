package PROSEFA.app.features.fiscalStamp.adapters.secondary.mappers;

import PROSEFA.app.features.company.adapters.secondary.mappers.CompanyMapper;
import PROSEFA.app.features.fiscalStamp.adapters.secondary.entity.FiscalStampEntity;
import PROSEFA.app.features.fiscalStamp.domain.entity.FiscalStamp;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class FiscalStampMapper {
    private final CompanyMapper companyMapper;

    public FiscalStampMapper(CompanyMapper companyMapper) {
        this.companyMapper = companyMapper;
    }

    public FiscalStampEntity toEntity(FiscalStamp stamp){
        FiscalStampEntity entity = new FiscalStampEntity();
        entity.setId(stamp.getId());
        entity.setRef(stamp.getRef() != null ? stamp.getRef() : UUID.randomUUID());
        entity.setCode(stamp.getCode());
        entity.setStatus(stamp.getStatus());
        entity.setCreatedAt(stamp.getCreatedAt());
        entity.setExpirationDate(stamp.getExpirationDate());
        entity.setCompany(this.companyMapper.toEntity(stamp.getCompany()));
        entity.setYear(stamp.getYear());
        entity.setValidateAt(stamp.getValidateAt());
        entity.setSequence(stamp.getSequence());
        return entity;
    }

    public FiscalStamp toDomain(FiscalStampEntity entity){
        FiscalStamp stamp = new FiscalStamp();
        stamp.setId(entity.getId());
        stamp.setRef(entity.getRef());
        stamp.setValidateAt(entity.getValidateAt());
        stamp.setCode(entity.getCode());
        stamp.setCompany(this.companyMapper.toDomain(entity.getCompany()));
        stamp.setStatus(entity.getStatus());
        stamp.setCreatedAt(entity.getCreatedAt());
        stamp.setExpirationDate(entity.getExpirationDate());
        stamp.setYear(entity.getYear());
        stamp.setSequence(entity.getSequence());
        return stamp;
    }
}