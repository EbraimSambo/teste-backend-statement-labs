package PROSEFA.app.features.stamp.adapters.secondary.repository;

import PROSEFA.app.features.stamp.adapters.secondary.entity.FiscalStampEntity;
import PROSEFA.app.features.stamp.adapters.secondary.jpa.FiscalStampJpaRepository;
import PROSEFA.app.features.stamp.adapters.secondary.mappers.FiscalStampMapper;
import PROSEFA.app.features.stamp.domain.entity.FiscalStamp;
import PROSEFA.app.features.stamp.domain.repository.FiscalStampRepository;
import org.springframework.stereotype.Repository;

@Repository
public class FiscalStampRepositoryImpl implements FiscalStampRepository {

    private final FiscalStampJpaRepository repository;
    private final FiscalStampMapper fiscalStampMapper;

    public FiscalStampRepositoryImpl(FiscalStampJpaRepository repository, FiscalStampMapper fiscalStampMapper) {
        this.repository = repository;
        this.fiscalStampMapper = fiscalStampMapper;
    }

    @Override
    public FiscalStamp generate(Long CompanyId) {
        FiscalStampEntity entity = this.fiscalStampMapper()
        return null;
    }
}
