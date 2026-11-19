package com.gcu.videogamestorefront.service;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

import com.gcu.videogamestorefront.model.Game;

/**
 * Service responsible for managing the user's shopping cart.
 * Cart information is stored for the duration of the user's session.
 */
@Service
@SessionScope
public class CartService {

    private final Map<Long, CartItem> items = new LinkedHashMap<>();

    /**
     * Adds a game to the shopping cart.
     * If the game is already present, its quantity is increased.
     *
     * @param game game being added to the cart
     */
    public void addGame(Game game) {
        CartItem item = items.get(game.getId());

        if (item == null) {
            items.put(game.getId(), new CartItem(game, 1));
        } else {
            item.setQuantity(item.getQuantity() + 1);
        }
    }

    /**
     * Increases the quantity of an existing game in the cart.
     *
     * @param gameId ID of the game whose quantity will be increased
     */
    public void increaseQuantity(Long gameId) {
        CartItem item = items.get(gameId);

        if (item != null) {
            item.setQuantity(item.getQuantity() + 1);
        }
    }

    /**
     * Decreases the quantity of a game in the cart.
     * If the quantity reaches zero, the game is removed.
     *
     * @param gameId ID of the game whose quantity will be decreased
     */
    public void decreaseQuantity(Long gameId) {
        CartItem item = items.get(gameId);

        if (item != null) {
            if (item.getQuantity() > 1) {
                item.setQuantity(item.getQuantity() - 1);
            } else {
                items.remove(gameId);
            }
        }
    }

    /**
     * Removes a game completely from the shopping cart.
     *
     * @param gameId ID of the game to remove
     */
    public void removeGame(Long gameId) {
        items.remove(gameId);
    }

    /**
     * Returns all items currently stored in the shopping cart.
     *
     * @return collection of cart items
     */
    public Collection<CartItem> getItems() {
        return items.values();
    }

    /**
     * Calculates the subtotal for all items in the cart.
     *
     * @return subtotal of all cart items
     */
    public BigDecimal getSubtotal() {
        return items.values()
                .stream()
                .map(CartItem::getLineTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    /**
     * Calculates the shipping charge for the cart.
     * Shipping is zero for an empty cart and $9.99 otherwise.
     *
     * @return shipping charge
     */
    public BigDecimal getShipping() {
        if (items.isEmpty()) {
            return BigDecimal.ZERO;
        }

        return new BigDecimal("9.99");
    }

    /**
     * Calculates the final cart total including shipping.
     *
     * @return total cart amount
     */
    public BigDecimal getTotal() {
        return getSubtotal().add(getShipping());
    }

    /**
     * Determines whether the shopping cart is empty.
     *
     * @return true if the cart contains no items, otherwise false
     */
    public boolean isEmpty() {
        return items.isEmpty();
    }

    /**
     * Removes all items from the shopping cart.
     */
    public void clear() {
        items.clear();
    }

    /**
     * Represents a game stored in the shopping cart
     * along with its selected quantity.
     */
    public static class CartItem {

        private Game game;
        private int quantity;

        /**
         * Creates a cart item for the selected game and quantity.
         *
         * @param game game stored in the cart
         * @param quantity selected quantity
         */
        public CartItem(Game game, int quantity) {
            this.game = game;
            this.quantity = quantity;
        }

        /**
         * Returns the game associated with this cart item.
         *
         * @return game stored in the cart item
         */
        public Game getGame() {
            return game;
        }

        /**
         * Returns the selected quantity.
         *
         * @return item quantity
         */
        public int getQuantity() {
            return quantity;
        }

        /**
         * Updates the selected quantity.
         *
         * @param quantity new item quantity
         */
        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }

        /**
         * Calculates the total price for this cart item.
         *
         * @return game price multiplied by the selected quantity
         */
        public BigDecimal getLineTotal() {
            return game.getPrice()
                    .multiply(BigDecimal.valueOf(quantity));
        }
    }
}