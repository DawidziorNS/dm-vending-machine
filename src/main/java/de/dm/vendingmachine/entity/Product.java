package de.dm.vendingmachine.entity;

import de.dm.vendingmachine.enums.ProductGroup;
import lombok.*;

import java.math.BigDecimal;

@Builder
@Getter
@EqualsAndHashCode
public final class Product {
    private final long id;
    private final String name;
    private final BigDecimal price;
    private final ProductGroup productGroup;
}
