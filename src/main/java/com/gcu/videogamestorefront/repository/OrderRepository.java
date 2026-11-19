package com.gcu.videogamestorefront.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gcu.videogamestorefront.model.Order;

/**
 * Repository interface for Order entities.
 * Provides standard CRUD operations for customer orders.
 */
public interface OrderRepository extends JpaRepository<Order, Long> {
}