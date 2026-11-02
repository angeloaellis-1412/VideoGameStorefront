package com.gcu.videogamestorefront.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gcu.videogamestorefront.model.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}