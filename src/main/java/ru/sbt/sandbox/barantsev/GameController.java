package ru.sbt.sandbox.barantsev;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
public class GameController {

    private GameWeb theGame = new GameWeb(10,10);

    @RequestMapping("/")
    public String index() {
        String result = theGame.toString();
        theGame.step();
        return result;
    }
}
