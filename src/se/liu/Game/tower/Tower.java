package se.liu.game.tower;

import se.liu.game.monster.MonsterType;

import java.awt.*;

/**
 * Towers are the object that the player places out on the screen, will shoot bullet objects that damage the monsters. Has different elements as well
 * represented by its subclasses
 */
public abstract class Tower
{
    protected int radius;
    protected Color color;
    protected Point position;
    protected int counter = 0;
    protected int coolDown;
    protected int damageLevel = 1;
    protected int rangeLevel = 1;
    protected int cost;
    protected TowerType towerType;


    public TowerType getTowerType() {
	return towerType;
    }

    public int getCounter() {
	return counter;
    }

    public void upgradeDamage(){
	damageLevel += 1;
	cost += cost/5;
    }

    public void upgradeRange(){
	radius += 10;
	cost += cost/5;
    }

    public int getCost() {
	return cost;
    }

    public int getElementMultiplier(MonsterType monsterType){
	return 0;
    }
    public int getDamageLevel() {
	return damageLevel;
    }

    public int getRangeLevel(){
	return rangeLevel;
    }

    public void setPosition(final Point position) {
	this.position = position;
    }

    public int getRadius() {
	return radius;
    }
    public void resetCounter() {
	counter = coolDown;
    }

    public Color getColor() {
	return color;
    }

    public Point getPosition() {
	return new Point(position);
    }


    // Only certain towers will override this behaviour
    public boolean maybeShoot(MonsterType monsterType, boolean isPoison){
	return true;
    }

    protected Tower(Point pos, int radius, int coolDown, Color color, int cost, TowerType towerType){
	position = pos;
	this.radius = radius;
	this.coolDown = coolDown;
	this.color = color;
	this.towerType = towerType;
	this.cost = cost;
    }

    public void update(){
	if(counter > 0){
	    counter -= 1;
	}
    }



}
