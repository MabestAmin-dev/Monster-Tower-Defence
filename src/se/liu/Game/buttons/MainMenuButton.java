package se.liu.game.buttons;

import se.liu.game.components.MenuComponent;

import java.awt.*;
import java.awt.event.MouseEvent;

/**
 * Menu button that will return the menu to the first screen.
 */

public class MainMenuButton extends AbstractMenuButton
{
    public MainMenuButton(final MenuComponent menuComponent, final String name, final Color color) {
	super(menuComponent, name, color);
    }

    @Override public void mousePressed(final MouseEvent e) {
	menuComponent.loadMainMenu();
    }
}
