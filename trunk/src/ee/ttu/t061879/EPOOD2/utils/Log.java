package ee.ttu.t061879.EPOOD2.utils;

import org.apache.log4j.Logger;

public class Log {

  private static Logger logger = Logger.getLogger(Log.class);
  
  public Log(){
	logger.info("Log4j started");  
  }
  
  public void log(String message, String level) {
	logger.info(message);
	System.out.println(message);
  }
}
