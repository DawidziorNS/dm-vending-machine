package de.dm.vendingmachine.dto;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.List;

@Builder
@Getter
public class ReceiptDTO {
    private List<ReceiptItemDTO> boughtItems;
    private BigDecimal total;
}
