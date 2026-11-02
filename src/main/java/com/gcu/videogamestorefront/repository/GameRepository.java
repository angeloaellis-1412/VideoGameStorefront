package com.gcu.videogamestorefront.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gcu.videogamestorefront.model.Game;

public interface GameRepository extends JpaRepository<Game, Long> {

    List<Game> findByActiveTrue();

    List<Game> findByTitleContainingIgnoreCaseAndActiveTrue(String title);
}