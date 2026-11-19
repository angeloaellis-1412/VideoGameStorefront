package com.gcu.videogamestorefront.service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gcu.videogamestorefront.model.Game;
import com.gcu.videogamestorefront.model.Order;
import com.gcu.videogamestorefront.model.OrderItem;
import com.gcu.videogamestorefront.repository.GameRepository;
import com.gcu.videogamestorefront.repository.OrderRepository;

/**
 * Service responsible for processing customer orders.
 * Creates order records, validates inventory, updates game stock,
 * saves completed orders, and retrieves existing orders.
 */
@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final GameRepository gameRepository;

    /**
     * Creates the order service with the required repositories.
     *
     * @param orderRepository repository used to save and retrieve orders
     * @param gameRepository repository used to update game inventory
     */
    public OrderService(OrderRepository orderRepository,
                        GameRepository gameRepository) {
        this.orderRepository = orderRepository;
        this.gameRepository = gameRepository;
    }

    /**
     * Creates and saves an order using the current shopping cart.
     * The method verifies available stock, creates order items,
     * decreases inventory quantities, saves the order, and clears
     * the shopping cart after the order is completed.
     *
     * @param order customer order information
     * @param cartService shopping cart containing the selected games
     * @return the saved order
     * @throws RuntimeException if there is not enough stock for a game
     */
    @Transactional
    public Order placeOrder(Order order, CartService cartService) {

        order.setOrderDate(LocalDateTime.now());
        order.setTotalAmount(cartService.getTotal());

        for (CartService.CartItem cartItem : cartService.getItems()) {

            Game game = cartItem.getGame();
            int quantity = cartItem.getQuantity();

            if (game.getStockQuantity() < quantity) {
                throw new RuntimeException(
                        "Not enough stock available for " + game.getTitle());
            }

            OrderItem orderItem = new OrderItem(
                    game,
                    quantity,
                    game.getPrice());

            order.addItem(orderItem);

            game.setStockQuantity(
                    game.getStockQuantity() - quantity);

            gameRepository.save(game);
        }

        Order savedOrder = orderRepository.save(order);

        cartService.clear();

        return savedOrder;
    }

    /**
     * Retrieves an order by its unique ID.
     *
     * @param id ID of the requested order
     * @return order matching the supplied ID
     * @throws RuntimeException if the order cannot be found
     */
    public Order getOrderById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Order not found."));
    }
}