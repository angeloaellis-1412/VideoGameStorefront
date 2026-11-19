package com.gcu.videogamestorefront.controller;

import java.io.IOException;
import java.util.Base64;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.gcu.videogamestorefront.model.Game;
import com.gcu.videogamestorefront.service.GameService;

/**
 * Controller for administrator inventory management.
 * Handles the admin dashboard, game creation and editing,
 * image uploads, and game deletion.
 */
@Controller
public class AdminController {

    private final GameService gameService;

    /**
     * Creates the admin controller with the required game service.
     *
     * @param gameService service used to manage game inventory
     */
    public AdminController(GameService gameService) {
        this.gameService = gameService;
    }

    /**
     * Displays the administrator dashboard and current game inventory.
     *
     * @param model model used to pass game data to the view
     * @return the administrator dashboard view
     */
    @GetMapping("/admin")
    public String showDashboard(Model model) {

        model.addAttribute("games", gameService.getAllGames());

        return "admin/dashboard";
    }

    /**
     * Displays the form used to add a new game.
     *
     * @param model model used to provide the new game and available platforms
     * @return the game form view
     */
    @GetMapping("/admin/add")
    public String showAddGameForm(Model model) {

        model.addAttribute("game", new Game());

        model.addAttribute(
                "platforms",
                gameService.getAllGames().stream()
                        .map(Game::getPlatform)
                        .filter(platform -> platform != null && !platform.isBlank())
                        .distinct()
                        .sorted()
                        .toList());

        return "admin/game-form";
    }

    /**
     * Displays the form used to edit an existing game.
     *
     * @param id ID of the game being edited
     * @param model model used to provide the game and available platforms
     * @return the game form view
     */
    @GetMapping("/admin/edit")
    public String showEditGameForm(
            @RequestParam Long id,
            Model model) {

        model.addAttribute(
                "game",
                gameService.getGameById(id));

        model.addAttribute(
                "platforms",
                gameService.getAllGames().stream()
                        .map(Game::getPlatform)
                        .filter(platform -> platform != null && !platform.isBlank())
                        .distinct()
                        .sorted()
                        .toList());

        return "admin/game-form";
    }

    /**
     * Saves a new or edited game and processes optional uploaded images.
     * Existing images are preserved during editing unless replacement
     * image files are selected.
     *
     * @param game game information submitted from the administrator form
     * @param mainImageFile optional main game image
     * @param imageFile1 optional additional game image
     * @param imageFile2 optional additional game image
     * @param imageFile3 optional additional game image
     * @return redirect to the administrator dashboard
     * @throws IOException if an uploaded image cannot be read or converted
     */
    @PostMapping("/admin/save")
    public String saveGame(
            Game game,
            @RequestParam(value = "mainImageFile", required = false)
            MultipartFile mainImageFile,

            @RequestParam(value = "imageFile1", required = false)
            MultipartFile imageFile1,

            @RequestParam(value = "imageFile2", required = false)
            MultipartFile imageFile2,

            @RequestParam(value = "imageFile3", required = false)
            MultipartFile imageFile3) throws IOException {

        /*
         * When editing an existing game, keep its current images
         * unless the administrator chooses replacement files.
         */
        if (game.getId() != null) {

            Game existingGame = gameService.getGameById(game.getId());

            game.setMainImageUrl(existingGame.getMainImageUrl());
            game.setImageUrl1(existingGame.getImageUrl1());
            game.setImageUrl2(existingGame.getImageUrl2());
            game.setImageUrl3(existingGame.getImageUrl3());
        }

        if (mainImageFile != null && !mainImageFile.isEmpty()) {
            game.setMainImageUrl(convertImage(mainImageFile));
        }

        if (imageFile1 != null && !imageFile1.isEmpty()) {
            game.setImageUrl1(convertImage(imageFile1));
        }

        if (imageFile2 != null && !imageFile2.isEmpty()) {
            game.setImageUrl2(convertImage(imageFile2));
        }

        if (imageFile3 != null && !imageFile3.isEmpty()) {
            game.setImageUrl3(convertImage(imageFile3));
        }

        gameService.saveGame(game);

        return "redirect:/admin";
    }

    /**
     * Deletes a game from the inventory.
     *
     * @param id ID of the game to delete
     * @return redirect to the administrator dashboard
     */
    @PostMapping("/admin/delete")
    public String deleteGame(@RequestParam Long id) {

        gameService.deleteGame(id);

        return "redirect:/admin";
    }

    /**
     * Converts an uploaded image into a Base64 data URL so the image
     * can be stored with the game and displayed directly in the browser.
     *
     * @param file uploaded image file
     * @return Base64 data URL representing the uploaded image
     * @throws IOException if the uploaded file cannot be read
     */
    private String convertImage(MultipartFile file) throws IOException {

        String contentType = file.getContentType();

        if (contentType == null || contentType.isBlank()) {
            contentType = "image/jpeg";
        }

        String base64 =
                Base64.getEncoder().encodeToString(file.getBytes());

        return "data:" + contentType + ";base64," + base64;
    }
}