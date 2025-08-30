package PROSEFA.app.features.stamp.adapters.secondary.mappers;

import PROSEFA.app.features.stamp.adapters.secondary.entity.FiscalStampEntity;
import PROSEFA.app.features.stamp.domain.entity.FiscalStamp;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class FiscalStampMapper {
    public FiscalStampEntity toEntity(FiscalStamp stamp){
        FiscalStampEntity entity = new FiscalStampEntity();
        entity.setId(stamp.getId());
        entity.setRef(stamp.getRef() != null ? stamp.getRef() : UUID.randomUUID());
        entity.setCode(stamp.getCode());
        entity.setStatus(stamp.getStatus());
        entity.setCreatedAt(stamp.getCreatedAt());
        entity.setExpirationDate(stamp.getExpirationDate());
        entity.setYear(stamp.getYear());
        entity.setSequence(stamp.getSequence());
        return entity;
    }

    public FiscalStamp toDomain(FiscalStampEntity entity){
        FiscalStamp stamp = new FiscalStamp();
        stamp.setId(entity.getId());
        stamp.setRef(entity.getRef());
        stamp.setCode(entity.getCode());
        stamp.setStatus(entity.getStatus());
        stamp.setCreatedAt(entity.getCreatedAt());
        stamp.setExpirationDate(entity.getExpirationDate());
        stamp.setYear(entity.getYear());
        stamp.setSequence(entity.getSequence());
        return stamp;
    }
}