package PROSEFA.app.company.adapters.secondary.repository;

import PROSEFA.app.company.adapters.secondary.entity.CompanyEntity;
import PROSEFA.app.company.adapters.secondary.mappers.CompanyMapper;
import PROSEFA.app.company.domain.entity.Company;
import PROSEFA.app.company.domain.repository.CompanyRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public class CompanyRepositoryImpl implements CompanyRepository {
    private final CompanyDataRepository companyRepository;
    private final CompanyMapper companyMapper;

    public CompanyRepositoryImpl(CompanyDataRepository companyRepository, CompanyMapper companyMapper) {
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

    @Repository
    public static interface CompanyDataRepository extends JpaRepository<CompanyEntity, Long> {
        Optional<CompanyEntity> findByRef(UUID ref);
    }
}