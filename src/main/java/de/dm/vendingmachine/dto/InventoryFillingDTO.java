package de.dm.vendingmachine.dto;

import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Builder
@Getter
public class InventoryFillingDTO {
    @NotNull
    private Long productId;

    @NotNull
    @Min(value = 1L, message = "The value must be positive")
    private Long amount;
}
