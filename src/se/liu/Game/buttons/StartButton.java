package se.liu.game.buttons;

import se.liu.game.game.GameEngine;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * In game button which will start the next round, is added to StoreComponent next to ExitButton
 */

public class StartButton extends JComponent implements MouseListener
{
    private final GameEngine gameEngine;
    private Font font = null;

    @Override protected void paintComponent(final Graphics g) {
	super.paintComponent(g);
	if(font == null) font = g.getFont().deriveFont(25.0f);
	g.setFont(font);
	g.setColor(Color.WHITE);
	g.fillRect(0, 0, getWidth(), getHeight());
	g.setColor(Color.BLACK);
	g.drawString("START", getWidth()/4, getHeight()/2);

    }

    public StartButton(final GameEngine gameEngine) {
	super();
	this.gameEngine = gameEngine;
	this.addMouseListener(this);
    }

    @Override public void mouseClicked(final MouseEvent mouseEvent) {

    }

    @Override public void mousePressed(final MouseEvent mouseEvent) {
	gameEngine.nextRound();
    }

    @Override public void mouseReleased(final MouseEvent mouseEvent) {

    }

    @Override public void mouseEntered(final MouseEvent mouseEvent) {

    }

    @Override public void mouseExited(final MouseEvent mouseEvent) {

    }
}
