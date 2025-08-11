package se.liu.game.game;

import se.liu.game.monster.Direction;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;


/**
 * Contains the 2D array for the tiles, which the game map is built from. Is loaded from file via MapManager.
 */
public class GameMap
{
    //private HashMap<Point, Direction> turnPoints;
    private final List<Point> turnPoints;
    private final List<Direction> directions;
    private final TileType[][] tileTypes;
    private final int height;
    private final int width;
    final private static int TILE_SIZE = 25;
    private Point startPos = null;
    private Point endPos = null;
    final private static int DEFAULT_MAP_SIZE = 30;





    public void addTurnPoint(Point point, Direction direction){
        turnPoints.add(point);
        directions.add(direction);
    }

    public void addTileAt(TileType tileType, int x, int y){
        tileTypes[y][x] = tileType;
    }

    public Point getStartPos() {
        if(startPos == null) return null;
        return new Point(startPos);
    }

    public Point getEndPos() {
        if(endPos == null) return null;

        return new Point(endPos);
    }

    public void setStartPos(final Point startPos) {
        this.startPos = startPos;
    }

    public void setEndPos(final Point endPos) {
        this.endPos = endPos;
    }

    public List<Point> getTurnPoints() {
        return turnPoints;
    }


    public List<Direction> getDirections() {
        return directions;
    }


    public void setTurnPointAt(int index, Point point){
        turnPoints.set(index, point);
    }

    public TileType getTileAt(int x, int y){
        return tileTypes[y][x];
    }


    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public int getTileSize() {
        return TILE_SIZE;
    }





    public GameMap(){
        tileTypes = new TileType[DEFAULT_MAP_SIZE][DEFAULT_MAP_SIZE];
        width = tileTypes.length;
        height = tileTypes[0].length;
        directions = new ArrayList<>();
        turnPoints = new ArrayList<>();

    }
}
