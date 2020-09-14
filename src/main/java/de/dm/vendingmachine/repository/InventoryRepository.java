package de.dm.vendingmachine.repository;

import de.dm.vendingmachine.entity.Inventory;
import de.dm.vendingmachine.entity.Product;
import de.dm.vendingmachine.exceptions.ProductNotFoundException;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class InventoryRepository {

    private List<Inventory> inventories = prepareInventory();

    public List<Inventory> getInventories() {
        return inventories;
    }

    public Inventory getInventoryByProductId(long productId) {
        Optional<Inventory> inventoryOpt = inventories.stream()
                .filter(inventory -> productId == inventory.getProduct().getId())
                .findFirst();

        if(inventoryOpt.isPresent()) {
            return inventoryOpt.get();
        }

        throw new ProductNotFoundException();

    }

    public List<Inventory> getInventoryByProductIds(List<Long> productIds) {
        return inventories.stream()
                .filter(inventory -> productIds.contains(inventory.getProduct().getId()))
                .collect(Collectors.toList());
    }

    public List<Inventory> fillInventory(long productId, long amount) {
        Inventory inventory = getInventoryByProductId(productId);

        long afterFillingAmount = inventory.getAmount() + amount;

        inventory.setAmount(afterFillingAmount);

        return inventories;
    }

    private List<Inventory> prepareInventory() {
        List<Product> allProducts = ProductRepository.PRODUCTS;
        return allProducts.stream()
                .map(product -> Inventory.builder()
                        .amount(10)
                        .product(product)
                        .build())
                .collect(Collectors.toList());
    }

}
