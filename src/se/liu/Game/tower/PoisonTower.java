package se.liu.game.tower;

import se.liu.game.monster.MonsterType;

import java.awt.*;

/**
 * A special tower that instead of damaging directly poisons the monsters, so they take damage over time. Therefore, it does not have an
 * element multiplier.
 */
public class PoisonTower extends Tower
{

    @Override public boolean maybeShoot(final MonsterType monsterType, boolean isPoison) {
	return !isPoison;
    }

    public PoisonTower(final Point pos) {
	super(pos, 150, 200, new Color(62, 243, 42, 255), 300, TowerType.POISON_TOWER);
    }
}
