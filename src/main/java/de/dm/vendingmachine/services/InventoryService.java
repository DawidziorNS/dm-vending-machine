package de.dm.vendingmachine.services;

import de.dm.vendingmachine.dto.*;
import de.dm.vendingmachine.entity.Inventory;
import de.dm.vendingmachine.entity.Product;
import de.dm.vendingmachine.exceptions.CoreException;
import de.dm.vendingmachine.exceptions.ErrorCode;
import de.dm.vendingmachine.mappers.InventoryMapper;
import de.dm.vendingmachine.mappers.ProductMapper;
import de.dm.vendingmachine.repository.InventoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class InventoryService {

    private InventoryRepository inventoryRepository;
    private InventoryMapper inventoryMapper;
    private ProductMapper productMapper;

    public List<InventoryDTO> getInventory() {
        return inventoryRepository.getInventories().stream()
                .map(inventoryMapper::mapToInventoryDTO)
                .collect(Collectors.toList());
    }

    public InventoryDTO getInventoryByProductId(long productId) {
        Inventory inventoryByProductId = inventoryRepository.getInventoryByProductId(productId);
        return inventoryMapper.mapToInventoryDTO(inventoryByProductId);

    }

    public List<InventoryDTO> fillInventory(FillInventoryDTO fillInventoryDTO) {
        long amount = fillInventoryDTO.getAmount();
        long productId = fillInventoryDTO.getProductId();

        List<Inventory> inventory = inventoryRepository.fillInventory(productId, amount);

        return inventory.stream()
                .map(inventoryMapper::mapToInventoryDTO)
                .collect(Collectors.toList());
    }

    public ReceiptDTO vending(List<VendingDTO> vendingList) {
        Map<ProductDTO, BigDecimal> boughtItems = new HashMap<>();

        List<Long> productIds = vendingList.stream()
                .map(VendingDTO::getProductId)
                .collect(Collectors.toList());

        List<Inventory> inventory = inventoryRepository.getInventoryByProductIds(productIds);

        Map<Long, Long> mapProductIdAmountInInventory = inventory.stream()
                .collect(Collectors.toMap(x -> x.getProduct().getId(), Inventory::getAmount));

        Map<Long, Long> mapProductIdAmountFromVendingList = vendingList.stream()
                .collect(Collectors.toMap(VendingDTO::getProductId, VendingDTO::getAmount));

        mapProductIdAmountFromVendingList.forEach((key, value) -> {
            long productId = key;
            long amount = value;

            long inventoryAmount = mapProductIdAmountInInventory.get(productId);

            if (amount > inventoryAmount) {
                throw new CoreException(ErrorCode.OUT_OF_STOCK);
            }
        });

        mapProductIdAmountFromVendingList.forEach((productId, amount) -> {
            mapProductIdAmountInInventory.compute(productId, (key, value) -> value -= amount);
        });

        inventory.forEach(x -> {
            long productId = x.getProduct().getId();
            x.setAmount(mapProductIdAmountInInventory.get(productId));

            Product product = x.getProduct();
            BigDecimal price = product.getPrice();

            Long amount = mapProductIdAmountFromVendingList.get(productId);
            boughtItems.put(productMapper.mapToProductDTO(product), price.multiply(BigDecimal.valueOf(amount)));
        });

        BigDecimal total = boughtItems.values().stream()
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return ReceiptDTO.builder()
                .boughtItems(boughtItems)
                .total(total)
                .build();
    }
}
