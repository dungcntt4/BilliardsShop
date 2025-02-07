package org.example.projectbilliardsshop.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.example.projectbilliardsshop.model.Cart;
import org.example.projectbilliardsshop.model.Product;
import org.example.projectbilliardsshop.model.User;
import org.example.projectbilliardsshop.service.CartService;
import org.example.projectbilliardsshop.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class CartController extends BaseController{
    @Autowired
    private CartService cartService;

    @Autowired
    private ProductService productService;

    @GetMapping(value = "cart")
    public ModelAndView showCart(HttpSession session) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser != null) {
            List<Cart> cart_items = cartService.getCartUser(loggedInUser);
            modelAndView.addObject("cart_items", cart_items);
            modelAndView.setViewName("viewuser/cart");
            return modelAndView;
        } else {
            modelAndView.addObject("error", true);
            modelAndView.setViewName("viewuser/login");
            return modelAndView;
        }
    }

    @PostMapping(value = "addToCart")
    public String addToCart(@ModelAttribute("cart") Cart cart, @RequestParam("action") String action, @RequestParam("product") Product product, HttpServletRequest request, Model model) {
        cart.setProduct(product);
        String currentUrl = request.getHeader("referer");
        if ("addToCart".equals(action)) {
            if(cartService.addToCart(cart)) {
                model.addAttribute("success", true);
            } else {
                model.addAttribute("error", true);
            };
            return "redirect:" + (currentUrl != null ? currentUrl : "/");
        } else if ("buyNow".equals(action)) {
            return "redirect:/cart";
        }
        return "redirect:" + (currentUrl != null ? currentUrl : "/");
    }
    @PostMapping(value = "deleteCartItems")
    public String deleteCartItems(@RequestParam("cartIds") String cartIds) {

        // Tách các ID và chuyển đổi chúng thành danh sách số nguyên
        List<Integer> cartIdList = Arrays.stream(cartIds.split(","))
                .map(Integer::parseInt)
                .collect(Collectors.toList());
        for (int i = 0; i < cartIdList.size(); i++) {
            cartService.deleteCartItemsByIds(cartIdList.get(i));
        }

        return "redirect:/cart";

    }

}
