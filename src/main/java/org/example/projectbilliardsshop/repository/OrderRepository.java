package org.example.projectbilliardsshop.repository;

import org.example.projectbilliardsshop.model.Order;
import org.example.projectbilliardsshop.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {
    List<Order> findByUser(User user);
}
