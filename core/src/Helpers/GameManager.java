package Helpers;

/**
 * Created by Darrell Payne on 8/20/17.
 */

public class GameManager {

    private static final GameManager ourInstance = new GameManager();

    public boolean gameStartedFromMainMenu, isPaused = true;
    public int lifeScore, coinScore, score;

    private GameManager() {

    }

    public static GameManager getInstance() {
        return ourInstance;
    }


} // GameManager
