package PROSEFA.app.features.stamp.domain.repository;

import PROSEFA.app.features.stamp.domain.entity.FiscalStamp;

public interface FiscalStampRepository {
    FiscalStamp generate(Long CompanyId);
}
