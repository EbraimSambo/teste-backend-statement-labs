package PROSEFA.app.stamp.domain.entity;

import PROSEFA.app.features.company.domain.entity.Company;

import java.util.UUID;

public class TaxStamp {
    private Long id;
    private UUID ref;
    private String code;
    private Company company;
    private FiscalStampStatus status;
}
