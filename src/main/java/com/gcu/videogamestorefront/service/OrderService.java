package com.gcu.videogamestorefront.service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gcu.videogamestorefront.model.Game;
import com.gcu.videogamestorefront.model.Order;
import com.gcu.videogamestorefront.model.OrderItem;
import com.gcu.videogamestorefront.repository.GameRepository;
import com.gcu.videogamestorefront.repository.OrderRepository;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final GameRepository gameRepository;

    public OrderService(OrderRepository orderRepository,
                        GameRepository gameRepository) {
        this.orderRepository = orderRepository;
        this.gameRepository = gameRepository;
    }

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

    public Order getOrderById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Order not found."));
    }
}