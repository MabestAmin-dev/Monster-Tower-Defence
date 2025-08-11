package se.liu.game.game;


import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Will create a new GameManager and tell it to start the game and music.
 * START HERE!
 */
public class GameLauncher
{
    public static void main(String[] args) {

	GameManager gameManager = new GameManager();



	/*
	How can we log the logger not being setup???????????????
	 */
	try {
	    GameLogger.setup();
	}
	catch (IOException ex){
	    Logger.getLogger(Logger.GLOBAL_LOGGER_NAME).log(Level.SEVERE, "Cannot setup logger", ex);
	}
	gameManager.runGame();
	gameManager.startMusic();


    }
}
