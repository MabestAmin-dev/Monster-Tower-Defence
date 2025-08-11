package se.liu.game.monster;

import java.awt.*;


/**
 * Monster is the enemy that spawns in every round, they are created by MonsterMaker and will be called to move from GameEngine.
 * Each monster will damage the base with their current health they have left. Each monster is also either has weak or strong resistance
 * to the different towers.
 */

public class Monster
{
    private final MonsterType monsterType;
    private final Point position;
    private final Color color;
    private int velX = 0;
    private int velY = 0;
    private int health;
    private int poisonCooldown = 200;
    private final int monsterMoney;
    private boolean poison = false;



    public int getMonsterMoney() {
	return monsterMoney;
    }

    public MonsterType getMonsterType() {
	return monsterType;
    }

    public Monster(MonsterType monsterType, Point pos, int health, Color color, int monsterMoney){
	position = pos;
	this.health = health;
	this.color = color;
	this.monsterMoney = monsterMoney;
	this.monsterType = monsterType;

    }

    public void setPoison(final boolean poison) {
	if(monsterType != MonsterType.REAPER) this.poison = poison;
	else this.poison = false;
    }

    public Color getColor() {
	return color;
    }
    public Point getPosition(){
	return position;
    }

    public int getHealth() {
	return health;
    }

    public boolean isPoison() {
	return poison;
    }

    public void move(){
	System.out.println(velY);

	position.x += velX;
	position.y += velY;

	if (poison){
	    if (poisonCooldown == 0) {

		health -= 1;
		poisonCooldown = 200;
	    } else {
		poisonCooldown -= 1;
	    }
	}

    }

    public void damageMonster(int damage) {
	health -= damage;
    }
    public boolean isDead() {
	return health <= 0;
    }



    public void turn(Direction direction){
	switch (direction) {
	    case UP -> {
		velX = 0;
		velY = -1;
	    }
	    case DOWN -> {
		velX = 0;
		velY = 1;
	    }
	    case LEFT -> {
		velX = -1;
		velY = 0;
	    }
	    case RIGHT -> {
		velX = 1;
		velY = 0;
	    }
	}
    }
}
