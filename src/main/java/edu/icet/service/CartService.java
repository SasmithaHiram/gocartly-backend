package edu.icet.service;

import edu.icet.dto.CartDto;
import edu.icet.dto.CartItemDto;
import edu.icet.entity.Cart;
import edu.icet.entity.CartItem;
import edu.icet.entity.Product;
import edu.icet.entity.User;
import edu.icet.exception.BadRequestException;
import edu.icet.exception.ResourceNotFoundException;
import edu.icet.repository.CartItemRepository;
import edu.icet.repository.CartRepository;
import edu.icet.repository.ProductRepository;
import edu.icet.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CartService {
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    
    public CartDto getCartByUserId(Long userId) {
        Cart cart = cartRepository.findByUser_Id(userId)
                .orElseGet(() -> createCartForUser(userId));
        return convertToDto(cart);
    }
    
    private Cart createCartForUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));
        
        Cart cart = new Cart();
        cart.setUser(user);
        return cartRepository.save(cart);
    }
    
    @Transactional
    public CartDto addItemToCart(Long userId, Long productId, Integer quantity) {
        if (quantity <= 0) {
            throw new BadRequestException("Quantity must be greater than 0");
        }
        
        Cart cart = cartRepository.findByUser_Id(userId)
                .orElseGet(() -> createCartForUser(userId));
        
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + productId));
        
        if (product.getStockQuantity() < quantity) {
            throw new BadRequestException("Insufficient stock for product: " + product.getName());
        }
        
        CartItem existingItem = cartItemRepository.findByCart_IdAndProduct_Id(cart.getId(), productId)
                .orElse(null);
        
        if (existingItem != null) {
            existingItem.setQuantity(existingItem.getQuantity() + quantity);
            cartItemRepository.save(existingItem);
        } else {
            CartItem newItem = new CartItem();
            newItem.setCart(cart);
            newItem.setProduct(product);
            newItem.setQuantity(quantity);
            newItem.setPrice(product.getPrice());
            cartItemRepository.save(newItem);
        }
        
        cart = cartRepository.findById(cart.getId()).get();
        return convertToDto(cart);
    }
    
    @Transactional
    public CartDto updateCartItemQuantity(Long userId, Long productId, Integer quantity) {
        Cart cart = cartRepository.findByUser_Id(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found for user id: " + userId));
        
        CartItem cartItem = cartItemRepository.findByCart_IdAndProduct_Id(cart.getId(), productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found in cart"));
        
        if (quantity <= 0) {
            cartItemRepository.delete(cartItem);
        } else {
            Product product = cartItem.getProduct();
            if (product.getStockQuantity() < quantity) {
                throw new BadRequestException("Insufficient stock for product: " + product.getName());
            }
            cartItem.setQuantity(quantity);
            cartItemRepository.save(cartItem);
        }
        
        cart = cartRepository.findById(cart.getId()).get();
        return convertToDto(cart);
    }
    
    @Transactional
    public void removeItemFromCart(Long userId, Long productId) {
        Cart cart = cartRepository.findByUser_Id(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found for user id: " + userId));
        
        CartItem cartItem = cartItemRepository.findByCart_IdAndProduct_Id(cart.getId(), productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found in cart"));
        
        cartItemRepository.delete(cartItem);
    }
    
    @Transactional
    public void clearCart(Long userId) {
        Cart cart = cartRepository.findByUser_Id(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found for user id: " + userId));
        
        cart.getItems().clear();
        cartRepository.save(cart);
    }
    
    private CartDto convertToDto(Cart cart) {
        CartDto dto = new CartDto();
        dto.setId(cart.getId());
        dto.setUserId(cart.getUser().getId());
        
        List<CartItemDto> itemDtos = cart.getItems().stream()
                .map(this::convertItemToDto)
                .collect(Collectors.toList());
        dto.setItems(itemDtos);
        
        BigDecimal totalAmount = itemDtos.stream()
                .map(CartItemDto::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        dto.setTotalAmount(totalAmount);
        
        return dto;
    }
    
    private CartItemDto convertItemToDto(CartItem item) {
        CartItemDto dto = new CartItemDto();
        dto.setId(item.getId());
        dto.setProductId(item.getProduct().getId());
        dto.setProductName(item.getProduct().getName());
        dto.setQuantity(item.getQuantity());
        dto.setPrice(item.getPrice());
        dto.setSubtotal(item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())));
        return dto;
    }
}
