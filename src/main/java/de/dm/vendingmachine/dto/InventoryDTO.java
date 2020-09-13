package de.dm.vendingmachine.dto;

import de.dm.vendingmachine.entity.Product;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public final class InventoryDTO {
    private final Product product;
    private final long amount;
}
