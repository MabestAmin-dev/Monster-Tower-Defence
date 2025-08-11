package se.liu.game.buttons;

import se.liu.game.components.MenuComponent;

import java.awt.*;
import java.awt.event.MouseEvent;

/**
 * Button to enter the choose map menu, calls chooseMap() method in menuComponent when pressed.
 */

public class ChooseMapButton extends AbstractMenuButton
{
    public ChooseMapButton(final MenuComponent menuComponent, final String name, final Color color) {
	super(menuComponent, name, color);

    }

    @Override public void mousePressed(final MouseEvent e) {
	menuComponent.chooseMap();
    }
}
