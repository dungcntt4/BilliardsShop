package org.example.projectbilliardsshop.repository;

import org.example.projectbilliardsshop.model.Order;
import org.example.projectbilliardsshop.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Integer> {
    List<OrderItem> findByOrder(Order order);
}
