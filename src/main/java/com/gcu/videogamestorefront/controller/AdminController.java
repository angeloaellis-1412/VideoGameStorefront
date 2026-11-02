package com.gcu.videogamestorefront.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.gcu.videogamestorefront.model.Game;
import com.gcu.videogamestorefront.service.GameService;

@Controller
public class AdminController {

    private final GameService gameService;

    public AdminController(GameService gameService) {
        this.gameService = gameService;
    }

    @GetMapping("/admin")
    public String showDashboard(Model model) {

        model.addAttribute("games", gameService.getAllGames());

        return "admin/dashboard";
    }

    @GetMapping("/admin/add")
    public String showAddGameForm(Model model) {

        model.addAttribute("game", new Game());

        return "admin/game-form";
    }

    @GetMapping("/admin/edit")
    public String showEditGameForm(
            @RequestParam Long id,
            Model model) {

        model.addAttribute(
                "game",
                gameService.getGameById(id));

        return "admin/game-form";
    }

    @PostMapping("/admin/save")
    public String saveGame(Game game) {

        gameService.saveGame(game);

        return "redirect:/admin";
    }

    @PostMapping("/admin/delete")
    public String deleteGame(@RequestParam Long id) {

        gameService.deleteGame(id);

        return "redirect:/admin";
    }
}