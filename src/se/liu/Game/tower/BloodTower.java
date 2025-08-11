package se.liu.game.tower;

import se.liu.game.monster.MonsterType;

import java.awt.*;

/**
 * A type of tower of the element blood, has high damage and fire rate
 */
public class BloodTower extends Tower
{

    @Override public int getElementMultiplier(final MonsterType monsterType) {
	int result = 0;
	switch (monsterType){
	    case FIRE, GRASS, ICE -> result = 4;
	}
	return result;
    }

    public BloodTower(final Point pos) {
	super(pos, 150, 50, new Color(139,0,0), 1500, TowerType.BLOOD_TOWER);


    }
}
