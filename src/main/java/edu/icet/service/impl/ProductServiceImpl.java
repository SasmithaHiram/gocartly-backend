package edu.icet.service.impl;

import edu.icet.dto.Product;
import edu.icet.entity.ProductEntity;
import edu.icet.repository.ProductReposiotry;
import edu.icet.service.ProductService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductServiceImpl implements ProductService {
    private final ProductReposiotry productReposiotry;
    private final ModelMapper modelMapper;
    private final RestTemplate restTemplate;

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
        Product[] response = restTemplate.getForObject("https://fakestoreapi.com/products", Product[].class);

        List<ProductEntity> productReposiotryAll = productReposiotry.findAll();

        List<Product> mergedproducts = new ArrayList<>();

        if (response!=null) {
            mergedproducts.addAll(Arrays.asList(response));
        }

        for (ProductEntity productEntity: productReposiotryAll) {
            Product mapped = modelMapper.map(productEntity, Product.class);
            mergedproducts.add(mapped);
        }
        return mergedproducts;
    }

}
