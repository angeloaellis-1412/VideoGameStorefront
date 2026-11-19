package com.gcu.videogamestorefront.model;

import java.math.BigDecimal;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;

/**
 * Represents an individual game included in a customer order.
 * Stores the purchased game, quantity, unit price, and relationship
 * to the parent order.
 */
@Entity
@Table(name = "order_items")
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Min(1)
    private int quantity;

    private BigDecimal price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "game_id")
    private Game game;

    /**
     * Creates an empty OrderItem object.
     * Required by JPA for entity creation.
     */
    public OrderItem() {
    }

    /**
     * Creates an order item for the selected game.
     *
     * @param game game included in the order
     * @param quantity number of copies purchased
     * @param price price of the game at the time of purchase
     */
    public OrderItem(Game game, int quantity, BigDecimal price) {
        this.game = game;
        this.quantity = quantity;
        this.price = price;
    }

    /**
     * Returns the unique order item ID.
     *
     * @return order item ID
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets the unique order item ID.
     *
     * @param id order item ID
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Returns the quantity purchased.
     *
     * @return item quantity
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * Sets the quantity purchased.
     *
     * @param quantity item quantity
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    /**
     * Returns the unit price of the game.
     *
     * @return game price at the time of purchase
     */
    public BigDecimal getPrice() {
        return price;
    }

    /**
     * Sets the unit price of the game.
     *
     * @param price game price
     */
    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    /**
     * Returns the parent order.
     *
     * @return associated order
     */
    public Order getOrder() {
        return order;
    }

    /**
     * Sets the parent order.
     *
     * @param order associated order
     */
    public void setOrder(Order order) {
        this.order = order;
    }

    /**
     * Returns the game associated with this order item.
     *
     * @return purchased game
     */
    public Game getGame() {
        return game;
    }

    /**
     * Sets the game associated with this order item.
     *
     * @param game purchased game
     */
    public void setGame(Game game) {
        this.game = game;
    }

    /**
     * Calculates the total price for this order item.
     *
     * @return unit price multiplied by quantity, or zero if price is null
     */
    public BigDecimal getLineTotal() {
        if (price == null) {
            return BigDecimal.ZERO;
        }

        return price.multiply(BigDecimal.valueOf(quantity));
    }
}