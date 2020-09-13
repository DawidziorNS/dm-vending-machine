package de.dm.vendingmachine.services;

import de.dm.vendingmachine.entity.Product;
import de.dm.vendingmachine.dto.ProductDTO;
import de.dm.vendingmachine.enums.ProductGroup;
import de.dm.vendingmachine.mappers.ProductMapper;
import de.dm.vendingmachine.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class ProductService {

    private ProductRepository productRepository;
    private ProductMapper productMapper;

    public List<ProductDTO> getAllProduct() {
        List<Product> allProducts = productRepository.getAllProducts();
        return allProducts.stream()
                .map(productMapper::mapToProductDTO)
                .collect(Collectors.toList());
    }

    public ProductDTO getProductById(long productId) {
        Product productById = productRepository.getProductById(productId);
        return productMapper.mapToProductDTO(productById);

    }

    public List<ProductDTO> getProductByProductGroup(ProductGroup productGroup) {
        List<Product> productByProductGroup = productRepository.getProductsByProductGroup(productGroup);
        return productByProductGroup.stream()
                .map(productMapper::mapToProductDTO)
                .collect(Collectors.toList());
    }

}
