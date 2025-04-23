package edu.icet.controller;

import edu.icet.dto.Product;
import edu.icet.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
@CrossOrigin
public class ProductController {
    private final ProductService productService;

    @PostMapping("create")
    public ResponseEntity<String> create(@RequestBody Product product) {
        boolean isSaved = productService.create(product);

        if (isSaved) {
            return ResponseEntity.status(HttpStatus.CREATED).body("PRODUCT SAVED SUCCESSFULLY");
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("FAILED TO SAVE THE PRODUCT");
    }

    @GetMapping("/searchById/{id}")
    public ResponseEntity<Product> searchById(@PathVariable Long id) {
        Product product = productService.searchById(id);

        if (product != null) {
            return ResponseEntity.status(HttpStatus.FOUND).build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @PutMapping("/update")
    public ResponseEntity<String> update(@RequestBody Product product) {
        boolean isUpdated = productService.update(product);

        if (isUpdated) {
            return ResponseEntity.status(HttpStatus.OK).body("PRODUCT UPDATED SUCCESSFULLY");
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("FAILED To UPDATE THE PRODUCT");
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable Long id) {
        Boolean isDeleted = productService.delete(id);

        if (isDeleted) {
            return ResponseEntity.ok(true);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(false);
    }

    @GetMapping("get-all")
    public ResponseEntity<List<Product>> getAll() {
        List<Product> all = productService.getAll();
        return ResponseEntity.ok(all);
    }

}
