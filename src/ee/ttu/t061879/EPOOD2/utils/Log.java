package ee.ttu.t061879.EPOOD2.utils;

import org.apache.log4j.Logger;

public class Log {

  private static Logger logger = Logger.getLogger(Log.class);
  
  public Log(){
	logger.info("Log4j started");  
  }
  
  public void log(String message, String level) {
//	if(level.equalsIgnoreCase("info")) logger.info(level + " " + message);
//	else if (level.equalsIgnoreCase("debug")) logger.debug(level + " " + message);
//	else logger.error((level + " " + message));
	  logger.info(level + " " + message);
	System.out.println(level + " " + message);
  }
}
