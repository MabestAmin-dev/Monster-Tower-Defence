package se.liu.game.game;

import java.io.File;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;


/**
 * Static class that configures the global logger to save in the correct file with a simple format.
 */
public class GameLogger
{

    public static void setup() throws IOException{
        Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
        logger.setLevel(Level.INFO);

        FileHandler fileTxt = new FileHandler("resources" + File.separator + "log.txt");

        SimpleFormatter formatterTxt = new SimpleFormatter();
        fileTxt.setFormatter(formatterTxt);
        logger.addHandler(fileTxt);
    }

}
