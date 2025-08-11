package se.liu.game.buttons;

import se.liu.game.components.MenuComponent;

import java.awt.*;
import java.awt.event.MouseEvent;

/**
 * Button that starts the map editor when pressed, extends AbstractMenuButton.
 */

public class CreateMapButton extends AbstractMenuButton
{
    public CreateMapButton(final MenuComponent menuComponent, final String name, final Color color) {
	super(menuComponent, name, color);
    }

    @Override public void mousePressed(final MouseEvent e) {
	menuComponent.loadMapCreator();
    }
}
