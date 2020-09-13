package de.dm.vendingmachine.controller;

import de.dm.vendingmachine.dto.ProductDTO;
import de.dm.vendingmachine.enums.ProductGroup;
import de.dm.vendingmachine.services.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("product")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class ProductController {

    private ProductService productService;

    @GetMapping("/{productId}")
    public ResponseEntity<ProductDTO> getProductById(@PathVariable long productId) {
        ProductDTO productById = productService.getProductById(productId);
        return ResponseEntity.ok(productById);
    }

    @GetMapping("/group/{productGroup}")
    public ResponseEntity<List<ProductDTO>> getProductByProductGroup(@PathVariable ProductGroup productGroup) {
        List<ProductDTO> productList = productService.getProductByProductGroup(productGroup);
        return ResponseEntity.ok(productList);
    }

    @GetMapping("/all")
    public ResponseEntity<List<ProductDTO>> getAllProduct() {
        List<ProductDTO> productList = productService.getAllProduct();
        return ResponseEntity.ok(productList);
    }
}
