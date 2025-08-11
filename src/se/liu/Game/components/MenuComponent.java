package se.liu.game.components;

import se.liu.game.game.GameMap;
import se.liu.game.buttons.ChooseMapButton;
import se.liu.game.buttons.CreateMapButton;
import se.liu.game.buttons.MainMenuButton;
import se.liu.game.buttons.MapButton;
import se.liu.game.buttons.QuitButton;
import se.liu.game.game.GameEngine;
import se.liu.game.game.GameFrame;
import se.liu.game.buttons.PlayButton;
import se.liu.game.game.MapManager;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;


/**
 * Component that contains the main menu and all menu buttons, handling changing state in the frame.
 */
public class MenuComponent extends JComponent
{
    private final GameFrame gameFrame;
    private final GameEngine gameEngine;
    private final Dimension preferredSize;
    private final MapManager mapManager;

    @Override public Dimension getPreferredSize() {
	return preferredSize;
    }





    public void loadMapCreator(){
	gameFrame.loadEditor();
    }


    public void saveMap(GameMap gameMap) throws IOException {
	mapManager.writeToFile(gameMap);
    }

    public void startGameWithMap(GameMap gameMap){
	gameEngine.readFromMap(gameMap);
	gameFrame.loadGame(gameMap);

    }


    public void playButtonPressed(){
	removeAll();
	MainMenuButton mainMenuButton = new MainMenuButton(this, "BACK", Color.RED);
	this.add(new ChooseMapButton(this, "CHOOSE MAP", Color.green));
	this.add(new CreateMapButton(this, "CREATE NEW MAP", Color.yellow));
	this.add(mainMenuButton);
	revalidate();

    }

    public void chooseMap(){
	removeAll();
	mapManager.refreshMaps();
	for (int i = 0; i < mapManager.getGameMaps().size(); i++) {
	    MapButton mapButton = new MapButton(this, "GameMap " + (i+1), Color.green, mapManager.getGameMaps().get(i));
	    this.add(mapButton);
	}
	this.add(new MainMenuButton(this, "BACK", Color.red));
	revalidate();
    }

    public void loadMainMenu(){

	removeAll();
	PlayButton playButton = new PlayButton(this, "PLAY", Color.green);
	QuitButton quitButton = new QuitButton(this, "QUIT", Color.black);
	this.add(playButton);
	this.add(quitButton);
	revalidate();

    }
    public MenuComponent(GameEngine gameEngine, GameFrame gameFrame){
	super();
	this.gameEngine = gameEngine;
	mapManager = new MapManager();
	preferredSize = new Dimension(600, 600);
	this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
	this.gameFrame = gameFrame;
	loadMainMenu();




    }
}
