package utilityclass;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

/**
 * Author: Shreyas Bhagat
 * Date: 24/05/2025
 * LoggerUtil provides necessary functionalities for logging actions in Selenium WebDriver tests.
 */

public final class LoggerUtil  {

	// Initialize Log4j logger
    private static final Logger logger = LogManager.getLogger(LoggerUtil.class);
   
	public static void startTestCase(String sTestCaseName){		  
		logger.info("====================================="+sTestCaseName+" TEST START=========================================");
	}

	public static void endTestCase(String sTestCaseName){
		logger.info("====================================="+sTestCaseName+" TEST END=========================================");
	}

	// Need to create below methods, so that they can be called  


	public static void fatal(String message) {
		//debug level 100
		logger.fatal(message);

	}
	public static void error(String message) {
		//debug level 200
		logger.error(message);

	}
	public static void warn(String message) {
		//debug level 300
		logger.warn(message);

	}
	public static void info(String message) {
		//debug level 400
		logger.info(message);

	}
	public static void debug(String message) {
		//debug level 500
		logger.debug(message);
	}
	public static void trace(String message) {
		//debug level 600
		logger.trace(message);
	}
}


