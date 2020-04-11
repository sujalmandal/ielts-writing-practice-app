package learn.ieltswriting;

import java.util.logging.Logger;

public class Launcher {
	
	private static final Logger log = FileLogger.getLogger(Launcher.class.getName());
	
	public static void main(String[] args) {
		log.info("launching main app proxy.");
		App.main(args);
	}
}
