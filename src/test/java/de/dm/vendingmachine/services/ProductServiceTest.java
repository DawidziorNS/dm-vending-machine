package de.dm.vendingmachine.services;

import de.dm.vendingmachine.dto.ProductDTO;
import de.dm.vendingmachine.entity.Product;
import de.dm.vendingmachine.enums.ProductGroup;
import de.dm.vendingmachine.exceptions.ProductNotFoundException;
import de.dm.vendingmachine.mappers.ProductMapper;
import de.dm.vendingmachine.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

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

    @InjectMocks
    private ProductService productService;

    @Mock
    private ProductRepository productRepository;

    @Spy
    private ProductMapper productMapper;

    @Test
    void productMapperShouldReturnAllProductsAsProductDTOs() {
        // given
        // when
        when(productRepository.getAllProducts()).thenReturn(PRODUCTS);

        List<ProductDTO> allProduct = productService.getAllProduct();

        // then
        assertThat(allProduct.size()).isEqualTo(8);
    }

    @Test
    void notFoundExceptionShouldBeThrownBecauseProductDoesntExists() {
        // given
        long productId = 999;

        // when
        when(productRepository.getProductById(productId)).thenReturn(Optional.empty());

        // then
        assertThrows(ProductNotFoundException.class, () -> productService.getProductById(productId));
    }

    @Test
    void getProductByIdShouldReturnProductDTOWithCorrectId() {
        // given
        long productId = 1;
        Optional<Product> productById = PRODUCTS.stream()
                .filter(product -> productId == product.getId())
                .findFirst();

        // when
        when(productRepository.getProductById(productId)).thenReturn(productById);
        ProductDTO productDTO = productService.getProductById(productId);

        // then
        assertThat(productDTO.getId()).isEqualTo(productId);

    }

    @Test
    void getProductByGroupShouldReturnListWithTwoElements() {
        // given
        ProductGroup productGroup = ProductGroup.HOUSEHOLD;
        List<Product> productsByGroup = PRODUCTS.stream()
                .filter(product -> productGroup.equals(product.getProductGroup()))
                .collect(Collectors.toList());

        // when
        when(productRepository.getProductsByProductGroup(productGroup)).thenReturn(productsByGroup);
        List<ProductDTO> productDTOs = productService.getProductByProductGroup(productGroup);

        // then
        assertThat(productDTOs.size()).isEqualTo(2);

    }

}