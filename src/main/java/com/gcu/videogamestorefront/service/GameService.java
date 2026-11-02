package com.gcu.videogamestorefront.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Stream;

import org.springframework.stereotype.Service;

import com.gcu.videogamestorefront.model.Game;
import com.gcu.videogamestorefront.repository.GameRepository;

@Service
public class GameService {

    private final GameRepository gameRepository;

    public GameService(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    public List<Game> getAllGames() {
        return gameRepository.findAll();
    }

    public List<Game> getActiveGames() {
        return gameRepository.findByActiveTrue();
    }

    public List<Game> filterGames(
            String search,
            String platform,
            List<String> genres,
            List<String> priceRanges) {

        Stream<Game> games = getActiveGames().stream();

        // Search by title
        if (search != null && !search.isBlank()) {
            String searchText = search.toLowerCase();

            games = games.filter(game ->
                    game.getTitle().toLowerCase().contains(searchText));
        }

        // Filter by platform
        if (platform != null
                && !platform.isBlank()
                && !platform.equalsIgnoreCase("All")) {

            games = games.filter(game ->
                    matchesPlatform(game, platform));
        }

        // Filter by one or more genres
        if (genres != null && !genres.isEmpty()) {

            games = games.filter(game ->
                    genres.stream().anyMatch(genre ->
                            game.getGenre().equalsIgnoreCase(genre)));
        }

        // Filter by one or more price ranges
        if (priceRanges != null && !priceRanges.isEmpty()) {

            games = games.filter(game ->
                    priceRanges.stream().anyMatch(range ->
                            matchesPriceRange(game.getPrice(), range)));
        }

        return games.toList();
    }

    private boolean matchesPlatform(Game game, String platform) {

        String gamePlatform = game.getPlatform().toLowerCase();
        String selectedPlatform = platform.toLowerCase();

        if (selectedPlatform.equals("playstation")) {
            return gamePlatform.contains("playstation");
        }

        if (selectedPlatform.equals("xbox")) {
            return gamePlatform.contains("xbox");
        }

        if (selectedPlatform.equals("nintendo")) {
            return gamePlatform.contains("nintendo");
        }

        if (selectedPlatform.equals("handheld")) {
            return gamePlatform.contains("switch")
                    || gamePlatform.contains("steam deck")
                    || gamePlatform.contains("handheld");
        }

        if (selectedPlatform.equals("pc")) {
            return gamePlatform.equals("pc");
        }

        return true;
    }

    private boolean matchesPriceRange(
            BigDecimal price,
            String range) {

        BigDecimal twenty = new BigDecimal("20.00");
        BigDecimal fifty = new BigDecimal("50.00");
        BigDecimal hundred = new BigDecimal("100.00");

        return switch (range) {

            case "under20" ->
                price.compareTo(twenty) < 0;

            case "20to50" ->
                price.compareTo(twenty) >= 0
                && price.compareTo(fifty) <= 0;

            case "50to100" ->
                price.compareTo(fifty) > 0
                && price.compareTo(hundred) <= 0;

            case "over100" ->
                price.compareTo(hundred) > 0;

            default -> false;
        };
    }

    public Game getGameById(Long id) {
        return gameRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Game not found."));
    }

    public Game saveGame(Game game) {
        return gameRepository.save(game);
    }

    public void deleteGame(Long id) {
        gameRepository.deleteById(id);
    }
}