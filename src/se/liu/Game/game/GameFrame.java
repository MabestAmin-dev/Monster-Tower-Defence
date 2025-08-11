package se.liu.game.game;

import se.liu.game.components.EditorComponent;
import se.liu.game.components.MainComponent;
import se.liu.game.components.MenuComponent;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * GameFrame extends JFrame and is the frame, contains methods for loading different components of the game.
 */
public class GameFrame extends JFrame
{
    private final GameEngine gameEngine;
    private MainComponent mainComponent;
    private final MenuComponent menuComponent;
    private GameMap gameMapFromEditor = null;
    private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);


    /**
     * Loads our game menu page, for example when the game is over or when we want to start a new game
     */
    public void loadMenu(){
	getContentPane().removeAll();
	this.add(menuComponent);
	this.pack();
	validate();


    }

    /**
     * Loads our map editor so that the player can create a new map by pressing on the button in the menu
     */
    public void loadEditor(){
	EditorComponent editorComponent = new EditorComponent();

	final Action action = new AbstractAction()
	{
	    @Override public void actionPerformed(final ActionEvent e) {
		if(JOptionPane.showConfirmDialog(null, "Save and finish map?", "", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION){
		    gameMapFromEditor = editorComponent.getMap();
		    try {
			menuComponent.saveMap(gameMapFromEditor);
		    }
		    catch(IOException ex){
			JOptionPane.showMessageDialog(null, "ERROR: FAILED TO SAVE MAP\nMap will be discarded");
			LOGGER.log(Level.SEVERE, "FAILED TO SAVE MAP", ex);
		    }
		    JOptionPane.showMessageDialog(null, "RETURNING TO MENU");
		    setJMenuBar(null);
		    loadMenu();
		}

	    }
	};

	JMenuBar editBar = new JMenuBar();
	JButton saveButton = new JButton("SAVE");
	saveButton.addActionListener(action);
	editBar.add(saveButton);

	getContentPane().removeAll();
	this.setJMenuBar(editBar);
	this.add(editorComponent);
	this.pack();
	validate();
    }

    public void loadGame(GameMap gameMap){
	getContentPane().removeAll();
	gameEngine.readFromMap(gameMap);
	mainComponent = new MainComponent(gameEngine);
	this.add(mainComponent, BorderLayout.CENTER);
	this.pack();
	validate();
    }

    public GameFrame(String name, GameEngine gameEngine){
	super(name);

	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	this.gameEngine = gameEngine;
	//initBar();

	mainComponent = new MainComponent(gameEngine);
	menuComponent = new MenuComponent(gameEngine, this);

	loadMenu();

    }
}
