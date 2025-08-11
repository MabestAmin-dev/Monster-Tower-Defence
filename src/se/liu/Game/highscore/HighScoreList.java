package se.liu.game.highscore;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;


/**
 * Contains a list of HighScore objects and also handles saving and reading the list to a file.
 */
public class HighScoreList
{
    private List<HighScore> highScores = new ArrayList<>();


    public HighScoreList() {
    }

    /**
     * checks if the highscore list is greater then 10, show only the top 10 in sorted order
     * @return
     * @throws NullPointerException
     */
    @Override public String toString() throws NullPointerException {
	ScoreComparator scoreComparator = new ScoreComparator();
	highScores.sort(scoreComparator);
	final int topTen = 10;

	StringBuilder builder = new StringBuilder("TOP 10 HighScores:  ");
	if (highScores.size() > topTen) {
	    for (int i = 0; i < topTen; i++) {
		builder.append("\n").append(highScores.get(i)).append("\n");
	    }
	}else {
	    for (HighScore hs : highScores) {
		builder.append("\n").append(hs).append("\n");
	    }
	}
	return builder.toString();
    }



    public void addHighscoreList(HighScore hs) throws FileNotFoundException {
	highScores.add(hs);
	saveHighscore();
    }


    public void saveHighscore() throws FileNotFoundException {
	Gson gson = new GsonBuilder().setPrettyPrinting().create();
	String listAsJson = gson.toJson(highScores);
	File file = new File("resources" + File.separator + "json" + File.separator + "highscorelist.json");
	File tempFile = new File("resources" + File.separator + "json" + File.separator + "highscorelist_temp.json");
	PrintWriter printWriter = new PrintWriter(tempFile);
	printWriter.print(listAsJson);
	printWriter.flush();
	printWriter.close();

	if(!file.delete() || !tempFile.renameTo(file)) {
	    throw new FileNotFoundException("COULD NOT SAVE TO FILE");
	}
    }


    public boolean isNotNull(){
	return (highScores != null);
    }

    public void overWriteFile() throws FileNotFoundException{
	highScores = new ArrayList<>();
	saveHighscore();
    }

    /**
     * reads the highscore file if not there create one
     * @throws IOException
     */
    public void readFromFile() throws FileNotFoundException, IOException{
	Gson gson = new Gson();
	// Filepath is relative to project even without using getSystemResource?
	File file = new File("resources" + File.separator + "json" + File.separator + "highscorelist.json");
	if(!file.exists())
	    if (file.createNewFile()) {
		overWriteFile();
	    }

	FileReader fileReader = new FileReader(file);
	highScores = gson.fromJson(fileReader, new TypeToken<List<HighScore>>(){}.getType());
	fileReader.close();


    }
}
