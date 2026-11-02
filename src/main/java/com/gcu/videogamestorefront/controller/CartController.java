package com.gcu.videogamestorefront.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.gcu.videogamestorefront.model.Game;
import com.gcu.videogamestorefront.service.CartService;
import com.gcu.videogamestorefront.service.GameService;

@Controller
public class CartController {

    private final CartService cartService;
    private final GameService gameService;

    public CartController(CartService cartService,
                          GameService gameService) {
        this.cartService = cartService;
        this.gameService = gameService;
    }

    @PostMapping("/cart/add")
    public String addToCart(@RequestParam Long gameId) {

        Game game = gameService.getGameById(gameId);

        cartService.addGame(game);

        return "redirect:/cart";
    }

    @GetMapping("/cart")
    public String showCart(Model model) {

        model.addAttribute("items", cartService.getItems());
        model.addAttribute("subtotal", cartService.getSubtotal());
        model.addAttribute("shipping", cartService.getShipping());
        model.addAttribute("total", cartService.getTotal());

        return "cart";
    }

    @PostMapping("/cart/increase")
    public String increaseQuantity(@RequestParam Long gameId) {

        cartService.increaseQuantity(gameId);

        return "redirect:/cart";
    }

    @PostMapping("/cart/decrease")
    public String decreaseQuantity(@RequestParam Long gameId) {

        cartService.decreaseQuantity(gameId);

        return "redirect:/cart";
    }

    @PostMapping("/cart/remove")
    public String removeGame(@RequestParam Long gameId) {

        cartService.removeGame(gameId);

        return "redirect:/cart";
    }
}