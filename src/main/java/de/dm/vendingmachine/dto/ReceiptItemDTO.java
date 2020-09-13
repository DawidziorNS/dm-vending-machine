package de.dm.vendingmachine.dto;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Builder
@Getter
public class ReceiptItemDTO {
    private ProductDTO boughtItem;
    private long amount;
    private BigDecimal cost;
}
