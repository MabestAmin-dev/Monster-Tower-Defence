package se.liu.game.buttons;

import se.liu.game.game.GameEngine;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.logging.Logger;

/**
 * the sell button which will remove the tower from the game and add money to the player
 */
public class SellTowerButton extends UpgradeButton
{
    final private static float REFUND_PERCENTAGE = 0.75F;


    public SellTowerButton(String description, GameEngine gameEngine){
	super(description, gameEngine);
    }

    /**
     * when pressing the sell button a confirmation panel will pop up and if pressed yes the tower will be removed and the player will get 75% of the towers cost
     */
    @Override public void mousePressed(final MouseEvent mouseEvent) {
	if(gameEngine.getUpgrading() != null){
	    if(JOptionPane.showConfirmDialog(this, "Are you sure you want to sell this tower?",
					     "Confirm", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION){
		gameEngine.addMoney((int) (gameEngine.getUpgrading().getCost() * REFUND_PERCENTAGE));
		gameEngine.removeTower(gameEngine.getUpgrading());
		gameEngine.setUpgrading(null);
		Logger.getLogger(Logger.GLOBAL_LOGGER_NAME).info("Tower sold for $" + gameEngine.getUpgrading().getCost());

	    }
	}
    }

    /**
     * If a tower is clicked on the button will be painted red otherwise stay grey
     */
    @Override public void gridChanged() {
	if(gameEngine.getUpgrading() != null){
	    setColor(Color.RED);
	}
	else setColor(Color.GRAY);
    }
}
