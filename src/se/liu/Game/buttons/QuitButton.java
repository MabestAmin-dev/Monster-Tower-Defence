package se.liu.game.buttons;

import se.liu.game.components.MenuComponent;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.logging.Logger;

/**
 * Will terminate the program completely by using System.exit(0)
 */

public class QuitButton extends AbstractMenuButton
{
    public QuitButton(final MenuComponent menuComponent, final String name, final Color color) {
	super(menuComponent, name, color);
    }

    @Override public void mousePressed(final MouseEvent e) {
	Logger.getLogger(Logger.GLOBAL_LOGGER_NAME).info("Exiting game");
	System.exit(0);
    }
}
