package com.gcu.videogamestorefront.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.gcu.videogamestorefront.model.Order;
import com.gcu.videogamestorefront.service.CartService;
import com.gcu.videogamestorefront.service.OrderService;

@Controller
public class CheckoutController {

    private final CartService cartService;
    private final OrderService orderService;

    public CheckoutController(CartService cartService,
                              OrderService orderService) {
        this.cartService = cartService;
        this.orderService = orderService;
    }

    @GetMapping("/checkout")
    public String showCheckout(Model model) {

        if (cartService.isEmpty()) {
            return "redirect:/cart";
        }

        model.addAttribute("order", new Order());
        model.addAttribute("items", cartService.getItems());
        model.addAttribute("subtotal", cartService.getSubtotal());
        model.addAttribute("shipping", cartService.getShipping());
        model.addAttribute("total", cartService.getTotal());

        return "checkout";
    }

    @PostMapping("/checkout/place-order")
    public String placeOrder(@ModelAttribute Order order) {

        if (cartService.isEmpty()) {
            return "redirect:/cart";
        }

        Order savedOrder = orderService.placeOrder(order, cartService);

        return "redirect:/order-confirmation?id=" + savedOrder.getId();
    }

    @GetMapping("/order-confirmation")
    public String showConfirmation(
            Long id,
            Model model) {

        model.addAttribute("order",
                orderService.getOrderById(id));

        return "order-confirmation";
    }
}