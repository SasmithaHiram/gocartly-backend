package edu.icet.controller;

import edu.icet.dto.OrderDto;
import edu.icet.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
@CrossOrigin
public class OrderController {
    private final OrderService orderService;
    
    @PostMapping("/user/{userId}")
    public ResponseEntity<OrderDto> createOrder(
            @PathVariable Long userId,
            @RequestBody OrderDto orderDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(orderService.createOrder(userId, orderDto));
    }
    
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<OrderDto>> getOrdersByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(orderService.getOrdersByUserId(userId));
    }
    
    @GetMapping("/{orderId}")
    public ResponseEntity<OrderDto> getOrderById(@PathVariable Long orderId) {
        return ResponseEntity.ok(orderService.getOrderById(orderId));
    }
    
    @GetMapping
    public ResponseEntity<List<OrderDto>> getAllOrders() {
        return ResponseEntity.ok(orderService.getAllOrders());
    }
    
    @PatchMapping("/{orderId}/status")
    public ResponseEntity<OrderDto> updateOrderStatus(
            @PathVariable Long orderId,
            @RequestBody Map<String, String> request) {
        String status = request.get("status");
        return ResponseEntity.ok(orderService.updateOrderStatus(orderId, status));
    }
}
