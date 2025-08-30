package PROSEFA.app.features.fiscalStamp.domain.usecase;

import PROSEFA.app.features.fiscalStamp.domain.entity.FiscalStamp;

import java.util.UUID;

public interface CreateFiscalStampUseCase {
   FiscalStamp execute(UUID companyRef);
}
