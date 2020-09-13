package de.dm.vendingmachine.mappers;

import de.dm.vendingmachine.dto.InventoryDTO;
import de.dm.vendingmachine.entity.Inventory;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class InventoryMapper {

    private ProductMapper productMapper;

    public InventoryDTO mapToInventoryDTO(Inventory inventory) {
        return InventoryDTO.builder()
                .product(productMapper.mapToProductDTO(inventory.getProduct()))
                .amount(inventory.getAmount())
                .build();
    }
}
