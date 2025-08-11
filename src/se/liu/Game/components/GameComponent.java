package se.liu.game.components;

import se.liu.game.game.GameEngine;
import se.liu.game.game.GridListener;
import se.liu.game.game.TileType;
import se.liu.game.monster.Monster;
import se.liu.game.tower.Tower;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Handles the actual visual representation of the game, also tracks mouse movement and presses on the main screen.
 *
 */

public class GameComponent extends JPanel implements MouseListener, GridListener, MouseMotionListener
{
    private final GameEngine gameEngine;
    final private static int DEFAULT_TOWER_SIZE = 40;
    final private static int DEFAULT_MONSTER_SIZE = 30;
    private final Dimension preferredSize;
    private final Point mousePos = new Point();
    final private static int BULLET_SIZE = 10;
    private Font font = null;
    final private static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    @Override public Dimension getPreferredSize() {
	return preferredSize;
    }

    /**
     * Relatively complex paintComponent, draws all placed towers and radii, all monsters, all bullets, currently placing tower which follows
     * the mouse pointer, updates via notifyListeners() in GameEngine
     * @param g
     */
    @Override protected void paintComponent(final Graphics g) {
	super.paintComponent(g);
	if(font == null) font = g.getFont().deriveFont(25.0f);
	g.setFont(font);
	for (int i = 0; i < gameEngine.getWidth(); i++) {
	    for (int j = 0; j < gameEngine.getHeight(); j++) {
		if(gameEngine.getTileAt(i, j) == TileType.FIELD) g.setColor(Color.GREEN);
		else if(gameEngine.getTileAt(i, j) == TileType.EDGE) g.setColor(new Color(64, 105, 13));
		else g.setColor(Color.yellow);

		g.fillRect(i * gameEngine.getTileSize(), j * gameEngine.getTileSize(), gameEngine.getTileSize(), gameEngine.getTileSize());

	    }
	}

	g.setColor(Color.blue);
	g.fillRect(gameEngine.getEndPos().x - gameEngine.getTileSize() / 2, gameEngine.getEndPos().y - gameEngine.getTileSize() / 2, gameEngine.getTileSize(), gameEngine.getTileSize());
	List<Tower> towers = gameEngine.getTowers();
	// For each loop causes problems if other thread removes tower during iteration, ignore warning
	for (int i = 0; i < towers.size(); i++) {
	    drawTower(g, towers.get(i), false, true);
	}

	for (int i = 0; i < gameEngine.getBullets().size(); i++) {
	    g.setColor(Color.black);
	    g.fillOval(gameEngine.getBullets().get(i).getPosition().x, gameEngine.getBullets().get(i).getPosition().y, BULLET_SIZE, BULLET_SIZE);
	}



	List<Monster> monsters = gameEngine.getMonsters();

	// For each loop causes problems if another thread removes a monster from the list during iteration
	for (int i = 0; i < monsters.size(); i++) {
	    g.setColor(monsters.get(i).getColor());
	    g.fillOval(monsters.get(i).getPosition().x-(DEFAULT_MONSTER_SIZE/2), monsters.get(i).getPosition().y-(DEFAULT_MONSTER_SIZE/2), DEFAULT_MONSTER_SIZE, DEFAULT_MONSTER_SIZE);
	    g.setColor(Color.black);
	    g.drawString(String.valueOf(monsters.get(i).getHealth()), monsters.get(i).getPosition().x-(DEFAULT_MONSTER_SIZE/2), monsters.get(i).getPosition().y-(DEFAULT_MONSTER_SIZE/2));

	}

	if(gameEngine.getPlacing() != null) {
	    drawTower(g, gameEngine.getPlacing(), true, false);
	}

	if(gameEngine.getUpgrading() != null){
	    g.setColor(Color.black);
	    g.drawOval(gameEngine.getUpgrading().getPosition().x - (gameEngine.getUpgrading().getRadius()), gameEngine.getUpgrading().getPosition().y - (gameEngine.getUpgrading().getRadius()),
		       gameEngine.getUpgrading().getRadius() * 2, gameEngine.getUpgrading().getRadius() * 2);
	}
	else{
	    for (int i = 0; i < gameEngine.getTowers().size(); i++) {
		if(isHitTower(mousePos, gameEngine.getTowers().get(i))){
		    drawRadius(g, gameEngine.getTowers().get(i), true);
		}
	    }
	}



    }


    private boolean isHitTower(Point point, Tower tower) {
	// Integer division is okay because precision is not that important here
	return point.x <= tower.getPosition().x + DEFAULT_TOWER_SIZE / 2 && point.x >= tower.getPosition().x - DEFAULT_TOWER_SIZE / 2 &&
	       point.x <= tower.getPosition().y + DEFAULT_TOWER_SIZE / 2 && point.x >= tower.getPosition().y - DEFAULT_TOWER_SIZE / 2;

    }

    // Boolean towerPos prevents crash in case tower has not been placed yet
    private void drawTower(Graphics g, Tower tower, boolean radius, boolean towerPos){
	g.setColor(tower.getColor());
	if(!towerPos)
	g.fillOval(mousePos.x - (DEFAULT_TOWER_SIZE / 2),
		   mousePos.y - (DEFAULT_TOWER_SIZE / 2), DEFAULT_TOWER_SIZE, DEFAULT_TOWER_SIZE);
	else g.fillOval(tower.getPosition().x - (DEFAULT_TOWER_SIZE / 2),
			tower.getPosition().y - (DEFAULT_TOWER_SIZE / 2), DEFAULT_TOWER_SIZE, DEFAULT_TOWER_SIZE);
	if(radius){
	    drawRadius(g, tower, towerPos);
	}

    }
    private void drawRadius(Graphics g, Tower tower, boolean towerPos){
	g.setColor(Color.black);
	if(towerPos)
	g.drawOval(tower.getPosition().x - (tower.getRadius()), tower.getPosition().y - (tower.getRadius()),
		   tower.getRadius() * 2, tower.getRadius() * 2);
	else g.drawOval(mousePos.x - (tower.getRadius()), mousePos.y - (tower.getRadius()),
			tower.getRadius() * 2, tower.getRadius() * 2);
    }
    public GameComponent(GameEngine gameEngine){
	super();
	this.gameEngine = gameEngine;
	//preferredSize = new Dimension(800, 600);
	this.addMouseListener(this);
	this.addMouseMotionListener(this);
	preferredSize = new Dimension(750, 750);
	gameEngine.addGridListener(this);

    }

    @Override public void mouseClicked(final MouseEvent e) {


    }


    /**
     * Tracks whether you pressed on a tower or where you placed a new tower
     * @param e
     */
    @Override public void mousePressed(final MouseEvent e) {



	boolean hit = false;
	for (int i = 0; i < gameEngine.getTowers().size(); i++) {
	    if(isHitTower(e.getPoint(), gameEngine.getTowers().get(i))){
		if(gameEngine.getPlacing() == null)gameEngine.setUpgrading(gameEngine.getTowers().get(i));
		hit = true;
	    }
	}
	if(!hit){
	    gameEngine.setUpgrading(null);
	}


	if(gameEngine.getPlacing() != null && !hit){
	    if (gameEngine.getTileAt(e.getX() / gameEngine.getTileSize(), e.getY() / gameEngine.getTileSize()) == TileType.FIELD) {
		gameEngine.getPlacing().setPosition(e.getPoint());
		gameEngine.addTower(gameEngine.getPlacing());
		gameEngine.setPlacing(null);
		LOGGER.log(Level.INFO, "Tower added");
	       // addingTower = 0;
	    }
	}
	else if(hit){
	    LOGGER.log(Level.WARNING, "Tried to overlap towers");
	}

	if(gameEngine.getPlacing() != null) {
	    Graphics g = getGraphics();
	    g.setColor(gameEngine.getPlacing().getColor());
	    g.fillOval(e.getX() - (DEFAULT_TOWER_SIZE / 2),
		       e.getY() - (DEFAULT_TOWER_SIZE / 2), DEFAULT_TOWER_SIZE, DEFAULT_TOWER_SIZE);
	    g.drawOval(e.getX() - (gameEngine.getPlacing().getRadius()),
		       e.getY() - (gameEngine.getPlacing().getRadius()), gameEngine.getPlacing().getRadius() * 2,
		       gameEngine.getPlacing().getRadius() * 2);
	}

    }

    @Override public void mouseReleased(final MouseEvent e) {

    }

    @Override public void mouseEntered(final MouseEvent e) {

    }

    @Override public void mouseExited(final MouseEvent e) {

    }

    @Override public void gridChanged() {
	repaint();
    }



    @Override public void mouseDragged(final MouseEvent e) {

    }

    @Override public void mouseMoved(final MouseEvent e) {
	mousePos.x = e.getX();
	mousePos.y = e.getY();
    }
}
