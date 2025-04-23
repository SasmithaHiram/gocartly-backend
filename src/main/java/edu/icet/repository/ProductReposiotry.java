package edu.icet.repository;

import edu.icet.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductReposiotry extends JpaRepository<ProductEntity, Long> {
}
