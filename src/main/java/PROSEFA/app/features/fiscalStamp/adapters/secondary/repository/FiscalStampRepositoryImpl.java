package PROSEFA.app.features.fiscalStamp.adapters.secondary.repository;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import PROSEFA.app.shared.exception.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import PROSEFA.app.features.company.adapters.secondary.mappers.CompanyMapper;
import PROSEFA.app.features.company.domain.entity.Company;
import PROSEFA.app.features.fiscalStamp.adapters.secondary.entity.FiscalStampEntity;
import PROSEFA.app.features.fiscalStamp.adapters.secondary.jpa.FiscalStampJpaRepository;
import PROSEFA.app.features.fiscalStamp.adapters.secondary.mappers.FiscalStampMapper;
import PROSEFA.app.features.fiscalStamp.domain.entity.FiscalStamp;
import PROSEFA.app.features.fiscalStamp.domain.entity.FiscalStampStatus;
import PROSEFA.app.features.fiscalStamp.domain.repository.FiscalStampRepository;

@Repository
public class FiscalStampRepositoryImpl implements FiscalStampRepository {

    private final FiscalStampJpaRepository repository;
    private final FiscalStampMapper fiscalStampMapper;
    private final CompanyMapper companyMapper;

    public FiscalStampRepositoryImpl(FiscalStampJpaRepository repository, FiscalStampMapper fiscalStampMapper, CompanyMapper companyMapper) {
        this.repository = repository;
        this.fiscalStampMapper = fiscalStampMapper;
        this.companyMapper = companyMapper;
    }

    @Override
    public FiscalStamp generate(Company company) {
        int currentYear = LocalDateTime.now().getYear();

        var lastStampOpt = repository.findTopByYearOrderBySequenceDesc(currentYear);
        int nextSequence = lastStampOpt.map(FiscalStampEntity::getSequence).orElse(0) + 1;

        String code = String.format("PROSEFA-%d-%04d", currentYear, nextSequence);

        FiscalStampEntity entity = new FiscalStampEntity();
        entity.setYear(currentYear);
        entity.setSequence(nextSequence);
        entity.setCode(code);
        entity.setValidateAt(null);
        entity.setStatus(FiscalStampStatus.PENDING);
        entity.setExpirationDate(LocalDateTime.now().plusMonths(3));


        entity.setCompany(this.companyMapper.toEntity(company));

        FiscalStampEntity saved = repository.save(entity);

        return fiscalStampMapper.toDomain(saved);
    }

    @Override
    public FiscalStamp updateStatus(UUID ref, FiscalStampStatus newStatus) {
        FiscalStampEntity stamp = repository.findByRef(ref)
                .orElseThrow(()-> new NotFoundException("Selo fiscal não encontrado"));
        stamp.setStatus(newStatus);
        stamp.setValidateAt(LocalDateTime.now());
        return this.fiscalStampMapper.toDomain(repository.save(stamp));
    }

    @Override
    public Optional<FiscalStamp> findByRef(UUID ref) {
        Optional<FiscalStampEntity> stamp = this.repository.findByRef(ref);
        return  stamp.map(this.fiscalStampMapper::toDomain);
    }

    @Override
    public Page<FiscalStamp> findAll(int page, int size) {
        Pageable pageable = PageRequest.of(page,size);
        Page<FiscalStampEntity> fiscalStampEntities = this.repository.findAll(pageable);
        return fiscalStampEntities.map(fiscalStampMapper::toDomain);
    }

}
