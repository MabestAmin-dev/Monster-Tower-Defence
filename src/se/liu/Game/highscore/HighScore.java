package se.liu.game.highscore;


/**
 * A highscore object, contains player name and what round they finished at
 */
public class HighScore
{
    private final int finishRound;
    private final String name;

    public HighScore(final int finishRound, final String name) {
	this.finishRound = finishRound;
	this.name = name;
    }

    public int getFinishRound() {
	return finishRound;
    }

    public String getName() {
	return name;
    }
    @Override public String toString() {
	return "Name: " + name + "\nScore: " + finishRound;
    }
}

