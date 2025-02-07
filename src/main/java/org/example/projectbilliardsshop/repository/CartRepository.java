package org.example.projectbilliardsshop.repository;

import org.example.projectbilliardsshop.model.Cart;
import org.example.projectbilliardsshop.model.Product;
import org.example.projectbilliardsshop.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartRepository extends JpaRepository<Cart, Integer> {
    List<Cart> findByUser(User user);

    Cart findByUserAndProduct(User user, Product product);

    Cart findByCartId(int cartId);

    void deleteCartByCartId(int cartId);


}
