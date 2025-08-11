package se.liu.game.components;

import se.liu.game.game.GameEngine;

import javax.swing.*;
import java.awt.*;

/**
 * Component that contains all other in-game components:
 * GameComponent, StoreComponent, StatusComponent, UpgradeComponent
 */

public class MainComponent extends JComponent
{

    private final GameEngine gameEngine;
    private Dimension preferredSize;



    @Override public Dimension getPreferredSize() {
	return preferredSize;
    }

    private void loadGameState(){
	GameComponent gameComponent = new GameComponent(gameEngine);
	StoreComponent storeComponent = new StoreComponent(gameEngine);
	StatusComponent statusComponent = new StatusComponent(gameEngine, new Dimension(
		gameComponent.getPreferredSize().width + storeComponent.getPreferredSize().width,
		storeComponent.getPreferredSize().height / 16));
	UpgradeComponent upgradeComponent = new UpgradeComponent(gameEngine, new Dimension(
		gameComponent.getPreferredSize().width + storeComponent.getPreferredSize().width,
		gameComponent.getPreferredSize().height / 6));

	this.setLayout(new BorderLayout());

	this.add(storeComponent, BorderLayout.EAST);
	this.add(statusComponent, BorderLayout.NORTH);
	this.add(upgradeComponent, BorderLayout.SOUTH);
	this.add(gameComponent, BorderLayout.CENTER);
	preferredSize = new Dimension(gameComponent.getPreferredSize().width + storeComponent.getPreferredSize().width,
				      gameComponent.getPreferredSize().height + upgradeComponent.getPreferredSize().height);
    }



    public MainComponent(GameEngine gameEngine){
	super();
	this.gameEngine = gameEngine;
	loadGameState();




    }
}
