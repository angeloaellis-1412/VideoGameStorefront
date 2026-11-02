package com.gcu.videogamestorefront.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gcu.videogamestorefront.model.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
}