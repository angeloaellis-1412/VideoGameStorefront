package com.gcu.videogamestorefront.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.gcu.videogamestorefront.model.Order;
import com.gcu.videogamestorefront.service.CartService;
import com.gcu.videogamestorefront.service.OrderService;

/**
 * Controller for the checkout and order confirmation process.
 * Handles displaying the checkout page, placing an order,
 * and showing the final order confirmation.
 */
@Controller
public class CheckoutController {

    private final CartService cartService;
    private final OrderService orderService;

    /**
     * Creates the checkout controller with the required cart and order services.
     *
     * @param cartService service used to access the current shopping cart
     * @param orderService service used to create and retrieve orders
     */
    public CheckoutController(CartService cartService,
                              OrderService orderService) {
        this.cartService = cartService;
        this.orderService = orderService;
    }

    /**
     * Displays the checkout page with the current cart items and totals.
     * If the shopping cart is empty, the user is redirected back to the cart.
     *
     * @param model model used to pass checkout information to the view
     * @return the checkout view or a redirect to the cart
     */
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

    /**
     * Creates and saves an order using the information submitted
     * from the checkout form and the current shopping cart.
     *
     * @param order order information submitted by the customer
     * @return redirect to the order confirmation page or the cart if empty
     */
    @PostMapping("/checkout/place-order")
    public String placeOrder(@ModelAttribute Order order) {

        if (cartService.isEmpty()) {
            return "redirect:/cart";
        }

        Order savedOrder = orderService.placeOrder(order, cartService);

        return "redirect:/order-confirmation?id=" + savedOrder.getId();
    }

    /**
     * Displays the confirmation page for a completed order.
     *
     * @param id ID of the completed order
     * @param model model used to provide the saved order to the view
     * @return the order confirmation view
     */
    @GetMapping("/order-confirmation")
    public String showConfirmation(
            Long id,
            Model model) {

        model.addAttribute(
                "order",
                orderService.getOrderById(id));

        return "order-confirmation";
    }
}