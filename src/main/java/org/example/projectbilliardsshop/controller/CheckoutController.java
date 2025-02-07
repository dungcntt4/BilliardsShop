package org.example.projectbilliardsshop.controller;

import jakarta.servlet.http.HttpSession;
import org.example.projectbilliardsshop.model.Cart;
import org.example.projectbilliardsshop.model.Order;
import org.example.projectbilliardsshop.model.OrderItem;
import org.example.projectbilliardsshop.model.User;
import org.example.projectbilliardsshop.service.CartService;
import org.example.projectbilliardsshop.service.CheckoutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.math.BigDecimal;
import java.util.List;

@Controller
public class CheckoutController extends BaseController {
    @Autowired
    private CartService cartService;
    @Autowired
    private CheckoutService checkoutService;

    @PostMapping("/checkout")
    public ModelAndView checkout(@RequestParam("selectedCartItems") List<Integer> selectedCartItems) {
        if (selectedCartItems.size() == 0) {
            modelAndView.addObject("error", "No products selected for checkout.");
            modelAndView.setViewName("viewuser/cart");  // Chuyển hướng về trang giỏ hàng nếu không có sản phẩm nào được chọn
            return modelAndView;
        }else{
            List <Cart> carts = cartService.getCartByCartIds(selectedCartItems);
            BigDecimal totalAmount = carts.stream()
                    .map(cartItem -> cartItem.getProduct().getPrice().multiply(BigDecimal.valueOf(cartItem.getQuantity())))
                    .reduce(BigDecimal.ZERO, BigDecimal::add);  // Cộng tổng các giá trị

            modelAndView.addObject("carts", carts);
            modelAndView.addObject("totalAmount", totalAmount);
            modelAndView.addObject("activeMenu", "checkout");
            modelAndView.setViewName("viewuser/checkout");
            return modelAndView;
        }

    }

    @PostMapping("proceedtopayment")
    public ModelAndView confirmOrder(@RequestParam("cartIds") List<Integer> cartIds, @RequestParam("totalAmount") BigDecimal totalAmount, HttpSession session) {
        User user = (User) session.getAttribute("loggedInUser");
        List <Cart> carts = cartService.getCartByCartIds(cartIds);
        Order newOrder = new Order();
        newOrder.setUser(user);
        newOrder.setTotalAmount(totalAmount);
        Order savedOrder =checkoutService.saveOrder(newOrder);

        for (Cart cartItem : carts) {
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(savedOrder);
            orderItem.setProduct(cartItem.getProduct());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setPrice(cartItem.getProduct().getPrice());
            checkoutService.saveOrderItem(orderItem);
            cartService.deleteCartItemsByIds(cartItem.getCartId());
        }

        modelAndView.addObject("success", "Order placed successfully.");
        modelAndView.setViewName("viewuser/profile");
        return modelAndView;

    }
    
}
