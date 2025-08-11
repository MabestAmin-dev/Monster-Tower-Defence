package se.liu.game.highscore;

import java.util.Comparator;


/**
 * Will sort the HighScoreList with top score at the top.
 */
public class ScoreComparator implements Comparator<HighScore>
{
    public int compare(final HighScore player1, final HighScore player2) {
	return Integer.compare(player2.getFinishRound(), player1.getFinishRound());
    }
}
