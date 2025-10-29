package edu.icet.service;

import edu.icet.dto.ProductDto;
import edu.icet.entity.Category;
import edu.icet.entity.Product;
import edu.icet.exception.ResourceNotFoundException;
import edu.icet.repository.CategoryRepository;
import edu.icet.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;
    
    public List<ProductDto> getAllProducts() {
        return productRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
    
    public List<ProductDto> getActiveProducts() {
        return productRepository.findByActiveTrue().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
    
    public ProductDto getProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));
        return convertToDto(product);
    }
    
    public List<ProductDto> getProductsByCategory(Long categoryId) {
        return productRepository.findByCategory_Id(categoryId).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
    
    public List<ProductDto> searchProducts(String keyword) {
        return productRepository.findByNameContainingIgnoreCase(keyword).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
    
    public ProductDto createProduct(ProductDto productDto) {
        Product product = modelMapper.map(productDto, Product.class);
        
        if (productDto.getCategoryId() != null) {
            Category category = categoryRepository.findById(productDto.getCategoryId())
                    .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + productDto.getCategoryId()));
            product.setCategory(category);
        }
        
        Product savedProduct = productRepository.save(product);
        return convertToDto(savedProduct);
    }
    
    public ProductDto updateProduct(Long id, ProductDto productDto) {
        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));
        
        existingProduct.setName(productDto.getName());
        existingProduct.setDescription(productDto.getDescription());
        existingProduct.setPrice(productDto.getPrice());
        existingProduct.setStockQuantity(productDto.getStockQuantity());
        existingProduct.setImageUrl(productDto.getImageUrl());
        existingProduct.setBrand(productDto.getBrand());
        existingProduct.setActive(productDto.getActive());
        
        if (productDto.getCategoryId() != null) {
            Category category = categoryRepository.findById(productDto.getCategoryId())
                    .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + productDto.getCategoryId()));
            existingProduct.setCategory(category);
        }
        
        Product updatedProduct = productRepository.save(existingProduct);
        return convertToDto(updatedProduct);
    }
    
    public void deleteProduct(Long id) {
        if (!productRepository.existsById(id)) {
            throw new ResourceNotFoundException("Product not found with id: " + id);
        }
        productRepository.deleteById(id);
    }
    
    private ProductDto convertToDto(Product product) {
        ProductDto dto = modelMapper.map(product, ProductDto.class);
        if (product.getCategory() != null) {
            dto.setCategoryId(product.getCategory().getId());
            dto.setCategoryName(product.getCategory().getName());
        }
        return dto;
    }
}
