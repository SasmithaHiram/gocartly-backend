package edu.icet.controller;

import edu.icet.dto.CartDto;
import edu.icet.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
@CrossOrigin
public class CartController {
    private final CartService cartService;
    
    @GetMapping("/user/{userId}")
    public ResponseEntity<CartDto> getCartByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(cartService.getCartByUserId(userId));
    }
    
    @PostMapping("/user/{userId}/items")
    public ResponseEntity<CartDto> addItemToCart(
            @PathVariable Long userId,
            @RequestBody Map<String, Object> request) {
        Long productId = Long.valueOf(request.get("productId").toString());
        Integer quantity = Integer.valueOf(request.get("quantity").toString());
        return ResponseEntity.ok(cartService.addItemToCart(userId, productId, quantity));
    }
    
    @PutMapping("/user/{userId}/items/{productId}")
    public ResponseEntity<CartDto> updateCartItemQuantity(
            @PathVariable Long userId,
            @PathVariable Long productId,
            @RequestBody Map<String, Integer> request) {
        Integer quantity = request.get("quantity");
        return ResponseEntity.ok(cartService.updateCartItemQuantity(userId, productId, quantity));
    }
    
    @DeleteMapping("/user/{userId}/items/{productId}")
    public ResponseEntity<Void> removeItemFromCart(
            @PathVariable Long userId,
            @PathVariable Long productId) {
        cartService.removeItemFromCart(userId, productId);
        return ResponseEntity.noContent().build();
    }
    
    @DeleteMapping("/user/{userId}")
    public ResponseEntity<Void> clearCart(@PathVariable Long userId) {
        cartService.clearCart(userId);
        return ResponseEntity.noContent().build();
    }
}
