package se.liu.game.tower;

import se.liu.game.monster.MonsterType;

import java.awt.*;

/**
 * A tower with the element fire, is cheap, will do good damage against grass monsters, limited damaged to others and zero to reaper
 */
public class FireTower extends Tower
{


    @Override public int getElementMultiplier(final MonsterType monsterType) {
        int result = 0;
        switch(monsterType){
            case ICE, FIRE -> result = 1;
            case GRASS -> result = 4;


        }
        return result;
    }

    public FireTower(final Point pos) {
        super(pos, 150, 150, new Color(255, 140, 0), 100, TowerType.FIRE_TOWER);


    }
}
