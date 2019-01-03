package ru.sbt.sandbox.barantsev;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
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
        List<List<String>> gameField = new ArrayList<List<String>>();
        for(int r = 0; r < game.getRows(); r++){
            List<String> cols = new ArrayList<String>();
            gameField.add(cols);
            for (int c = 0; c < game.getColumns(); c++) {
                switch (game.retrieveCellStatus(r, c)) {
                    case Live:
                        cols.add("celllive");
                        break;
                    case Empty:
                        cols.add("cellempty");
                        break;
                    case Burn:
                        cols.add("cellburn");
                        break;
                    case Die:
                        cols.add("celldie");
                        break;
                }
            }
        }
        model.addAttribute("url", "http://127.0.0.1:8080/");
        model.addAttribute("gameField", gameField);
        return "game";
    }

    @RequestMapping("/")
    public String gamestatus(Model model) {
        List<List<String>> gameField = new ArrayList<List<String>>();
        for(int r = 0; r < game.getRows(); r++){
            List<String> cols = new ArrayList<String>();
            gameField.add(cols);
            for (int c = 0; c < game.getColumns(); c++) {
                if (game.retrieveCell(r, c))
                    cols.add("celllive");
                else
                    cols.add("cellempty");
            }
        }
        model.addAttribute("url", "http://127.0.0.1:8080/changes");
        model.addAttribute("gameField", gameField);
        return "game";
    }
}
