package de.dm.vendingmachine.mappers;

import de.dm.vendingmachine.dto.ProductDTO;
import de.dm.vendingmachine.entity.Product;
import org.springframework.stereotype.Service;

@Service
public class ProductMapper {
    public ProductDTO mapToProductDTO(Product product) {
        return ProductDTO.builder()
                .id(product.getId())
                .name(product.getName())
                .price(product.getPrice())
                .productGroup(product.getProductGroup())
                .build();
    }
}
