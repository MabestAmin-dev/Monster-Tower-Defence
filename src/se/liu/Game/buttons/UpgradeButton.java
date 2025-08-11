package se.liu.game.buttons;

import se.liu.game.game.GameEngine;
import se.liu.game.game.GridListener;
import se.liu.game.tower.UpgradeType;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Button on the south component which when pressed on will upgrade the specific towers range or damage
 */
public class UpgradeButton extends JComponent implements MouseListener, GridListener
{
    private final String description;
    private Color color = Color.gray;
    protected GameEngine gameEngine;
    private final UpgradeType upgradeType;
    private final static int DIVIDE_WIDTH = 4;
    private final static int DIVIDE_HEIGHT = 2;
    private final static int UPGRADE_MULTIPLIER = 100;
    private Font font = null;


    @Override protected void paintComponent(final Graphics g) {
	super.paintComponent(g);
	g.setColor(color);
	g.fillRect(0,0,getWidth(), getHeight());
	if(font == null) font = g.getFont().deriveFont(25.0f);
	g.setFont(font);
	g.setColor(Color.black);
	g.drawRect(0,0,getWidth(), getHeight());
	g.drawString(description, getWidth()/DIVIDE_WIDTH, getHeight()/DIVIDE_HEIGHT);
    }


    // SellTowerButton extends this class, solve this with constructor overloading
    public UpgradeButton(String description, GameEngine gameEngine){
	this.description = description;
	this.gameEngine = gameEngine;
	this.upgradeType = null;
	this.addMouseListener(this);
	gameEngine.addGridListener(this);
    }


    public UpgradeButton(String description, GameEngine gameEngine, UpgradeType upgradeType) {
	this.description = description;
	this.gameEngine = gameEngine;
	this.upgradeType = upgradeType;
	this.addMouseListener(this);
	gameEngine.addGridListener(this);
    }


    public void setColor(Color color){
	this.color = color;
	repaint();
    }

    @Override public void mouseClicked(final MouseEvent e) {

    }

    /**
     * if the button is pressed on it will upgrade the tower whether the damage or the range button was clicked on
     * @param e
     */
    @Override public void mousePressed(final MouseEvent e) {
	//System.out.println("UPGRADE BUTTON PRESSED");
	if(gameEngine.getUpgrading() != null){
	    switch(upgradeType){
		case RANGE -> {
		    //.out.println("Try range");
		    if(gameEngine.getUpgrading().getRangeLevel() * UPGRADE_MULTIPLIER <= gameEngine.getMoney()){
			gameEngine.subtractMoney(gameEngine.getUpgrading().getRangeLevel() * UPGRADE_MULTIPLIER);
			gameEngine.getUpgrading().upgradeRange();
		    }
		}
		case DAMAGE -> {
		    //System.out.println("Try damage");
		    if(gameEngine.getUpgrading().getDamageLevel() * UPGRADE_MULTIPLIER <= gameEngine.getMoney()){
			gameEngine.subtractMoney(gameEngine.getUpgrading().getDamageLevel() * UPGRADE_MULTIPLIER);
			gameEngine.getUpgrading().upgradeDamage();

		    }
		}
	    }
	}
    }

    @Override public void mouseReleased(final MouseEvent e) {

    }

    @Override public void mouseEntered(final MouseEvent e) {

    }

    @Override public void mouseExited(final MouseEvent e) {

    }

    /**
     * if a tower is selected and you have enough money the buttons will be green, otherwise gray
     * uses gridChanged to update the status
     */
    @Override public void gridChanged() {
	if(gameEngine.getUpgrading() != null){
	    switch(upgradeType){
		case DAMAGE -> {
		    if(gameEngine.getUpgrading().getDamageLevel() * UPGRADE_MULTIPLIER <= gameEngine.getMoney()){
			setColor(Color.green);
		    }
		    else setColor(Color.gray);
		}
		case RANGE -> {
		    if(gameEngine.getUpgrading().getRangeLevel() * UPGRADE_MULTIPLIER <= gameEngine.getMoney()){
			setColor(Color.green);
		    }
		    else setColor(Color.gray);
		}
	    }
	}
	else setColor(Color.gray);
    }
}
