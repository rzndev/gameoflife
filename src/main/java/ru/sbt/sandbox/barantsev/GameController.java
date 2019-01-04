package ru.sbt.sandbox.barantsev;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class GameController {

    @Autowired
    GameModel game;
    @RequestMapping("/changes")
    public String gamechanges(Model model) {
        if (game.step()) {
            game.createGameField(10, 10);
        }
        List<List<String>> gameChanges =game.retrieveChanges();
        model.addAttribute("url", "http://127.0.0.1:8080/");
        model.addAttribute("gameField", gameChanges);
        return "game";
    }

    @RequestMapping("/")
    public String gamestatus(Model model) {
        List<List<String>> gameState = game.retrieveState();
        model.addAttribute("url", "http://127.0.0.1:8080/changes");
        model.addAttribute("gameField", gameState);
        return "game";
    }
}
