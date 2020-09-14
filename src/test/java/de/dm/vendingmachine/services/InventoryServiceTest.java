package de.dm.vendingmachine.services;

import de.dm.vendingmachine.dto.*;
import de.dm.vendingmachine.entity.Inventory;
import de.dm.vendingmachine.entity.Product;
import de.dm.vendingmachine.enums.ProductGroup;
import de.dm.vendingmachine.mappers.InventoryMapper;
import de.dm.vendingmachine.mappers.ProductMapper;
import de.dm.vendingmachine.repository.InventoryRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class InventoryServiceTest {

    private static final List<Product> PRODUCTS = Arrays.asList(
            Product.builder()
                    .id(1)
                    .name("Balea shower gel")
                    .price(BigDecimal.valueOf(0.55))
                    .productGroup(ProductGroup.PERSONAL_HYGIENE)
                    .build(),
            Product.builder()
                    .id(2)
                    .name("SEINZ beard oil")
                    .price(BigDecimal.valueOf(7.95))
                    .productGroup(ProductGroup.FACE)
                    .build(),
            Product.builder()
                    .id(3)
                    .name("Balea body lotion")
                    .price(BigDecimal.valueOf(0.75))
                    .productGroup(ProductGroup.PERSONAL_HYGIENE)
                    .build(),
            Product.builder()
                    .id(4)
                    .name("Fotoparadies USB stick 16GB")
                    .price(BigDecimal.valueOf(6.95))
                    .productGroup(ProductGroup.PHOTO)
                    .build(),
            Product.builder()
                    .id(5)
                    .name("Nivea night cream Q10")
                    .price(BigDecimal.valueOf(15.95))
                    .productGroup(ProductGroup.FACE)
                    .build(),
            Product.builder()
                    .id(6)
                    .name("Kinderhose (one-size)")
                    .price(BigDecimal.valueOf(9.90))
                    .productGroup(ProductGroup.TEXTILE)
                    .build(),
            Product.builder()
                    .id(7)
                    .name("All-purpose cleaner Power degreaser")
                    .price(BigDecimal.valueOf(1.75))
                    .productGroup(ProductGroup.HOUSEHOLD)
                    .build(),
            Product.builder()
                    .id(8)
                    .name("Profissimo Dirt eraser")
                    .price(BigDecimal.valueOf(2.25))
                    .productGroup(ProductGroup.HOUSEHOLD)
                    .build()
    );

    @BeforeAll
    static void setup() {
        ProductMapper productMapper = new ProductMapper();
        inventoryMapper = new InventoryMapper(productMapper);
        inventoryRepository = mock(InventoryRepository.class);
        inventoryService = new InventoryService(inventoryRepository, inventoryMapper, productMapper);
    }

    @BeforeEach
    void setupEachTest() {
        INVENTORY = prepareInventory();
    }

    private static List<Inventory> INVENTORY;

    private static InventoryService inventoryService;
    private static InventoryRepository inventoryRepository;
    private static InventoryMapper inventoryMapper;

    private static List<Inventory> prepareInventory() {
        return PRODUCTS.stream()
                .map(product -> Inventory.builder()
                        .amount(10)
                        .product(product)
                        .build())
                .collect(Collectors.toList());
    }

    private static List<Inventory> returnInventoryAfterFilling(long productId, long amount) {
         INVENTORY.stream()
                .filter(inventory -> productId == inventory.getProduct().getId())
                .forEach(x -> x.setAmount(x.getAmount() + amount));

         return INVENTORY;
    }

    @Test
    void inventoryShouldHaveTenItemsEachProducts() {
        // given
        // when
        when(inventoryRepository.getInventories()).thenReturn(INVENTORY);
        List<InventoryDTO> inventories = inventoryService.getInventories();

        // then
        assertAll(
                () -> assertThat(inventories.size()).isEqualTo(8),
                () -> assertTrue(inventories.stream()
                    .allMatch(inventory -> 10 == inventory.getAmount()))
        );
    }

    @Test
    void inventoryShouldHaveIncreasedNumberOfProductWithIdOne() {
        // given
        long productId = 1;
        long amountToAdd = 1;

        long amountBeforeIncreasing = INVENTORY.stream()
                .filter(inventory -> productId == inventory.getProduct().getId())
                .mapToLong(Inventory::getAmount)
                .findFirst()
                .getAsLong();

        InventoryFillingDTO inventoryFillingDTO = InventoryFillingDTO.builder()
                .amount(amountToAdd)
                .productId(productId)
                .build();

        // when
        when(inventoryRepository.fillInventory(productId, amountToAdd)).thenReturn(returnInventoryAfterFilling(productId, amountToAdd));
        List<InventoryDTO> inventoryDTOS = inventoryService.fillInventory(inventoryFillingDTO);

        // then
        assertAll(
                () -> assertThat(inventoryDTOS.size()).isEqualTo(8),
                () -> assertThat(inventoryDTOS.stream()
                        .anyMatch(inventory -> (productId == inventory.getProduct().getId()) && (inventory.getAmount() == amountBeforeIncreasing + amountToAdd)))
        );
    }

    @Test
    void fillInventoryShouldThrowNullPointerException() {
        // given
        // when
        // then
        assertThrows(NullPointerException.class, () -> inventoryService.fillInventory(null));
    }

    @Test
    void vendingShouldReturnCorrectReceiptDTO() {
        // given
        List<Long> productIds = Arrays.asList(1L, 2L);

        List<Inventory> inventoriesByProductIds = INVENTORY.stream()
                .filter(inventory -> productIds.contains(inventory.getProduct().getId()))
                .collect(Collectors.toList());

        List<VendingItemDTO> vendingItems = Arrays.asList(
                VendingItemDTO.builder()
                        .amount(2L)
                        .productId(1L)
                    .build(),
                VendingItemDTO.builder()
                        .amount(3L)
                        .productId(2L)
                        .build()
        );

        VendingDTO vendingDTO = VendingDTO.builder()
                .vendingItems(vendingItems)
                .build();

        // when
        when(inventoryRepository.getInventoryByProductIds(productIds)).thenReturn(inventoriesByProductIds);
        ReceiptDTO receiptDTO = inventoryService.vending(vendingDTO);

        // then
        assertAll(
                () -> assertThat(receiptDTO.getTotal()).isEqualTo(BigDecimal.valueOf(24.95)),
                () -> assertThat(receiptDTO.getBoughtItems().size()).isEqualTo(2)
        );
    }

}