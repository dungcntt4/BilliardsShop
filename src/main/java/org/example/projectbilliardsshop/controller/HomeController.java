package org.example.projectbilliardsshop.controller;

import org.example.projectbilliardsshop.model.Category;
import org.example.projectbilliardsshop.model.Product;
import org.example.projectbilliardsshop.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class HomeController extends BaseController {

    @Autowired
    private ProductService productService;

    public String index() {
        return "index";
    }

    @GetMapping("/home")
    public ModelAndView home() {
        List<Product> poolTables = productService.findByCategory(categoryService.getCategoryByUrl("pools-table"));
        List<Product> poolTable = poolTables.stream().limit(5).toList();
        List<Product> cueSticks = productService.findByCategory(categoryService.getCategoryByUrl("cue-stick"));
        List<Product> cueStick = cueSticks.stream().limit(5).toList();
        List<Product> bAs = productService.findByCategory(categoryService.getCategoryByUrl("billiard-accessories"));
        List<Product> bA = bAs.stream().limit(5).toList();
        modelAndView.addObject("poolTables", poolTable);
        modelAndView.addObject("cueSticks", cueStick);
        modelAndView.addObject("bAs", bA);
        modelAndView.addObject("activeMenu", "home");
        modelAndView.setViewName("viewuser/home");
        return modelAndView;
    }

    @GetMapping("/about")
    public ModelAndView about() {
        modelAndView.addObject("activeMenu", "about");
        modelAndView.setViewName("viewuser/news");
        return modelAndView;
    }

    @GetMapping(value = "news")
    public ModelAndView news() {
        modelAndView.addObject("activeMenu", "news");
        modelAndView.setViewName("viewuser/news");
        return modelAndView;
    }

    @GetMapping(value = "contact")
    public ModelAndView contact() {
        modelAndView.addObject("activeMenu", "contact");
        modelAndView.setViewName("viewuser/news");
        return modelAndView;
    }

}
