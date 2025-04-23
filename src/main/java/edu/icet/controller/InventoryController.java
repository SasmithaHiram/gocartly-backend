package edu.icet.controller;

import edu.icet.dto.Inventory;
import edu.icet.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/inventory")
@RequiredArgsConstructor
@CrossOrigin
public class InventoryController {
    private final InventoryService inventoryService;

    @PostMapping("create")
    public ResponseEntity<String> create(@RequestBody Inventory inventory) {
        boolean isSaved = inventoryService.create(inventory);

        if (isSaved) {
            return ResponseEntity.status(HttpStatus.CREATED).body("PRODUCT SAVED SUCCESSFULLY");
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("FAILED TO SAVE THE PRODUCT");
    }

    @GetMapping("/searchById/{id}")
    public ResponseEntity<Inventory> searchById(@PathVariable Long id) {
        Inventory inventory = inventoryService.searchById(id);

        if (inventory != null) {
            return ResponseEntity.status(HttpStatus.FOUND).build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @PutMapping("/update")
    public ResponseEntity<String> update(@RequestBody Inventory inventory) {
        boolean isUpdated = inventoryService.update(inventory);

        if (isUpdated) {
            return ResponseEntity.status(HttpStatus.OK).body("PRODUCT UPDATED SUCCESSFULLY");
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("FAILED To UPDATE THE PRODUCT");
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable Long id) {
        Boolean isDeleted = inventoryService.delete(id);

        if (isDeleted) {
            return ResponseEntity.ok(true);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(false);
    }

    @GetMapping("get-all")
    public ResponseEntity<List<Inventory>> getAll() {
        List<Inventory> all = inventoryService.getAll();
        return ResponseEntity.ok(all);
    }

}
