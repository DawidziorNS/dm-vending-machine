package de.dm.vendingmachine.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public final class InventoryDTO {
    private final ProductDTO product;
    private final long amount;
}
