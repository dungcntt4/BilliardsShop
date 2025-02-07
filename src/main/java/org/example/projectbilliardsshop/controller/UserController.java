package org.example.projectbilliardsshop.controller;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.example.projectbilliardsshop.model.Order;
import org.example.projectbilliardsshop.model.User;
import org.example.projectbilliardsshop.service.CheckoutService;
import org.example.projectbilliardsshop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;


import java.util.List;
import java.util.Optional;


@Controller
public class UserController extends BaseController {
    @Autowired
    private UserService userService ;

    @Autowired
    private CheckoutService checkoutService;

    @GetMapping(value = "signup")
    public String showSignup(Model model) {
        model.addAttribute("user", new User());
        return "viewuser/signup";
    }

    @PostMapping(value = "userSignUp")
    public String signup(@Validated @ModelAttribute("user") User user, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "viewuser/signup"; // Trả về trang signup nếu có lỗi
        }

        try {
            Optional<User> userOptional = userService.findByEmail(user);
            if (userOptional.isPresent()) {
                model.addAttribute("message", "Email đã tồn tại");
                model.addAttribute("user", user);
                return "viewuser/signup";
            }

            if (userService.signUp(user)) {
                model.addAttribute("error1", false);
                model.addAttribute("user", user);
                return "viewuser/login";
            } else {
                model.addAttribute("error", true);
                return "redirect:viewuser/signup";
            }

        } catch (ConstraintViolationException e) {
            for (ConstraintViolation<?> violation : e.getConstraintViolations()) {
                String propertyPath = violation.getPropertyPath().toString();
                String message = violation.getMessage();
                result.rejectValue(propertyPath, "", message);
            }
            return "viewuser/signup"; // Trả về trang signup nếu có lỗi ràng buộc
        } catch (Exception e) {
            model.addAttribute("message", "Đã xảy ra lỗi trong quá trình đăng ký. Vui lòng thử lại sau.");
            return "viewuser/signup"; // Trả về trang signup nếu có lỗi khác
        }
    }
    @GetMapping("/login")
    public String showLogin(Model model) {
        model.addAttribute("user",new User());
        return "viewuser/login"; // trả về file login.html
    }

    @PostMapping(value = "gohome")
    public ModelAndView goHome(@ModelAttribute("user") User user, HttpSession session) {
        User loggedInUser = userService.login(user);
        if (loggedInUser != null) {
            session.setAttribute("loggedInUser", loggedInUser);
            modelAndView.setViewName("viewuser/home");
            return new ModelAndView("redirect:/home?success=true");
        } else {
            modelAndView.addObject("error", true);
            modelAndView.setViewName("viewuser/login");
            return modelAndView;
        }
    }

    @GetMapping(value = "logout")
    public ModelAndView logout(HttpSession session) {
        session.invalidate();
        modelAndView.setViewName("viewuser/home");
        return modelAndView;
    }

    @GetMapping(value = "profile")
    public ModelAndView showProfile(HttpSession session){
        User user = (User) session.getAttribute("loggedInUser");
        List<Order> orders = checkoutService.getOrdersByUser(user);

        modelAndView.addObject("orders", orders);
        modelAndView.addObject("user", user);
        modelAndView.addObject("activeMenu", "profile");
        modelAndView.setViewName("viewuser/profile");
        return modelAndView;
    }
}
