package se.liu.game.tower;

import se.liu.game.monster.MonsterType;

import java.awt.*;

/**
 * A special tower that can damage the reaper monster. Does no damage to fire
 */

public class SunTower extends Tower
{
    @Override public int getElementMultiplier(final MonsterType monsterType) {
	int result = 0;
	switch (monsterType){
	    case REAPER -> result = 2;
	    case GRASS, ICE -> result = 1;
	}
	return result;
    }

    // Will only attempt to shoot reaper monster, but if it hits other monster still does damage according to above method
    @Override public boolean maybeShoot(final MonsterType monsterType, boolean isPoison) {
	return monsterType==MonsterType.REAPER;
    }

    public SunTower(final Point pos) {
	super(pos, 200, 150, Color.yellow, 400, TowerType.SUN_TOWER);


    }
}
