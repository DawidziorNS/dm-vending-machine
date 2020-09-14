package de.dm.vendingmachine.services;

import de.dm.vendingmachine.dto.*;
import de.dm.vendingmachine.entity.Inventory;
import de.dm.vendingmachine.entity.Product;
import de.dm.vendingmachine.exceptions.OutOfStockException;
import de.dm.vendingmachine.mappers.InventoryMapper;
import de.dm.vendingmachine.mappers.ProductMapper;
import de.dm.vendingmachine.repository.InventoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class InventoryService {

    private InventoryRepository inventoryRepository;
    private InventoryMapper inventoryMapper;
    private ProductMapper productMapper;

    public List<InventoryDTO> getInventories() {
        return inventoryRepository.getInventories().stream()
                .map(inventoryMapper::mapToInventoryDTO)
                .collect(Collectors.toList());
    }

    public InventoryDTO getInventoryByProductId(long productId) {
        Inventory inventoryByProductId = inventoryRepository.getInventoryByProductId(productId);
        return inventoryMapper.mapToInventoryDTO(inventoryByProductId);

    }

    public List<InventoryDTO> fillInventory(InventoryFillingDTO inventoryFillingDTO) {
        long amount = inventoryFillingDTO.getAmount();
        long productId = inventoryFillingDTO.getProductId();

        List<Inventory> inventories = inventoryRepository.fillInventory(productId, amount);

        return inventories.stream()
                .map(inventoryMapper::mapToInventoryDTO)
                .collect(Collectors.toList());
    }

    public ReceiptDTO vending(VendingDTO vendingDTO) {
        List<ReceiptItemDTO> boughtItems = new LinkedList<>();

        List<VendingItemDTO> vendingItems = vendingDTO.getVendingItems();

        List<Long> productIds = vendingItems.stream()
                .map(VendingItemDTO::getProductId)
                .collect(Collectors.toList());

        List<Inventory> inventories = inventoryRepository.getInventoryByProductIds(productIds);

        Map<Long, Long> productIdsByAmountFromInventory = inventories.stream()
                .collect(Collectors.toMap(x -> x.getProduct().getId(), Inventory::getAmount));

        Map<Long, Long> productIdsByAmountFromVendingList = vendingItems.stream()
                .collect(Collectors.toMap(VendingItemDTO::getProductId, VendingItemDTO::getAmount));

        productIdsByAmountFromVendingList.forEach((key, value) -> {
            long productId = key;
            long amount = value;

            long inventoryAmount = productIdsByAmountFromInventory.get(productId);

            if (amount > inventoryAmount) {
                throw new OutOfStockException();
            }
        });

        productIdsByAmountFromVendingList.forEach((productId, amount) -> {
            productIdsByAmountFromInventory.compute(productId, (key, value) -> value -= amount);
        });

        inventories.forEach(inventory -> {
            long productId = inventory.getProduct().getId();
            inventory.setAmount(productIdsByAmountFromInventory.get(productId));

            Product product = inventory.getProduct();
            BigDecimal price = product.getPrice();

            Long amount = productIdsByAmountFromVendingList.get(productId);

            ReceiptItemDTO receiptItemDTO = ReceiptItemDTO.builder()
                    .boughtItem(productMapper.mapToProductDTO(product))
                    .amount(amount)
                    .cost(price.multiply(BigDecimal.valueOf(amount)))
                    .build();

            boughtItems.add(receiptItemDTO);

        });

        BigDecimal total = boughtItems.stream()
                .map(ReceiptItemDTO::getCost)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return ReceiptDTO.builder()
                .boughtItems(boughtItems)
                .total(total)
                .build();
    }

}
