package edu.icet.service;

import edu.icet.dto.Product;

import java.util.List;

public interface ProductService {
    boolean create(Product product);
    Product searchById(Long id);
    boolean update(Product product);
    Boolean delete(Long id);
    List<Product> getAll();
}
