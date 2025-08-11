package se.liu.game.components;

import se.liu.game.monster.Direction;
import se.liu.game.game.GameMap;
import se.liu.game.game.TileType;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

/**
 * EditorComponent is a map editor that allows the user to place tiles and create and save their own map which is then saved to a file.
 * Implements MouseListener and MouseMotionListener to track the users mouse movement and presses.
 */
public class EditorComponent extends JComponent implements MouseListener, MouseMotionListener
{

    private final GameMap gameMap = new GameMap();

    private final Point roundedMousePos = new Point();
    private Direction lastDirection = null;
    private int placedSinceTurn = 0;


    private final Dimension preferredSize;




    public GameMap getMap(){

        switch(lastDirection){
            /*
            About warning:
            Duplicated code here is kinda complicated to fix without doing some kind of if-else with an or statement, that would make it
            even harder to read than it already is. Doing it in this hardcoded way probably wasn't the best, but it was the solution we
            thought of given the limited time left.
            In general, this code wraps the road around the end point when you save the map to make it.

             */
            case UP -> {
                gameMap.addTileAt(TileType.EDGE, gameMap.getEndPos().x - 2, gameMap.getEndPos().y - 2);
                gameMap.addTileAt(TileType.EDGE, gameMap.getEndPos().x - 1, gameMap.getEndPos().y - 2);
                gameMap.addTileAt(TileType.EDGE, gameMap.getEndPos().x, gameMap.getEndPos().y - 2);
                gameMap.addTileAt(TileType.EDGE, gameMap.getEndPos().x + 1, gameMap.getEndPos().y - 2);
                gameMap.addTileAt(TileType.EDGE, gameMap.getEndPos().x - 2, gameMap.getEndPos().y - 1);
                gameMap.addTileAt(TileType.EDGE, gameMap.getEndPos().x - 2, gameMap.getEndPos().y);
                gameMap.addTileAt(TileType.EDGE, gameMap.getEndPos().x + 1, gameMap.getEndPos().y - 1);
                gameMap.addTileAt(TileType.EDGE, gameMap.getEndPos().x + 1, gameMap.getEndPos().y);


            }
            case DOWN -> {
                gameMap.addTileAt(TileType.EDGE, gameMap.getEndPos().x - 2, gameMap.getEndPos().y + 1);
                gameMap.addTileAt(TileType.EDGE, gameMap.getEndPos().x - 1, gameMap.getEndPos().y + 1);
                gameMap.addTileAt(TileType.EDGE, gameMap.getEndPos().x, gameMap.getEndPos().y + 1);
                gameMap.addTileAt(TileType.EDGE, gameMap.getEndPos().x + 1, gameMap.getEndPos().y + 1);
                gameMap.addTileAt(TileType.EDGE, gameMap.getEndPos().x - 2, gameMap.getEndPos().y);
                gameMap.addTileAt(TileType.EDGE, gameMap.getEndPos().x - 2, gameMap.getEndPos().y - 1);
                gameMap.addTileAt(TileType.EDGE, gameMap.getEndPos().x + 1, gameMap.getEndPos().y);
                gameMap.addTileAt(TileType.EDGE, gameMap.getEndPos().x + 1, gameMap.getEndPos().y - 1);

            }
            case LEFT -> {
                gameMap.addTileAt(TileType.EDGE, gameMap.getEndPos().x - 2, gameMap.getEndPos().y - 2);
                gameMap.addTileAt(TileType.EDGE, gameMap.getEndPos().x - 2, gameMap.getEndPos().y - 1);
                gameMap.addTileAt(TileType.EDGE, gameMap.getEndPos().x - 2, gameMap.getEndPos().y);
                gameMap.addTileAt(TileType.EDGE, gameMap.getEndPos().x - 2, gameMap.getEndPos().y + 1);
                gameMap.addTileAt(TileType.EDGE, gameMap.getEndPos().x, gameMap.getEndPos().y - 2);
                gameMap.addTileAt(TileType.EDGE, gameMap.getEndPos().x - 1, gameMap.getEndPos().y - 2);
                gameMap.addTileAt(TileType.EDGE, gameMap.getEndPos().x, gameMap.getEndPos().y + 1);
                gameMap.addTileAt(TileType.EDGE, gameMap.getEndPos().x - 1, gameMap.getEndPos().y + 1);
            }

            case RIGHT -> {
                gameMap.addTileAt(TileType.EDGE, gameMap.getEndPos().x + 1, gameMap.getEndPos().y - 2);
                gameMap.addTileAt(TileType.EDGE, gameMap.getEndPos().x + 1, gameMap.getEndPos().y - 1);
                gameMap.addTileAt(TileType.EDGE, gameMap.getEndPos().x + 1, gameMap.getEndPos().y);
                gameMap.addTileAt(TileType.EDGE, gameMap.getEndPos().x + 1, gameMap.getEndPos().y + 1);
                gameMap.addTileAt(TileType.EDGE, gameMap.getEndPos().x, gameMap.getEndPos().y - 2);
                gameMap.addTileAt(TileType.EDGE, gameMap.getEndPos().x - 1, gameMap.getEndPos().y - 2);
                gameMap.addTileAt(TileType.EDGE, gameMap.getEndPos().x, gameMap.getEndPos().y + 1);
                gameMap.addTileAt(TileType.EDGE, gameMap.getEndPos().x - 1, gameMap.getEndPos().y + 1);
            }




        }


        // Convert the coordinates to absolute coordinates instead of Tile coordinates
        gameMap.setStartPos(new Point(gameMap.getStartPos().x * gameMap.getTileSize(), gameMap.getStartPos().y * gameMap.getTileSize()));
        gameMap.setEndPos(new Point(gameMap.getEndPos().x * gameMap.getTileSize(), gameMap.getEndPos().y * gameMap.getTileSize()));

        for (int i = 0; i < gameMap.getTurnPoints().size(); i++) {
            gameMap.setTurnPointAt(i, new Point(gameMap.getTurnPoints().get(i).x * gameMap.getTileSize(), gameMap.getTurnPoints().get(i).y *
                                                                                                          gameMap.getTileSize()));
        }
        return this.gameMap;
    }

    private void placeBlock(int x, int y){
        for (int i = x - 1; i < x + 1; i++) {
            for (int j = y - 1; j < y + 1; j++) {


                gameMap.addTileAt(TileType.ROAD, i, j);

            }
        }


        for (int i = gameMap.getEndPos().x - 2; i < gameMap.getEndPos().x + 2; i++) {
            for (int j = gameMap.getEndPos().y - 2; j < gameMap.getEndPos().y + 2; j++) {

                if(lastDirection == Direction.UP || lastDirection == Direction.DOWN){
                    if((i == gameMap.getEndPos().x - 2 || i == gameMap.getEndPos().x + 1) && j >= gameMap.getEndPos().y - 1 && j < gameMap.getEndPos().y + 1){
                        gameMap.addTileAt(TileType.EDGE, i, j);
                    }
                }
                else if(lastDirection == Direction.LEFT || lastDirection == Direction.RIGHT){
                    if((j == gameMap.getEndPos().y - 2 || j == gameMap.getEndPos().y + 1) && i >= gameMap.getEndPos().x - 1 && i < gameMap.getEndPos().x + 1){
                        gameMap.addTileAt(TileType.EDGE, i, j);
                    }
                }
                else if(gameMap.getDirections().size() < 1){
                    if(gameMap.getStartPos().x == 1 || 1 == (gameMap.getWidth() - gameMap.getStartPos().x)){
                        if((j == gameMap.getEndPos().y - 2 || j == gameMap.getEndPos().y + 1) && i >= gameMap.getEndPos().x - 1 && i < gameMap.getEndPos().x + 1){
                            gameMap.addTileAt(TileType.EDGE, i, j);
                        }
                    }
                    else{
                        if((i == gameMap.getEndPos().x - 2 || i == gameMap.getEndPos().x + 1) && j >= gameMap.getEndPos().y - 1 && j < gameMap.getEndPos().y + 1){
                            gameMap.addTileAt(TileType.EDGE, i, j);
                        }
                    }

                }



            }
        }
    }
    @Override protected void paintComponent(final Graphics g) {
        super.paintComponent(g);

        for (int i = 0; i < gameMap.getWidth(); i++) {
            for (int j = 0; j < gameMap.getWidth(); j++) {
                if(gameMap.getTileAt(i, j) == TileType.FIELD) g.setColor(Color.green);
                else if(gameMap.getTileAt(i, j) == TileType.ROAD) g.setColor(Color.yellow);
                else if(gameMap.getTileAt(i, j) == TileType.EDGE) g.setColor(new Color(48, 77, 13, 255));
                g.fillRect(i * gameMap.getTileSize(), j * gameMap.getTileSize(), gameMap.getTileSize(), gameMap.getTileSize());

            }
        }

        g.setColor(Color.yellow);
        g.fillRect(roundedMousePos.x - gameMap.getTileSize(), roundedMousePos.y - gameMap.getTileSize(), gameMap.getTileSize(), gameMap.getTileSize());
        g.fillRect(roundedMousePos.x, roundedMousePos.y - gameMap.getTileSize(), gameMap.getTileSize(), gameMap.getTileSize());
        g.fillRect(roundedMousePos.x - gameMap.getTileSize(), roundedMousePos.y, gameMap.getTileSize(), gameMap.getTileSize());
        g.fillRect(roundedMousePos.x, roundedMousePos.y, gameMap.getTileSize(), gameMap.getTileSize());


        if(gameMap.getStartPos() != null){
            g.setColor(Color.blue);
            g.drawOval(gameMap.getStartPos().x * gameMap.getTileSize() - gameMap.getTileSize() / 2, gameMap.getStartPos().y *
                                                                                                    gameMap.getTileSize() - gameMap.getTileSize() / 2, gameMap.getTileSize(), gameMap.getTileSize());
        }
        if(gameMap.getEndPos() != null && !gameMap.getEndPos().equals(gameMap.getStartPos())) {
            g.fillRect(gameMap.getEndPos().x * gameMap.getTileSize() - gameMap.getTileSize() / 2, gameMap.getEndPos().y *
                                                                                                  gameMap.getTileSize() - gameMap.getTileSize() / 2, gameMap.getTileSize(), gameMap.getTileSize());
        }

        for (int i = 0; i < gameMap.getTurnPoints().size(); i++){
            g.setColor(Color.blue);
            g.drawOval(gameMap.getTurnPoints().get(i).x * gameMap.getTileSize() - gameMap.getTileSize() / 2, gameMap.getTurnPoints().get(i).y *
                                                                                                             gameMap.getTileSize() - gameMap.getTileSize() / 2, gameMap.getTileSize(), gameMap.getTileSize());
        }

    }


    /*
      This is a special method that makes sure the edge is placed correctly depending on the way you turn
      this was necessary to hardcode due to how we programmed the game, largely hardcoded and definitely not the cleanest solution.
      The "magical" numbers are relative tiles compared to the coordinate that was clicked on.
     */

    private void compensateForTurn(Direction direction, int x, int y){

        switch(direction){
            case UP -> {


                if(lastDirection == Direction.RIGHT) {
                    gameMap.addTileAt(TileType.ROAD, x - 1, y);
                    gameMap.addTileAt(TileType.ROAD, x, y);
                    gameMap.addTileAt(TileType.EDGE, x + 1, y);
                    gameMap.addTileAt(TileType.EDGE, x + 1, y + 1);
                    gameMap.addTileAt(TileType.EDGE, x + 1, y + 2);
                    gameMap.addTileAt(TileType.EDGE, x + 1, y + 3);

                }
                else if(lastDirection == Direction.LEFT){
                    gameMap.addTileAt(TileType.ROAD, x - 1, y);
                    gameMap.addTileAt(TileType.ROAD, x, y);
                    gameMap.addTileAt(TileType.EDGE, x - 2, y);
                    gameMap.addTileAt(TileType.EDGE, x - 2, y + 1);
                    gameMap.addTileAt(TileType.EDGE, x - 2, y + 2);
                    gameMap.addTileAt(TileType.EDGE, x - 2, y + 3);


                }

            }

            case RIGHT -> {

                    if(lastDirection == Direction.UP){
                        gameMap.addTileAt(TileType.ROAD, x - 1, y - 1);
                        gameMap.addTileAt(TileType.ROAD, x - 1, y);
                        gameMap.addTileAt(TileType.EDGE, x - 1, y - 2);
                        gameMap.addTileAt(TileType.EDGE, x - 2, y - 2);
                        gameMap.addTileAt(TileType.EDGE, x - 3, y - 2);
                        gameMap.addTileAt(TileType.EDGE, x - 4, y - 2);
                    }
                    else if(lastDirection == Direction.DOWN){
                        gameMap.addTileAt(TileType.ROAD, x - 1, y - 1);
                        gameMap.addTileAt(TileType.ROAD, x - 1, y);
                        gameMap.addTileAt(TileType.EDGE, x - 1, y + 1);
                        gameMap.addTileAt(TileType.EDGE, x - 2, y + 1);
                        gameMap.addTileAt(TileType.EDGE, x - 3, y + 1);
                        gameMap.addTileAt(TileType.EDGE, x - 4, y + 1);

                    }

            }

            case DOWN -> {



                if(lastDirection == Direction.RIGHT) {
                    gameMap.addTileAt(TileType.ROAD, x - 1, y - 1);
                    gameMap.addTileAt(TileType.ROAD, x, y - 1);
                    gameMap.addTileAt(TileType.EDGE, x + 1, y - 1);
                    gameMap.addTileAt(TileType.EDGE, x + 1, y - 2);
                    gameMap.addTileAt(TileType.EDGE, x + 1, y - 3);
                    gameMap.addTileAt(TileType.EDGE, x + 1, y - 4);
                }

                else if(lastDirection == Direction.LEFT){
                    gameMap.addTileAt(TileType.ROAD, x - 1, y - 1);
                    gameMap.addTileAt(TileType.ROAD, x, y - 1);
                    gameMap.addTileAt(TileType.EDGE, x - 2, y - 1);
                    gameMap.addTileAt(TileType.EDGE, x - 2, y - 2);
                    gameMap.addTileAt(TileType.EDGE, x - 2, y - 3);
                    gameMap.addTileAt(TileType.EDGE, x - 2, y - 4);
                }
            }

            case LEFT -> {

                if(lastDirection == Direction.UP){
                    gameMap.addTileAt(TileType.ROAD, x, y - 1);
                    gameMap.addTileAt(TileType.ROAD, x, y);
                    gameMap.addTileAt(TileType.EDGE, x, y - 2);
                    gameMap.addTileAt(TileType.EDGE, x + 1, y - 2);
                    gameMap.addTileAt(TileType.EDGE, x + 2, y - 2);
                    gameMap.addTileAt(TileType.EDGE, x + 3, y - 2);
                }
                else if(lastDirection == Direction.DOWN){
                    gameMap.addTileAt(TileType.ROAD, x, y - 1);
                    gameMap.addTileAt(TileType.ROAD, x, y);
                    gameMap.addTileAt(TileType.EDGE, x, y + 1);
                    gameMap.addTileAt(TileType.EDGE, x + 1 , y + 1);
                    gameMap.addTileAt(TileType.EDGE, x + 2, y + 1);
                    gameMap.addTileAt(TileType.EDGE, x + 3, y + 1);

                }


            }



        }



    }

    @Override public void mouseClicked(final MouseEvent e) {

    }

    @Override public void mousePressed(final MouseEvent e) {
        int x = Math.round((float) e.getX() / gameMap.getTileSize());
        int y = Math.round((float) e.getY() / gameMap.getTileSize());


        for (int i = 0; i < gameMap.getTurnPoints().size(); i++) {
            if(gameMap.getTurnPoints().get(i).x == x && gameMap.getTurnPoints().get(i).y == y){
                JOptionPane.showMessageDialog(this, "Don't overlap waypoints!");
                return;
            }
        }
        if (gameMap.getStartPos() == null) {
            if (x == 1 || 1 == (gameMap.getWidth() - x) || y == 1 || 1 == (gameMap.getWidth() - y)) {


                gameMap.setStartPos(new Point(x, y));
                gameMap.setEndPos(new Point(x, y));
                placeBlock(x, y);


            }
        }


        if (gameMap.getEndPos() != null && ((x == gameMap.getEndPos().x) && (Math.abs(y - gameMap.getEndPos().y) == 2) ||
                                            ((y == gameMap.getEndPos().y) && (Math.abs(x - gameMap.getEndPos().x) == 2)))) {

            if (x <= 1 || 1 >= (gameMap.getWidth() - x) || y <= 1 || 1 >= (gameMap.getWidth() - y)) {
                JOptionPane.showMessageDialog(this, "Too close to wall!");
            } else {
                Direction direction = null;
                if (gameMap.getEndPos().x == x) {
                    if (gameMap.getEndPos().y < y) {
                        direction = Direction.DOWN;
                    } else if (gameMap.getEndPos().y > y) {
                        direction = Direction.UP;
                    }
                }
                else if (gameMap.getEndPos().y == y) {
                    if (gameMap.getEndPos().x < x) {
                        direction = Direction.RIGHT;
                    } else if (gameMap.getEndPos().x > x) {
                        direction = Direction.LEFT;
                    }
                }
                if (direction != lastDirection && direction != null) {
                    if(placedSinceTurn < 1 && gameMap.getTurnPoints().size() >= 1){
                        JOptionPane.showMessageDialog(this, "Turn not allowed here!");
                        return;
                    }
                    if((lastDirection == Direction.DOWN && direction == Direction.UP) ||
                       (lastDirection == Direction.UP && direction == Direction.DOWN) ||
                       (lastDirection == Direction.LEFT && direction == Direction.RIGHT) ||
                       (lastDirection == Direction.RIGHT && direction == Direction.LEFT)) {
                        JOptionPane.showMessageDialog(this, "You cannot turn back!");
                        return;
                    }

                    gameMap.addTurnPoint(gameMap.getEndPos(), direction);
                    placeBlock(x, y);
                    if(gameMap.getDirections().size() > 1) compensateForTurn(direction, x, y);
                    placedSinceTurn = 0;
                }

                else {
                    placeBlock(x, y);
                    placedSinceTurn++;
                }
                gameMap.setEndPos(new Point(x, y));
                lastDirection = direction;


            }
        }
    }

    @Override public void mouseReleased(final MouseEvent e) {

    }

    @Override public void mouseEntered(final MouseEvent e) {

    }

    @Override public void mouseExited(final MouseEvent e) {

    }

    @Override public void mouseDragged(final MouseEvent e) {

    }

    @Override public void mouseMoved(final MouseEvent e) {
        final float tileSize = gameMap.getTileSize();
        roundedMousePos.x = (int) (Math.round(e.getX() / tileSize) * tileSize);
        roundedMousePos.y = (int) (Math.round(e.getY() / tileSize) * tileSize);

        repaint();
    }


    @Override public Dimension getPreferredSize() {
        return preferredSize;
    }

    public EditorComponent() {
        super();
        this.preferredSize = new Dimension(750, 750);
        for (int i = 0; i < gameMap.getWidth(); i++) {
            for (int j = 0; j < gameMap.getWidth(); j++) {
                gameMap.addTileAt(TileType.FIELD, i, j);
            }
        }
        this.addMouseListener(this);
        this.addMouseMotionListener(this);

    }


}
