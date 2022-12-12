package logic;

import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 * this class is responsible to record the game status in a log file.
 */
public class Log {
    private static final Logger logger = Logger.getLogger(Log.class.getName());
    static {
        try {
            FileHandler fh;
            fh = new FileHandler("MyLog.log");
            logger.addHandler(fh);
            SimpleFormatter formatter = new SimpleFormatter();
            fh.setFormatter(formatter);

        } catch (Exception e) {
            logger.log(Level.WARNING, "Exception ::", e);
        }
    }

    /**
     * a method allow other class to record event to a log
     *
     * @param msg string message
     */
    public static void writeToLog(String msg) {
        logger.info(msg);
    }

}
