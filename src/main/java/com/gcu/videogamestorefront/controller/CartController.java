package com.gcu.videogamestorefront.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.gcu.videogamestorefront.model.Game;
import com.gcu.videogamestorefront.service.CartService;
import com.gcu.videogamestorefront.service.GameService;

/**
 * Controller for shopping cart operations.
 * Handles adding games to the cart, viewing the cart,
 * changing item quantities, and removing games.
 */
@Controller
public class CartController {

    private final CartService cartService;
    private final GameService gameService;

    /**
     * Creates the cart controller with the required cart and game services.
     *
     * @param cartService service used to manage shopping cart operations
     * @param gameService service used to retrieve game information
     */
    public CartController(CartService cartService,
                          GameService gameService) {
        this.cartService = cartService;
        this.gameService = gameService;
    }

    /**
     * Adds the selected game to the shopping cart.
     *
     * @param gameId ID of the game being added to the cart
     * @return redirect to the shopping cart page
     */
    @PostMapping("/cart/add")
    public String addToCart(@RequestParam Long gameId) {

        Game game = gameService.getGameById(gameId);

        cartService.addGame(game);

        return "redirect:/cart";
    }

    /**
     * Displays the shopping cart and calculated totals.
     *
     * @param model model used to pass cart items and totals to the view
     * @return the shopping cart view
     */
    @GetMapping("/cart")
    public String showCart(Model model) {

        model.addAttribute("items", cartService.getItems());
        model.addAttribute("subtotal", cartService.getSubtotal());
        model.addAttribute("shipping", cartService.getShipping());
        model.addAttribute("total", cartService.getTotal());

        return "cart";
    }

    /**
     * Increases the quantity of a game in the shopping cart.
     *
     * @param gameId ID of the game whose quantity will be increased
     * @return redirect to the shopping cart page
     */
    @PostMapping("/cart/increase")
    public String increaseQuantity(@RequestParam Long gameId) {

        cartService.increaseQuantity(gameId);

        return "redirect:/cart";
    }

    /**
     * Decreases the quantity of a game in the shopping cart.
     *
     * @param gameId ID of the game whose quantity will be decreased
     * @return redirect to the shopping cart page
     */
    @PostMapping("/cart/decrease")
    public String decreaseQuantity(@RequestParam Long gameId) {

        cartService.decreaseQuantity(gameId);

        return "redirect:/cart";
    }

    /**
     * Removes the selected game from the shopping cart.
     *
     * @param gameId ID of the game being removed
     * @return redirect to the shopping cart page
     */
    @PostMapping("/cart/remove")
    public String removeGame(@RequestParam Long gameId) {

        cartService.removeGame(gameId);

        return "redirect:/cart";
    }
}