package se.liu.game.game;

import se.liu.game.highscore.HighScore;
import se.liu.game.highscore.HighScoreList;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * Is the top class that manages everything at the upper level.
 * Manages loading the highscoreList into the game and also handles restarting the game when baseHealth reaches 0. Has a lot of dialog windows
 * for different scenarios when trying to load the JSON file.
 */
public class GameManager extends JComponent
{
    private GameEngine gameEngine = null;
    private GameFrame frame = null;
    private HighScoreList highScoreList = null;
    final private static int TICK_RATE = 5;
    final private static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);



    public void runGame(){
	LOGGER.info("Game started!");
	highScoreList = new HighScoreList();



	while(true) {
	    try {
		highScoreList.readFromFile();
		LOGGER.log(Level.INFO, "High scores loaded");
		break;
	    }
	    // catch fallthrough is okay in this case since the program will try again
	    catch(FileNotFoundException ex){
		LOGGER.log(Level.SEVERE, "Cannot find high score file", ex);
		if (JOptionPane.showConfirmDialog(this, "ERROR, could not find high score list file \nTry again?", "", JOptionPane.YES_NO_OPTION) ==
		    JOptionPane.NO_OPTION) {

		    System.exit(1);
		}
	    }
	    catch(IOException ex){
		LOGGER.log(Level.SEVERE, "Cannot access high score file", ex);
		if (JOptionPane.showConfirmDialog(this, "ERROR, could not access high score list file \nTry again?","", JOptionPane.YES_NO_OPTION) ==
		    JOptionPane.NO_OPTION){
		    System.exit(1);
		}
	    }
	}

	GameMap gameMap = new GameMap();
	gameEngine = new GameEngine(gameMap);


	frame = new GameFrame("Tower Defense", gameEngine);
	frame.pack();
	frame.setLocationRelativeTo(null);
	frame.setVisible(true);

	Timer gameClock = new Timer("Game Clock");
	TimerTask tickTask = new TimerTask()
	{
	    @Override public void run() {
		gameEngine.tick();
		if(gameOver()){
		    handleGameOver();
		    this.cancel();
		    frame.dispose();
		    runGame();

		}

	    }
	};

	gameClock.schedule(tickTask, 0, TICK_RATE);

    }


    public boolean gameOver() {
	return gameEngine.getBaseHealth() <= 0;
    }




    /*
    A little over the top but handles exception even if file disappears between reading and writing the file
     */
    public void handleGameOver(){
	if(highScoreList.isNotNull())JOptionPane.showMessageDialog(this, highScoreList);
	else {
	    // Ask user to overwrite if file format is invalid
	    while(true) {
		try {
		    if (JOptionPane.showConfirmDialog(this,
						      "High score file not setup correctly \nOverwrite?", "",
						      JOptionPane.YES_NO_OPTION) == JOptionPane.NO_OPTION) {
			System.exit(1);
		    }
		    else {
			highScoreList.overWriteFile();
			JOptionPane.showMessageDialog(this, highScoreList);
		    }
		    break;
		} catch (FileNotFoundException ex) {
		    LOGGER.log(Level.SEVERE, "Failed to find highscore file when trying to overwrite", ex);
		    if (JOptionPane.showConfirmDialog(this, "Failed to find highscore file \nTry again?", "ERROR",
						      JOptionPane.YES_NO_OPTION) == JOptionPane.NO_OPTION) {

			System.exit(1);
		    }
		}
	    }
	}



	if(JOptionPane.showConfirmDialog(this, "Do you want to save your high score?", "", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
	    // If file is not valid Json format, ask user to overwrite
	    String playerName = null;
	    while (playerName == null || playerName.isBlank()) {
		playerName = JOptionPane.showInputDialog("Enter your name");
	    }
	    while (true) {
		try {
		    if (highScoreList.isNotNull()) {
			highScoreList.addHighscoreList(new HighScore(gameEngine.getRound(), playerName));
			JOptionPane.showMessageDialog(this, highScoreList);
		    }
		    else if (JOptionPane.showConfirmDialog(this,
						      "High score file not setup \nOverwrite?", "",
						      JOptionPane.YES_NO_OPTION) == JOptionPane.NO_OPTION) {
			System.exit(1);
		    }
		    else {
			highScoreList.overWriteFile();
		    }

		    break;
		} catch (FileNotFoundException ex) {
		    LOGGER.log(Level.SEVERE, "Failed to access high score file after overwriting when trying to save. ", ex);
		    if (JOptionPane.showConfirmDialog(this, "Failed to access highscore file \nTry again?", "ERROR", JOptionPane.YES_NO_OPTION) ==
			JOptionPane.NO_OPTION) {

			System.exit(1);
		    }
		}
	    }
	}

    }


    public void startMusic() {
	//URL url = getClass().getResource("music.waw");
	if(JOptionPane.showConfirmDialog(this, "Do you want to play with music?", "", JOptionPane.YES_NO_OPTION) == JOptionPane.NO_OPTION)
	    return;
	try {
	    Clip clip = AudioSystem.getClip();
	    AudioInputStream ais = AudioSystem.getAudioInputStream(ClassLoader.getSystemResource("audio/music.wav"));
	    clip.open(ais);
	    clip.loop(Clip.LOOP_CONTINUOUSLY);
	    ais.close();
	    LOGGER.log(Level.INFO, "Music started");

	} catch (LineUnavailableException ex) {
	    JOptionPane.showMessageDialog(null, "Something went wrong trying to play the music.\nGame will start without music.");
	    LOGGER.log(Level.SEVERE, "Cannot play music", ex);
	}
	catch(IOException ex){
	    JOptionPane.showMessageDialog(null, "Something went wrong trying to access the audio file. \nGame will start without music.");
	    LOGGER.log(Level.SEVERE, "Cannot play music", ex);
	}
	catch(UnsupportedAudioFileException ex){
	    JOptionPane.showMessageDialog(null, "The audio file has an unsupported format. \nGame will start without music.");
	    LOGGER.log(Level.SEVERE, "Cannot play music, unsupported file format", ex);
	}
    }
}
