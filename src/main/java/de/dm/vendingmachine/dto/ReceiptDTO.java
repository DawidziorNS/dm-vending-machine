package de.dm.vendingmachine.dto;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.Map;

@Builder
@Getter
public class ReceiptDTO {
    private Map<ProductDTO, BigDecimal> boughtItems;
    private BigDecimal total;
}
