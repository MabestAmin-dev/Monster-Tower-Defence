package se.liu.game.buttons;

import se.liu.game.tower.TowerType;

/**
 * Checks if any of the storebuttons are being pressed and which tower this button represent
 */
public interface ButtonListener
{

    public void buttonPressed(TowerType towerType);


}
