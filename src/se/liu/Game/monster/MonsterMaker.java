package se.liu.game.monster;

import se.liu.game.game.GameEngine;

import java.awt.*;
import java.util.Timer;
import java.util.TimerTask;


/**
 * Creates the correct amount of monsters per round. Difficulty increases over time. Does this with timers for each monsterType that
 * makes sure the monsters spawn not overlapping each other.
 */
public class MonsterMaker
{
    private final GameEngine gameEngine;
    private int fireAmount = 0;
    private int iceAmount = 0;
    private int grassAmount = 0;
    private int reaperAmount = 0;


    public void createMonsterPerRound(int round) {

	if(round >= 41){
	    fireAmount = round * 4;
	    iceAmount = round * 3;
	    grassAmount = round * 3;
	    reaperAmount = round*2;
	}
	else if(round >= 31){
	    fireAmount = round * 3;
	    iceAmount = round * 2;
	    grassAmount = round * 2;
	    reaperAmount = round;
	}
	else if(round >= 21){
	    fireAmount = round * 2;
	    iceAmount = round;
	    grassAmount = round;
	    reaperAmount = (int) (round / 1.5);
	}
	else if(round >= 11){
	    fireAmount = 0;
	    iceAmount = round / 2;
	    grassAmount = round;
	    reaperAmount = round / 4;
	}
	else{
	    fireAmount = round * 3;
	    if(round >= 4) iceAmount = round;
	    if(round >= 9) grassAmount = round / 2;
	}



	Timer timer = new Timer();
	TimerTask timerTask1 = new TimerTask()
	{
	    private int count = fireAmount;
	    @Override public void run() {

		count--;
		if(count <= 0) this.cancel();
		else gameEngine.addMonster(new Monster(MonsterType.FIRE, gameEngine.getStartPos(), 2, new Color(255, 99, 0), 5));
	    }
	};

	TimerTask timerTask2 = new TimerTask()
	{
	    private int count = iceAmount;
	    @Override public void run() {
		count--;
		if(count <= 0) this.cancel();
		else gameEngine.addMonster(new Monster(MonsterType.ICE, gameEngine.getStartPos(), 4, new Color(44, 186, 208), 10));

	    }
	};
	TimerTask timerTask3 = new TimerTask()
	{
	    private int count = grassAmount;
	    @Override public void run() {
		count--;
		if(count <= 0) this.cancel();
		else gameEngine.addMonster(new Monster(MonsterType.GRASS, gameEngine.getStartPos(), 6, new Color(55, 255, 65), 15));
	    }
	};

	TimerTask timerTask4 = new TimerTask()
	{
	    private int count = reaperAmount;

	    @Override public void run() {
		count--;
		if(count <= 0) this.cancel();
		else gameEngine.addMonster(new Monster(MonsterType.REAPER, gameEngine.getStartPos(), 4, new Color(84, 4, 4), 15));
	    }
	};
	/*
	These numbers are made to avoid overlapping products so the monsters will not spawn on top of each other.
	 */
	timer.schedule(timerTask1, 0, 500);
	timer.schedule(timerTask2, 300, 500);
	timer.schedule(timerTask3, 600, 500);
	timer.schedule(timerTask4, 900, 500);
    }

    public MonsterMaker(GameEngine gameEngine) {
	this.gameEngine = gameEngine;
    }

}
