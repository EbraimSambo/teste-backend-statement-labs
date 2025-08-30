package PROSEFA.app.features.company.adapters.secondary.repository;

import java.util.Optional;
import java.util.UUID;

import PROSEFA.app.features.company.adapters.secondary.jpa.CompanyJpaRepository;
import PROSEFA.app.features.company.domain.entity.CompanyStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import PROSEFA.app.features.company.adapters.secondary.entity.CompanyEntity;
import PROSEFA.app.features.company.adapters.secondary.mappers.CompanyMapper;
import PROSEFA.app.features.company.domain.entity.Company;
import PROSEFA.app.features.company.domain.repository.CompanyRepository;
import PROSEFA.app.features.fiscalStamp.domain.entity.FiscalStampStatus;

@Service
public class CompanyRepositoryImpl implements CompanyRepository {

    private final CompanyJpaRepository companyRepository;
    private final CompanyMapper companyMapper;

    public CompanyRepositoryImpl(CompanyJpaRepository companyRepository, CompanyMapper companyMapper) {
        this.companyRepository = companyRepository;
        this.companyMapper = companyMapper;
    }

    @Override
    public Company save(Company company) {
        CompanyEntity entity = this.companyMapper.toEntity(company);
        CompanyEntity savedEntity = this.companyRepository.save(entity);
        return this.companyMapper.toDomain(savedEntity);
    }

    @Override
    public Optional<Company> findByRef(UUID ref) {
        Optional<CompanyEntity> entityOptional = companyRepository.findByRef(ref);
        return entityOptional.map(companyMapper::toDomain);
    }

    @Override
    public Optional<Company> findByNif(String nif) {
        Optional<CompanyEntity> entityOptional = companyRepository.findByNif(nif);
        return entityOptional.map(companyMapper::toDomain);
    }

    @Override
    public   boolean findActiveStamp(Long companyId, FiscalStampStatus status) {
        return this.companyRepository.existsActiveStamp(companyId, status);
    }

    @Override
    public Company updateStatus(Company company, CompanyStatus status) {
        CompanyEntity companyEntity = this.companyMapper.toEntity(company);
        companyEntity.setStatus(status);
         return this.companyMapper.toDomain(this.companyRepository.save(companyEntity));
    }

    @Override
    public Page<Company> findAllCompanies(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<CompanyEntity> entityPage = companyRepository.findAll(pageable);
        return entityPage.map(companyMapper::toDomain);
    }


}
