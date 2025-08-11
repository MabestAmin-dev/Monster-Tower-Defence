package se.liu.game.game;


import se.liu.game.tower.Bullet;
import se.liu.game.monster.Direction;
import se.liu.game.monster.MonsterMaker;
import se.liu.game.monster.Monster;
import se.liu.game.tower.Tower;

import java.awt.*;
import java.util.List;
import java.util.ArrayList;
import java.util.logging.Logger;

import static java.lang.Math.abs;

/**
 * Does most of the physics and hold a lot of information such as money, round and base health
 */

public class GameEngine
{

    private final List<Tower> towers = new ArrayList<>();
    private final List<GridListener> gridListeners = new ArrayList<>();
    private final List<Monster> monsters = new ArrayList<>();
    private final List<Bullet> bullets = new ArrayList<>();
    private GameMap gameMap;
    private int baseHealth = 300; // Starting base health
    private int round = 0;
    private int money = 200; // Starting money
    private Tower placing = null;
    private Tower upgrading = null;
    private final MonsterMaker monsterMaker;
    private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);


    public Tower getPlacing() {
        return placing;
    }


    public Tower getUpgrading() {
        return upgrading;
    }


    public void endGame(){
        baseHealth = 0;
    }


    public void setPlacing(final Tower placing) {
        this.placing = placing;
    }


    public void setUpgrading(final Tower upgrading){
        this.upgrading = upgrading;
    }


    public void subtractMoney(int towerCost){
        money -= towerCost;
    }


    public int getMoney() {
        return money;
    }


    public boolean isEmpty(){
        return monsters.isEmpty();
    }


    public int getRound() {
        return round;
    }


    public void addRound() {
        round += 1;
    }


    public int getBaseHealth() {
        return baseHealth;
    }


    public int getWidth() {
        return gameMap.getWidth();
    }

    public int getHeight() {
        return gameMap.getHeight();
    }


    public List<Monster> getMonsters() {
        return monsters;
    }


    public int getTileSize(){
        return gameMap.getTileSize();
    }


    public GameEngine(GameMap gameMap){
        monsterMaker = new MonsterMaker(this);
        this.gameMap = gameMap;



    }

    public List<Bullet> getBullets() {
        return bullets;
    }

    public void nextRound(){
        if(isEmpty()) {
            addRound();
            monsterMaker.createMonsterPerRound(getRound());
            setUpgrading(null);
            LOGGER.info("Round " + round + " started.");
        }
    }


    public void updateMonsters(){

        if(gameMap != null) {
            List<Point> turnPoints = gameMap.getTurnPoints();
            List<Direction> directions = gameMap.getDirections();

            // For each loop causes problems if another thread removes a monster from the list during iteration
            for (int i = 0; i < monsters.size(); i++) {

                if (turnPoints.contains(monsters.get(i).getPosition())) {
                    monsters.get(i).turn(directions.get(turnPoints.indexOf(monsters.get(i).getPosition())));
                }
                if (monsters.get(i).getPosition().equals(gameMap.getEndPos())) {
                    baseHealth -= monsters.get(i).getHealth();
                    monsters.remove(i);
                    break;
                } else if (monsters.get(i).isDead()) {
                    money += monsters.get(i).getMonsterMoney();
                    monsters.remove(i);
                    break;
                } else {
                    monsters.get(i).move();
                }
            }

            //notifyListeners();
        }

    }


    private void updateTowers(){
    // For each loop causes problems if another thread removes an object from the list during iteration
         for (int i = 0; i < towers.size(); i++) {
            towers.get(i).update();
            for (int j = 0; j < monsters.size(); j++) {
                if ((abs(towers.get(i).getPosition().x - monsters.get(j).getPosition().x) < towers.get(i).getRadius()) && (abs(towers.get(i).getPosition().y - monsters.get(j).getPosition().y) < towers.get(i).getRadius())) {
                    if(towers.get(i).getCounter() <= 0) {
                        if(towers.get(i).maybeShoot(monsters.get(j).getMonsterType(), monsters.get(j).isPoison())){
                            bullets.add(new Bullet(towers.get(i).getPosition(), monsters.get(j).getPosition(), towers.get(i)));
                            towers.get(i).resetCounter();
                        }
                    }
                }
            }
        }
    }


    public void tick(){
        updateBullets();
        updateMonsters();
        updateTowers();
        notifyListeners();
    }

    public void updateBullets(){
        final int maxBulletLife = 200;
        // For each loop causes problems if another thread removes an object from the list during iteration
        for (int i = 0; i < bullets.size(); i++) {
            bullets.get(i).move();
            for (int j = 0; j < monsters.size(); j++) {

                if (Math.abs(bullets.get(i).getPosition().x - monsters.get(j).getPosition().x) <= 1 &&
                    Math.abs(bullets.get(i).getPosition().y - monsters.get(j).getPosition().y) <= 1) {
                    if(bullets.get(i).isPoison() && !monsters.get(j).isPoison()){
                        monsters.get(j).setPoison(true);
                    }
                    else
                        monsters.get(j).damageMonster(bullets.get(i).getDamage() * bullets.get(i).getDamageMultiplier(monsters.get(j).getMonsterType()));
                    bullets.remove(i);
                    break;

                }
                else if(bullets.get(i).getLife() >= maxBulletLife){
                    bullets.remove(i);
                    break;
                }

            }

        }
    }

    public void notifyListeners(){
        for (GridListener listener : gridListeners) {
            listener.gridChanged();

        }

    }


    public Point getStartPos(){
        return gameMap.getStartPos();
    }

    public void addGridListener(GridListener gl){
        gridListeners.add(gl);
    }


    public void addMonster(Monster monster){
        LOGGER.fine("Monster added");
        monsters.add(monster);


    }


    public TileType getTileAt(int x, int y){
        return gameMap.getTileAt(x, y);

    }



    @Override public String toString() {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < getHeight(); i++) {
            for (int j = 0; j < getWidth(); j++) {
                 result.append(gameMap.getTileAt(j, i));
            }
            result.append("\n");
        }


        return String.valueOf(result);
    }


    public List<Tower> getTowers() {
        return towers;
    }


    public void addTower(Tower tower){
        towers.add(tower);


    }


    public Point getEndPos(){
        return gameMap.getEndPos();
    }


    public void addMoney(int money){
        this.money += money;
    }

    public void removeTower(Tower tower){
        towers.remove(tower);
    }


    public void readFromMap(GameMap gameMap){
        this.gameMap = gameMap;

    }
}
