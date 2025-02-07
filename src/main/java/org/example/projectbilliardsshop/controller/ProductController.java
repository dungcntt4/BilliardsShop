package org.example.projectbilliardsshop.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.example.projectbilliardsshop.model.*;
import org.example.projectbilliardsshop.service.ProductReviewService;
import org.example.projectbilliardsshop.service.ProductService;
import org.example.projectbilliardsshop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class ProductController extends BaseController {
    @Autowired
    private ProductService productService;

    @Autowired
    private ProductReviewService productReviewService;

    @Autowired
    private UserService userService;

    @GetMapping(value = "/{category}")
    public ModelAndView showProduct(@PathVariable String category,@RequestParam(value = "priceRange", required = false) List<String> priceRanges) {
        Category category1 = categoryService.getCategoryByUrl(category);
        Brand brand = productService.getBrandByUrl(category);
        if(category1!=null) {
            modelAndView.addObject("category", category1);
            modelAndView.getModel().remove("brand");
            modelAndView.addObject("brand", null);
            List<Product> products;

            if (priceRanges != null && !priceRanges.isEmpty()) {
                products = productService.filterProductsByPriceRange(category1, priceRanges);
            } else {
                products = productService.findByCategory(category1);
            }
            modelAndView.addObject("products", products);


            if(category1.getParent()==null) {
                modelAndView.addObject("activeMenu", category);
            }else {
                modelAndView.addObject("activeMenu", category1.getParent().getUrl());
            }

            modelAndView.setViewName("viewuser/product");
            return modelAndView;
        }else if (brand!=null){
            List<Product> products = productService.findByBrand(brand);
            modelAndView.getModel().remove("category");
            modelAndView.addObject("category", null);
            modelAndView.addObject("activeMenu", category);
            modelAndView.addObject("products", products);
            modelAndView.addObject("brand", brand);
            modelAndView.setViewName("viewuser/product");
            return modelAndView;
        }else {
            modelAndView.setViewName("viewuser/home");
            return modelAndView;
        }

    }
    @GetMapping(value = "/productdetails/{product}")
    public ModelAndView showDetailsProduct(@PathVariable String product,@RequestParam(name="id") int id){
        Product product1 = productService.getProductByProductId(id);
        modelAndView.addObject("product", product1);
        modelAndView.addObject("brand", product1.getBrand());
        modelAndView.addObject("productReviews", productReviewService.getProductReviewsByProductId(product1));
        modelAndView.addObject("productService", productReviewService);
        modelAndView.addObject("averageRating", productReviewService.averageRating(product1));
        modelAndView.addObject("totalRating", productReviewService.totalRating(product1));
        modelAndView.addObject("ratingsDistribution", productReviewService.getRatingsDistribution(product1));
        modelAndView.setViewName("viewuser/productdetails");
        return modelAndView;
    }

    @PostMapping(value = "addReview")
    public String addReview(@ModelAttribute("productReview") ProductReview productReview, HttpServletRequest request, Model model) {
        String currentUrl = request.getHeader("referer");
        if (productReviewService.addProductReview(productReview)) {
            model.addAttribute("error", false);
            return "redirect:" + (currentUrl != null ? currentUrl : "/");
        } else {
            model.addAttribute("error", false);
            return "redirect:" + (currentUrl != null ? currentUrl : "/");
        }
    }

    @GetMapping("/search")
    @ResponseBody
    public List<Product> searchProducts(@RequestParam String keyword) {
        return productService.searchProducts(keyword);
    }


}
