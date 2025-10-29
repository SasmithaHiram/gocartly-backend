package edu.icet.repository;

import edu.icet.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUser_Id(Long userId);
    List<Order> findByStatus(Order.OrderStatus status);
}
