package edu.icet.service.impl;

import edu.icet.dto.Product;
import edu.icet.entity.ProductEntity;
import edu.icet.repository.ProductReposiotry;
import edu.icet.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductReposiotry productReposiotry;
    private final ModelMapper modelMapper;

    @Override
    public boolean create(Product product) {
        if (product != null) {
            productReposiotry.save(modelMapper.map(product, ProductEntity.class));
            return true;
        }
        return false;
    }

    @Override
    public Product searchById(Long id) {
        if (id != null) {
            modelMapper.map(productReposiotry.findById(id), Product.class);
        }
        return null;
    }

    @Override
    public boolean update(Product product) {
        if (product != null) {
            this.create(product);
            return true;
        }
        return false;
    }

    @Override
    public Boolean delete(Long id) {
        if (id != null) {
            productReposiotry.deleteById(id);

        }
        return null;
    }

    @Override
    public List<Product> getAll() {
        return productReposiotry.findAll().stream().map(productEntity ->
                modelMapper.map(productEntity, Product.class)).toList();
    }
    
}
