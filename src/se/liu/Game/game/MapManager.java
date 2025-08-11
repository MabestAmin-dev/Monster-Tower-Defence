package se.liu.game.game;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;

import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Manages loading and writing the maps to files, makes sure the right maps are shown in the menu.
 */
public class MapManager
{
    private List<GameMap> gameMaps;
    private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);


    public void writeToFile(GameMap gameMap) throws IOException {
	Gson gson = new GsonBuilder().setPrettyPrinting().create();
	String fileSeparator = File.separator;

	int index = 1;


	// Loop finds a free file name for the new gameMap file, will be Mapx.json where x is an integer between 1-100
	String fileNameTry;
	while (true) {

	    fileNameTry = "GameMap" + index;
	    File testFile = new File("resources" + fileSeparator + "json" + fileSeparator + "maps" + fileSeparator + fileNameTry + ".json");
	    if (!testFile.exists()) {
		break;
	    }

	    // Prevents infinite looping, over 100 maps is unreasonable
	    else if (index > 100) {
		throw new IOException("Cannot find empty filename!");
	    } else {
		index++;
	    }

	}




	FileWriter fileWriter = new FileWriter(
		"resources" + fileSeparator + "json" + fileSeparator + "maps" + fileSeparator + fileNameTry + ".json");
	gson.toJson(gameMap, fileWriter);
	fileWriter.close();
	LOGGER.info("Map" + fileNameTry + " written to file");

    }

    public GameMap readFromFile(String name) {
	Gson gson = new Gson();

	FileReader fileReader = null;
	try {
	    fileReader = new FileReader("resources" + File.separator + "json" + File.separator + "maps" + File.separator + name + ".json");
	} catch (FileNotFoundException e) {
	    JOptionPane.showMessageDialog(null, "FAILED TO ACCESS MAP FILE\nEXITING");
	    LOGGER.log(Level.SEVERE, "Failed to access map file", e);
	    System.exit(1);
	}

	try {
	   GameMap gameMapFromJson = gson.fromJson(fileReader, GameMap.class);
	   fileReader.close();
	   LOGGER.log(Level.INFO, "Map file loaded");
	   return gameMapFromJson;
	} catch (JsonIOException | JsonSyntaxException e) {
	    JOptionPane.showMessageDialog(null,"FAILED TO GET MAP FROM FILE\nWILL SKIP: " + name + ".json");
	    LOGGER.log(Level.WARNING, "Failed to get map from file, skipping map " + name, e);
	} catch (IOException e) {
	    JOptionPane.showMessageDialog(null, "FAILED TO CLOSE FILEREADER\nEXITING");
	    LOGGER.log(Level.SEVERE, "Failed to close FileReader", e);
	    System.exit(1);
	}

	return null;
    }

    public List<GameMap> getGameMaps() {
	return gameMaps;
    }

    public void refreshMaps(){
	gameMaps = new ArrayList<>();

 	int index = 1;

	while(true) {
	    String fileNameTry = "GameMap" + index;
	    File testFile = new File("resources" + File.separator + "json" + File.separator + "maps" + File.separator + fileNameTry + ".json");
	    if(!testFile.exists()) break;
	    else{
		GameMap gameMapFromFile = readFromFile(fileNameTry);
		if(gameMapFromFile != null) gameMaps.add(gameMapFromFile); // Will not load if file format invalid
		index++;
	    }
	}
	LOGGER.log(Level.INFO, "Maps have been refreshed");

    }

    public MapManager(){
	gameMaps = new ArrayList<>();

    }
}
