package se.liu.game.tower;

import se.liu.game.monster.MonsterType;

import java.awt.*;

/**
 * A tower with the element Ice, effective against fire(don't ask me why, ice melts) and weak against ice and zero damage to reaper.
 */
public class IceTower extends Tower
{


    @Override public int getElementMultiplier(final MonsterType monsterType) {
	int result = 0;

	switch(monsterType){
	    case FIRE -> result = 4;
	    case ICE -> result = 1;
	    case GRASS -> result = 2;

	}
	return result;
    }

    public IceTower(Point pos){
	super(pos, 150, 150, new Color(11, 198, 208), 150, TowerType.ICE_TOWER);


    }
}
