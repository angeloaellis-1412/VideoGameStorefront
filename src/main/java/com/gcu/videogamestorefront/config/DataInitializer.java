package com.gcu.videogamestorefront.config;

import java.math.BigDecimal;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.gcu.videogamestorefront.model.Game;
import com.gcu.videogamestorefront.repository.GameRepository;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner loadGames(GameRepository gameRepository) {
        return args -> {

            if (gameRepository.count() == 0) {

                gameRepository.save(new Game(
                        "Final Fantasy VII Rebirth",
                        "PlayStation 5",
                        "RPG",
                        2024,
                        new BigDecimal("69.99"),
                        12,
                        "An action RPG adventure featuring Cloud and his allies.",
                        true));

                gameRepository.save(new Game(
                        "Resident Evil 4",
                        "PlayStation 5",
                        "Action",
                        2023,
                        new BigDecimal("39.99"),
                        8,
                        "A survival horror action game featuring Leon Kennedy.",
                        true));

                gameRepository.save(new Game(
                        "Dragon Ball: Sparking! ZERO",
                        "PlayStation 5",
                        "Fighting",
                        2024,
                        new BigDecimal("69.99"),
                        15,
                        "A fast-paced 3D fighting game featuring Dragon Ball characters.",
                        true));

                gameRepository.save(new Game(
                        "Elden Ring",
                        "PC",
                        "RPG",
                        2022,
                        new BigDecimal("59.99"),
                        10,
                        "An open-world fantasy action RPG.",
                        true));

                gameRepository.save(new Game(
                        "NBA 2K26",
                        "PlayStation 5",
                        "Sports",
                        2025,
                        new BigDecimal("49.99"),
                        6,
                        "A basketball simulation game.",
                        true));

                gameRepository.save(new Game(
                        "Civilization VII",
                        "PC",
                        "Strategy",
                        2025,
                        new BigDecimal("59.99"),
                        9,
                        "A turn-based strategy game focused on building civilizations.",
                        true));
            }
        };
    }
}