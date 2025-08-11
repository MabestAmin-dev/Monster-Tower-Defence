package se.liu.game.components;

import se.liu.game.game.GameEngine;
import se.liu.game.game.GridListener;

import javax.swing.*;
import java.awt.*;

/**
 * Shows current base health, round and money on the top of the frame
 */

public class StatusComponent extends JComponent implements GridListener
{
    private final Dimension preferredSize;
    private final GameEngine gameEngine;
    private Font font = null;

    @Override public Dimension getPreferredSize() {
	return preferredSize;
    }

    @Override protected void paintComponent(final Graphics g) {
	super.paintComponent(g);
	if(font == null) font = g.getFont().deriveFont(25.0f);
	g.setFont(font);
	g.drawRect(0,0, (int)preferredSize.getWidth(), (int)preferredSize.getHeight());
	g.setColor(Color.black);
	g.drawString("$" + gameEngine.getMoney(), 0, (int)preferredSize.getHeight() / 2);
	g.drawString("\u2661" + gameEngine.getBaseHealth(), (int)preferredSize.getWidth() / 4, (int)preferredSize.getHeight() / 2);
	g.drawString("\u23F0" + gameEngine.getRound(), (int)preferredSize.getWidth() / 2, (int)preferredSize.getHeight() / 2);
    }

    public StatusComponent(GameEngine gameEngine, final Dimension preferredSize) {
	this.preferredSize = preferredSize;
	this.gameEngine = gameEngine;
	gameEngine.addGridListener(this);

    }

    @Override public void gridChanged() {
	repaint();
    }
}
