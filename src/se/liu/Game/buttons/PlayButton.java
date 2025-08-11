package se.liu.game.buttons;

import se.liu.game.components.MenuComponent;

import java.awt.*;
import java.awt.event.MouseEvent;

/**
 * First button that appears when starting the game, will load part of menu where user can pick between choosing a map or creating one.
 */

public class PlayButton extends AbstractMenuButton
{
    public PlayButton(final MenuComponent menuComponent, String name, Color color) {
        super(menuComponent, name, color);
    }

    @Override public void mousePressed(final MouseEvent e) {
        menuComponent.playButtonPressed();
    }
}
