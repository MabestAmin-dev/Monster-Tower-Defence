package se.liu.game.components;

import se.liu.game.buttons.ButtonListener;
import se.liu.game.buttons.ExitButton;
import se.liu.game.game.GameEngine;
import se.liu.game.buttons.StartButton;
import se.liu.game.buttons.StoreButton;
import se.liu.game.tower.TowerType;
import se.liu.game.tower.BloodTower;
import se.liu.game.tower.FireTower;
import se.liu.game.tower.IceTower;
import se.liu.game.tower.PoisonTower;
import se.liu.game.tower.StormTower;
import se.liu.game.tower.SunTower;

import javax.swing.*;
import java.awt.*;
import java.util.EnumMap;
import java.util.Map;

/**
 * Contains all store buttons from which you can purchase towers, is on the right side of the in-game screen
 */

public class StoreComponent extends JPanel implements ButtonListener
{
    private final Dimension preferredSize;
    private final GameEngine gameEngine;
    private final Map<TowerType, Integer> prices = new EnumMap<>(TowerType.class);


    @Override public Dimension getPreferredSize() {
	return preferredSize;
    }

    /**
     * Constructor that creates the storebuttons which are one the right side of the game
     * @param gameEngine
     */
    public StoreComponent(GameEngine gameEngine){
	super();
	prices.put(TowerType.FIRE_TOWER, new FireTower(null).getCost());
	prices.put(TowerType.ICE_TOWER, new IceTower(null).getCost());
	prices.put(TowerType.STORM_TOWER, new StormTower(null).getCost());
	prices.put(TowerType.POISON_TOWER, new PoisonTower(null).getCost());
	prices.put(TowerType.SUN_TOWER, new SunTower(null).getCost());
	prices.put(TowerType.BLOOD_TOWER, new BloodTower(null).getCost());

	this.gameEngine = gameEngine;
	this.setLayout(new GridLayout(4,2,0,0));
	preferredSize = new Dimension(300, 750);

	this.add(new StoreButton(TowerType.FIRE_TOWER, this, "FIRE", "COST: " + "$" + prices.get(TowerType.FIRE_TOWER), new Color(247, 136, 33)));
	this.add(new StoreButton(TowerType.ICE_TOWER, this, "ICE", "COST: " + "$" + prices.get(TowerType.ICE_TOWER), new Color(29, 205, 227)));
	this.add(new StoreButton(TowerType.STORM_TOWER, this, "STORM", "COST: " + "$" + prices.get(TowerType.STORM_TOWER), new Color(88, 111, 156)));
	this.add(new StoreButton(TowerType.POISON_TOWER, this, "POISON", "COST: " + "$" + prices.get(TowerType.POISON_TOWER), new Color(121, 225, 79)));
	this.add(new StoreButton(TowerType.BLOOD_TOWER, this, "BLOOD", "COST: " + "$" + prices.get(TowerType.BLOOD_TOWER), new Color(229, 71, 74)));
	this.add(new StoreButton(TowerType.SUN_TOWER, this, "SUN", "COST: " + "$" + prices.get(TowerType.SUN_TOWER), new Color(240, 214, 65 )));
	this.add(new StartButton(gameEngine));
	this.add(new ExitButton(gameEngine));

    }


    /**
     * implement method from buttonlistener that selects which button that is pressed using the enum TowerType
     * @param towerType
     */
    @Override public void buttonPressed(final TowerType towerType) {
	switch(towerType){
	    case FIRE_TOWER:
		if(prices.get(TowerType.FIRE_TOWER) <= gameEngine.getMoney()){
		    gameEngine.setPlacing(new FireTower(null));
		    gameEngine.subtractMoney(prices.get(TowerType.FIRE_TOWER));
		}
		break;
	    case ICE_TOWER:
		if(prices.get(TowerType.ICE_TOWER) <= gameEngine.getMoney()){
		    gameEngine.setPlacing(new IceTower(null));
		    gameEngine.subtractMoney(prices.get(TowerType.ICE_TOWER));
		}
		break;
	    case STORM_TOWER:
		if(prices.get(TowerType.STORM_TOWER) <= gameEngine.getMoney()){
		    gameEngine.setPlacing(new StormTower(null));
		    gameEngine.subtractMoney(prices.get(TowerType.STORM_TOWER));
		}
		break;
	    case POISON_TOWER:
		if(prices.get(TowerType.POISON_TOWER) <= gameEngine.getMoney()){
		    gameEngine.setPlacing(new PoisonTower(null));
		    gameEngine.subtractMoney(prices.get(TowerType.POISON_TOWER));
		}
		break;
	    case BLOOD_TOWER:
		if(prices.get(TowerType.BLOOD_TOWER) <= gameEngine.getMoney()){
		    gameEngine.setPlacing(new BloodTower(null));
		    gameEngine.subtractMoney(prices.get(TowerType.BLOOD_TOWER));
		}
		break;
	    case SUN_TOWER:
		if(prices.get(TowerType.SUN_TOWER) <= gameEngine.getMoney()){
		    gameEngine.setPlacing(new SunTower(null));
		    gameEngine.subtractMoney(prices.get(TowerType.SUN_TOWER));
		}
		break;
	}
    }

}
