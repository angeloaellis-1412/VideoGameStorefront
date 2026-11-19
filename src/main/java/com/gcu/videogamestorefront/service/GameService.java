package com.gcu.videogamestorefront.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Stream;

import org.springframework.stereotype.Service;

import com.gcu.videogamestorefront.model.Game;
import com.gcu.videogamestorefront.repository.GameRepository;

/**
 * Service responsible for game inventory operations.
 * Provides access to game data and supports storefront
 * searching and filtering.
 */
@Service
public class GameService {

    private final GameRepository gameRepository;

    /**
     * Creates the game service with the required repository.
     *
     * @param gameRepository repository used to access game data
     */
    public GameService(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    /**
     * Retrieves all games in the database.
     *
     * @return list of all games
     */
    public List<Game> getAllGames() {
        return gameRepository.findAll();
    }

    /**
     * Retrieves only games marked as active.
     *
     * @return list of active games
     */
    public List<Game> getActiveGames() {
        return gameRepository.findByActiveTrue();
    }

    /**
     * Filters active games using optional search text,
     * platform, genre, and price range selections.
     *
     * @param search optional title search text
     * @param platform optional selected platform
     * @param genres optional list of selected genres
     * @param priceRanges optional list of selected price ranges
     * @return list of games matching the selected filters
     */
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

    /**
     * Determines whether a game matches the selected platform filter.
     *
     * @param game game being evaluated
     * @param platform selected platform filter
     * @return true if the game matches the platform, otherwise false
     */
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

    /**
     * Determines whether a game price falls within
     * the selected price range.
     *
     * @param price game price
     * @param range selected price range
     * @return true if the price matches the range, otherwise false
     */
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

    /**
     * Retrieves a game by its unique ID.
     *
     * @param id ID of the requested game
     * @return game matching the supplied ID
     * @throws RuntimeException if the game cannot be found
     */
    public Game getGameById(Long id) {
        return gameRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Game not found."));
    }

    /**
     * Saves a new or updated game.
     *
     * @param game game to save
     * @return saved game entity
     */
    public Game saveGame(Game game) {
        return gameRepository.save(game);
    }

    /**
     * Deletes a game by its unique ID.
     *
     * @param id ID of the game to delete
     */
    public void deleteGame(Long id) {
        gameRepository.deleteById(id);
    }
}