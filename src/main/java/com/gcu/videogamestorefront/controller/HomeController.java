package com.gcu.videogamestorefront.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.gcu.videogamestorefront.service.GameService;

@Controller
public class HomeController {

    private final GameService gameService;

    public HomeController(GameService gameService) {
        this.gameService = gameService;
    }

    @GetMapping({"/", "/games"})
    public String showGames(
            @RequestParam(required = false) String search,
            @RequestParam(required = false, defaultValue = "All") String platform,
            @RequestParam(required = false) List<String> genres,
            @RequestParam(required = false) List<String> priceRanges,
            Model model) {

        model.addAttribute(
                "games",
                gameService.filterGames(
                        search,
                        platform,
                        genres,
                        priceRanges));

        model.addAttribute("search", search);
        model.addAttribute("platform", platform);
        model.addAttribute("genres", genres);
        model.addAttribute("priceRanges", priceRanges);

        return "games";
    }

    @GetMapping("/games/details")
    public String showGameDetails(
            @RequestParam Long id,
            Model model) {

        model.addAttribute(
                "game",
                gameService.getGameById(id));

        return "game-details";
    }
}