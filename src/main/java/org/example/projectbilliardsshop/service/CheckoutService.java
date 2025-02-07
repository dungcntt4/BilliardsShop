package org.example.projectbilliardsshop.service;

import org.example.projectbilliardsshop.model.Order;
import org.example.projectbilliardsshop.model.OrderItem;
import org.example.projectbilliardsshop.model.User;
import org.example.projectbilliardsshop.repository.OrderItemRepository;
import org.example.projectbilliardsshop.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CheckoutService {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    public Order saveOrder(Order order) {
        order.setOrderStatus("Chờ xác nhận"); // Trạng thái mặc định
        return orderRepository.save(order);
    }
    public List<Order> getOrdersByUser(User user) {
        return orderRepository.findByUser(user);
    }

    public Order getOrderById(int orderId) {
        return orderRepository.findById(orderId).orElse(null);
    }

    public OrderItem saveOrderItem(OrderItem orderItem) {
        return orderItemRepository.save(orderItem);
    }

    public List<OrderItem> getOrderItemsByOrder(Order order) {
        return orderItemRepository.findByOrder(order);
    }

}
