package se.liu.game.buttons;

import se.liu.game.game.GameEngine;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * In game button that ends the current game correctly and returns to main menu. In practice like losing the game.
 */

public class ExitButton extends JComponent implements MouseListener
{
    private final GameEngine gameEngine;
    private Font font = null;

    @Override protected void paintComponent(final Graphics g) {
	super.paintComponent(g);
	if(font == null) font = g.getFont().deriveFont(25.0f);
	g.setFont(font);
	g.setColor(Color.BLACK);
	g.fillRect(0, 0, getWidth(), getHeight());
	g.setColor(Color.WHITE);
	g.drawString("EXIT", getWidth()/4, getHeight()/2);

    }

    public ExitButton(final GameEngine gameEngine) {
	this.gameEngine = gameEngine;
	this.addMouseListener(this);
    }

    @Override public void mouseClicked(final MouseEvent mouseEvent) {

    }

    @Override public void mousePressed(final MouseEvent mouseEvent) {
	gameEngine.endGame();
    }

    @Override public void mouseReleased(final MouseEvent mouseEvent) {

    }

    @Override public void mouseEntered(final MouseEvent mouseEvent) {

    }

    @Override public void mouseExited(final MouseEvent mouseEvent) {

    }
}

