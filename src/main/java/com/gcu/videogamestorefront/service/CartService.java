package com.gcu.videogamestorefront.service;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

import com.gcu.videogamestorefront.model.Game;

@Service
@SessionScope
public class CartService {

    private final Map<Long, CartItem> items = new LinkedHashMap<>();

    public void addGame(Game game) {
        CartItem item = items.get(game.getId());

        if (item == null) {
            items.put(game.getId(), new CartItem(game, 1));
        } else {
            item.setQuantity(item.getQuantity() + 1);
        }
    }

    public void increaseQuantity(Long gameId) {
        CartItem item = items.get(gameId);

        if (item != null) {
            item.setQuantity(item.getQuantity() + 1);
        }
    }

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

    public void removeGame(Long gameId) {
        items.remove(gameId);
    }

    public Collection<CartItem> getItems() {
        return items.values();
    }

    public BigDecimal getSubtotal() {
        return items.values()
                .stream()
                .map(CartItem::getLineTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public BigDecimal getShipping() {
        if (items.isEmpty()) {
            return BigDecimal.ZERO;
        }

        return new BigDecimal("9.99");
    }

    public BigDecimal getTotal() {
        return getSubtotal().add(getShipping());
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }

    public void clear() {
        items.clear();
    }

    public static class CartItem {

        private Game game;
        private int quantity;

        public CartItem(Game game, int quantity) {
            this.game = game;
            this.quantity = quantity;
        }

        public Game getGame() {
            return game;
        }

        public int getQuantity() {
            return quantity;
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }

        public BigDecimal getLineTotal() {
            return game.getPrice()
                    .multiply(BigDecimal.valueOf(quantity));
        }
    }
}