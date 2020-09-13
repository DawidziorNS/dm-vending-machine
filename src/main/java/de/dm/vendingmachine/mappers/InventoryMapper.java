package de.dm.vendingmachine.mappers;

import de.dm.vendingmachine.dto.InventoryDTO;
import de.dm.vendingmachine.entity.Inventory;
import org.springframework.stereotype.Service;

@Service
public class InventoryMapper {
    public InventoryDTO mapToInventoryDTO(Inventory inventory) {
        return InventoryDTO.builder()
                .product(inventory.getProduct())
                .amount(inventory.getAmount())
                .build();
    }
}
