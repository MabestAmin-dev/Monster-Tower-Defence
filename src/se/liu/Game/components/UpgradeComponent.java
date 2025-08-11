package se.liu.game.components;

import se.liu.game.game.GameEngine;
import se.liu.game.buttons.SellTowerButton;
import se.liu.game.buttons.UpgradeButton;
import se.liu.game.tower.UpgradeType;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * At the bottom of the screen, contains buttons for upgrading and selling towers.
 */
public class UpgradeComponent extends JComponent implements MouseListener
{
    private final Dimension preferredSize;

    @Override public Dimension getPreferredSize() {
	return preferredSize;
    }



    public UpgradeComponent(final GameEngine gameEngine, Dimension preferredSize) {
	this.addMouseListener(this);
	this.preferredSize = preferredSize;
	this.setLayout(new GridLayout(1, 3, 0, 0));

	UpgradeButton upgradeDamage = new UpgradeButton("UPGRADE RANGE", gameEngine, UpgradeType.RANGE);
	UpgradeButton upgradeRange = new UpgradeButton("UPGRADE DAMAGE", gameEngine, UpgradeType.DAMAGE);
	SellTowerButton sellTowerButton = new SellTowerButton("SELL", gameEngine);
	this.add(upgradeDamage);
	this.add(upgradeRange);
	this.add(sellTowerButton);



    }


    @Override public void mouseClicked(final MouseEvent mouseEvent) {

    }

    @Override public void mousePressed(final MouseEvent mouseEvent) {

    }

    @Override public void mouseReleased(final MouseEvent mouseEvent) {

    }

    @Override public void mouseEntered(final MouseEvent mouseEvent) {

    }

    @Override public void mouseExited(final MouseEvent mouseEvent) {

    }
}
