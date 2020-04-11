package learn.ieltswriting;

import java.io.IOException;
import java.nio.file.Files;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class FileLogger {

	private FileLogger() {
	}

	private static String logFile = null;

	public static Logger getLogger(String className) {
		Logger logger = Logger.getLogger(className);
		try {
			Date date = Calendar.getInstance().getTime();
			DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd-hh-mm");
			String strDate = dateFormat.format(date);
			if (logFile == null) {
				logFile = Files.createTempFile("ielts-writing-practise" + strDate + "-", ".log").toFile()
						.getAbsolutePath();
				logger.info("log file : " + logFile);
			}
			FileHandler fileHandler = new FileHandler(logFile);
			SimpleFormatter simple = new SimpleFormatter();
			fileHandler.setFormatter(simple);
			logger.addHandler(fileHandler);// adding Handler for file
		} catch (IOException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
		}
		return logger;
	}

	public static String getLogFilePath() {
		return FileLogger.logFile;
	}

}
