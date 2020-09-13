package de.dm.vendingmachine.dto;

import de.dm.vendingmachine.enums.ProductGroup;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Builder
@Getter
public final class ProductDTO {
    private final long id;
    private final String name;
    private final BigDecimal price;
    private final ProductGroup productGroup;
}
