package se.liu.game.buttons;

import se.liu.game.game.GridListener;
import se.liu.game.tower.TowerType;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Each store button corresponds to a tower you can purchase in the game, appears in the right side of the screen in StoreComponent
 */
public class StoreButton extends JPanel implements MouseListener, GridListener
{
    private final Color color;
    private final TowerType towerType;
    private final ButtonListener buttonListener;
    private final String towerButton;
    private final String showMoney;
    private final static int DIVIDE_WIDTH_1 = 4;
    private final static int DIVIDE_HEIGHT_1 = 2;
    private final static int DIVIDE_WIDTH_2 = 8;
    private final static int DIVIDE_HEIGHT_2 = 6;

    private final Font smallFont;
    private final Font bigFont;

    /**
     * paints the buttons depending on which tower using the enum TowerType
     * @param g
     */
    @Override protected void paintComponent(final Graphics g) {
	super.paintComponent(g);
	g.setFont(bigFont);
	g.setColor(color);
	g.fillRect(0,0,getWidth(), getHeight());
	g.setColor(Color.black);
	g.drawString(towerButton, getWidth()/DIVIDE_WIDTH_1, getHeight()/DIVIDE_HEIGHT_1);
	g.setFont(smallFont);
	g.setColor(Color.black);
	g.drawString(showMoney, getWidth()/DIVIDE_WIDTH_2, getHeight()/DIVIDE_HEIGHT_2);


    }


    public StoreButton(TowerType towerType, ButtonListener buttonListener, String towerButton, String showMoney, Color color){
	this.towerType = towerType;
	this.buttonListener = buttonListener;
	this.towerButton = towerButton;
	this.showMoney = showMoney;
	this.color = color;
	this.smallFont = getFont().deriveFont(16.0f);	// Font sizes we want for our button
	this.bigFont = getFont().deriveFont(25.0f);

	this.addMouseListener(this);
    }


    @Override public void mouseClicked(final MouseEvent mouseEvent) {

    }

    @Override public void mousePressed(final MouseEvent mouseEvent) {
	buttonListener.buttonPressed(towerType);
    }

    @Override public void mouseReleased(final MouseEvent mouseEvent) {

    }

    @Override public void mouseEntered(final MouseEvent mouseEvent) {

    }

    @Override public void mouseExited(final MouseEvent mouseEvent) {

    }


    @Override public void gridChanged() {
	repaint();
    }
}
