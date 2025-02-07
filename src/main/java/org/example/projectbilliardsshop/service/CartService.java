package org.example.projectbilliardsshop.service;

import org.example.projectbilliardsshop.model.Cart;
import org.example.projectbilliardsshop.model.Product;
import org.example.projectbilliardsshop.model.User;
import org.example.projectbilliardsshop.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CartService {
    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private ProductService productService;

    public List<Cart> getCartUser(User user) {
        return cartRepository.findByUser(user);
    }

    public Boolean addToCart(Cart cart) {
        User user = userService.findById(cart.getUser().getId());
        Product product = productService.getProductByProductId(cart.getProduct().getProductId());

        System.out.println("🛒 Adding to cart - User ID: " + user.getId() + ", Product ID: " + product.getProductId());

        // Kiểm tra xem sản phẩm đã tồn tại trong giỏ hàng chưa
        Cart existingCart = cartRepository.findByUserAndProduct(user, product);

        if (existingCart != null) {
            System.out.println("⚠ Sản phẩm đã có trong giỏ hàng, cập nhật số lượng...");
            existingCart.setQuantity(existingCart.getQuantity() + cart.getQuantity());
            cartRepository.save(existingCart);
        } else {
            System.out.println("✅ Thêm sản phẩm mới vào giỏ hàng...");
            cart.setUser(user);
            cart.setProduct(product);
            cartRepository.save(cart);
        }

        return true;
    }

    public List<Cart> getCartByCartIds(List<Integer> cartIds) {
        List<Cart> carts = new ArrayList<>();
        for (int cartId : cartIds) {
            Cart cart = cartRepository.findByCartId(cartId);
            carts.add(cart);
        }
        return carts;
    }

    @Transactional
    public void deleteCartItemsByIds(int cartId) {
        cartRepository.deleteCartByCartId(cartId);
    }

    public void updateQuantity(int cartItemId, int change) {
        Cart cart = cartRepository.findByCartId(cartItemId);
        cart.setQuantity(change);
        cartRepository.save(cart);
    }
}
