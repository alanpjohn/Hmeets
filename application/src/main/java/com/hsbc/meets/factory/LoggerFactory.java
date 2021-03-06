package com.hsbc.meets.factory;

import java.io.IOException;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 * Factory class that returns a logger
 * setup purely for our application
 * 
 * @author alan
 *
 */
public class LoggerFactory {

	/**
	 * Returns the application configured logger.
	 * Uses console if file handler fails
	 * 
	 * @return logger
	 */
	public static Logger getLogger() {
		Logger logger = Logger.getLogger("meetings");
		logger.setLevel(Level.INFO);
		Handler handler = null;
		try {
			handler = new FileHandler("meetsapplication.log");
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if(handler == null) {
				handler = new ConsoleHandler();
				logger.warning("Log file couldnt be created, logging to console");
			}
			handler.setFormatter(new SimpleFormatter());
		}
		logger.addHandler(handler);
		return logger;
	}

}
