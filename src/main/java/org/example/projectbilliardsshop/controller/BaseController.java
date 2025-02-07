package org.example.projectbilliardsshop.controller;

import jakarta.annotation.PostConstruct;
import org.example.projectbilliardsshop.model.Category;
import org.example.projectbilliardsshop.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@Controller
public class BaseController {
    ModelAndView modelAndView = new ModelAndView();
    @Autowired
    CategoryService categoryService;

    @PostConstruct
    public ModelAndView getModelAndView() {
        List<Category> parentCategories = categoryService.getAllParentCategories();
        modelAndView.addObject("categories", parentCategories);
        return modelAndView;
    }
}
