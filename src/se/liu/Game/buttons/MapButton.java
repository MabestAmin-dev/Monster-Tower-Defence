package se.liu.game.buttons;

import se.liu.game.components.MenuComponent;
import se.liu.game.game.GameMap;

import java.awt.*;
import java.awt.event.MouseEvent;

/**
 * Will load a game with the given gameMap upon pressing, calls method startGameWithMap(GameMap gameMap) in menuComponent.
 */

public class MapButton extends AbstractMenuButton
{
    private final GameMap gameMap;
    public MapButton(final MenuComponent menuComponent, final String name, final Color color, GameMap gameMap) {
	super(menuComponent, name, color);
	this.gameMap = gameMap;
    }

    @Override public void mousePressed(final MouseEvent e) {
	menuComponent.startGameWithMap(gameMap);
    }
}
