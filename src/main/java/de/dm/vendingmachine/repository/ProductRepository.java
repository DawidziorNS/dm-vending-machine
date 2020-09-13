package de.dm.vendingmachine.repository;

import de.dm.vendingmachine.entity.Product;
import de.dm.vendingmachine.enums.ProductGroup;
import de.dm.vendingmachine.exceptions.CoreException;
import de.dm.vendingmachine.exceptions.ErrorCode;
import de.dm.vendingmachine.exceptions.ProductNotFoundException;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class ProductRepository {

    static List<Product> products = Arrays.asList(
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

    public List<Product> getAllProducts() {
        return products;
    }

    public List<Product> getProductsByProductGroup(ProductGroup productGroup) {
        return products.stream()
                .filter(x -> productGroup.equals(x.getProductGroup()))
                .collect(Collectors.toList());
    }

    public Product getProductById(long productId) {
        Optional<Product> product = products.stream()
                .filter(x -> productId == x.getId())
                .findFirst();

        if(product.isPresent()) {
            return product.get();
        }

        throw new ProductNotFoundException();
    }

}
