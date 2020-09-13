package de.dm.vendingmachine.controller;

import de.dm.vendingmachine.dto.InventoryFillingDTO;
import de.dm.vendingmachine.dto.InventoryDTO;
import de.dm.vendingmachine.dto.ReceiptDTO;
import de.dm.vendingmachine.dto.VendingDTO;
import de.dm.vendingmachine.services.InventoryService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("inventory")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class InventoryController {

    private InventoryService inventoryService;

    @GetMapping(path = "/all")
    public ResponseEntity<List<InventoryDTO>> getInventory() {
        return ResponseEntity.ok(inventoryService.getInventories());
    }

    @GetMapping(path = "/product/{productId}")
    public ResponseEntity<InventoryDTO> getInventoryByProductId(@PathVariable("productId") long productId) {
        return ResponseEntity.ok(inventoryService.getInventoryByProductId(productId));
    }

    @PutMapping(path = "/fill")
    public ResponseEntity<List<InventoryDTO>> fillInventory(@RequestBody InventoryFillingDTO inventoryFillingDTO) {
        return ResponseEntity.ok(inventoryService.fillInventory(inventoryFillingDTO));
    }

    @PutMapping(path = "/vending")
    public ResponseEntity<ReceiptDTO> vending(@RequestBody List<VendingDTO> shoppingItems) {
        return ResponseEntity.ok(inventoryService.vending(shoppingItems));
    }
}
