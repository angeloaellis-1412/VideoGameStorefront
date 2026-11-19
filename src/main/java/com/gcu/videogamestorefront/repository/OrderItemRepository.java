package com.gcu.videogamestorefront.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gcu.videogamestorefront.model.OrderItem;

/**
 * Repository interface for OrderItem entities.
 * Provides standard CRUD operations for individual items
 * associated with customer orders.
 */
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}