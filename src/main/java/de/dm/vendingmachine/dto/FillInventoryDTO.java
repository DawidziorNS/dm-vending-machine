package de.dm.vendingmachine.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class FillInventoryDTO {
    private Long productId;
    private Long amount;
}
