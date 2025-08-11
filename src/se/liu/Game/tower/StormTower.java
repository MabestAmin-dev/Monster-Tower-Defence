package se.liu.game.tower;

import se.liu.game.monster.MonsterType;

import java.awt.*;

/**
 * Tower with the element Storm, overall good against everything except reaper
 */
public class StormTower extends Tower
{

    @Override public int getElementMultiplier(final MonsterType monsterType) {
	int result = 0;

	switch (monsterType) {
	    case ICE, GRASS, FIRE -> result = 2;

	}
	return result;

    }

    public StormTower(final Point pos) {
	super(pos, 150, 150, new Color(31, 190, 214), 250, TowerType.STORM_TOWER);


    }
}
