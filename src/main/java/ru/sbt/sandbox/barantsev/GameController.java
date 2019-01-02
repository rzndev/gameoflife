package ru.sbt.sandbox.barantsev;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;



@RestController
public class GameController {
    private final Object sync = new Object();
    private GameModel theGame = new GameModel(10,10);
    private GameView view = new GameView(theGame);
    @RequestMapping("/")
    public String index() {
        String result = "";
        if (theGame.getNumberOfReads() % 2 == 0) {
            result = view.toString();
            if (theGame.step()) {
                synchronized (sync) {
                    theGame = new GameModel(10, 10);
                    view = new GameView(theGame);
                }
            }
        } else {
            result = view.printChanges();
        }

        return result;
    }
}
