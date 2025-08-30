package PROSEFA.app.features.fiscalStamp.infrastructure.utils;

import PROSEFA.app.features.fiscalStamp.domain.entity.FiscalStampStatus;

public class UtilValidateFiscalStamp {
    private void validateStatusTransition(FiscalStampStatus current, FiscalStampStatus next) {
        if (current == FiscalStampStatus.INVALIDATED) {
            throw new RuntimeException("Não é possível alterar um selo INVALIDATED");
        }
    }
}
