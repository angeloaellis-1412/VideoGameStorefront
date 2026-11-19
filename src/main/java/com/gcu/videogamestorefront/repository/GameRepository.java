package com.gcu.videogamestorefront.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gcu.videogamestorefront.model.Game;

/**
 * Repository interface for Game entities.
 * Provides standard CRUD operations through JpaRepository
 * and custom queries used by the storefront.
 */
public interface GameRepository extends JpaRepository<Game, Long> {

    /**
     * Retrieves all active games.
     *
     * @return list of active games
     */
    List<Game> findByActiveTrue();

    /**
     * Searches active games by title using a case-insensitive match.
     *
     * @param title title text used for the search
     * @return list of active games matching the title
     */
    List<Game> findByTitleContainingIgnoreCaseAndActiveTrue(String title);
}